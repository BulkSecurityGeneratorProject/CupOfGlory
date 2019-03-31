package org.distribution.cupofglory.web.rest;

import org.distribution.cupofglory.CupOfGloryApp;

import org.distribution.cupofglory.domain.House;
import org.distribution.cupofglory.domain.School;
import org.distribution.cupofglory.repository.HouseRepository;
import org.distribution.cupofglory.service.HouseService;
import org.distribution.cupofglory.service.dto.HouseDTO;
import org.distribution.cupofglory.service.mapper.HouseMapper;
import org.distribution.cupofglory.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static org.distribution.cupofglory.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HouseResource REST controller.
 *
 * @see HouseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CupOfGloryApp.class)
public class HouseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHouseMockMvc;

    private House house;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HouseResource houseResource = new HouseResource(houseService);
        this.restHouseMockMvc = MockMvcBuilders.standaloneSetup(houseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static House createEntity(EntityManager em) {
        House house = new House()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .score(DEFAULT_SCORE);
        // Add required entity
        School school = SchoolResourceIntTest.createEntity(em);
        em.persist(school);
        em.flush();
        house.setSchool(school);
        return house;
    }

    @Before
    public void initTest() {
        house = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouse() throws Exception {
        int databaseSizeBeforeCreate = houseRepository.findAll().size();

        // Create the House
        HouseDTO houseDTO = houseMapper.toDto(house);
        restHouseMockMvc.perform(post("/api/houses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isCreated());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeCreate + 1);
        House testHouse = houseList.get(houseList.size() - 1);
        assertThat(testHouse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHouse.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testHouse.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createHouseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = houseRepository.findAll().size();

        // Create the House with an existing ID
        house.setId(1L);
        HouseDTO houseDTO = houseMapper.toDto(house);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseMockMvc.perform(post("/api/houses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = houseRepository.findAll().size();
        // set the field null
        house.setName(null);

        // Create the House, which fails.
        HouseDTO houseDTO = houseMapper.toDto(house);

        restHouseMockMvc.perform(post("/api/houses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isBadRequest());

        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHouses() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList
        restHouseMockMvc.perform(get("/api/houses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(house.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    
    @Test
    @Transactional
    public void getHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get the house
        restHouseMockMvc.perform(get("/api/houses/{id}", house.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(house.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingHouse() throws Exception {
        // Get the house
        restHouseMockMvc.perform(get("/api/houses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        int databaseSizeBeforeUpdate = houseRepository.findAll().size();

        // Update the house
        House updatedHouse = houseRepository.findById(house.getId()).get();
        // Disconnect from session so that the updates on updatedHouse are not directly saved in db
        em.detach(updatedHouse);
        updatedHouse
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .score(UPDATED_SCORE);
        HouseDTO houseDTO = houseMapper.toDto(updatedHouse);

        restHouseMockMvc.perform(put("/api/houses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isOk());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeUpdate);
        House testHouse = houseList.get(houseList.size() - 1);
        assertThat(testHouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHouse.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testHouse.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingHouse() throws Exception {
        int databaseSizeBeforeUpdate = houseRepository.findAll().size();

        // Create the House
        HouseDTO houseDTO = houseMapper.toDto(house);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseMockMvc.perform(put("/api/houses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        int databaseSizeBeforeDelete = houseRepository.findAll().size();

        // Delete the house
        restHouseMockMvc.perform(delete("/api/houses/{id}", house.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(House.class);
        House house1 = new House();
        house1.setId(1L);
        House house2 = new House();
        house2.setId(house1.getId());
        assertThat(house1).isEqualTo(house2);
        house2.setId(2L);
        assertThat(house1).isNotEqualTo(house2);
        house1.setId(null);
        assertThat(house1).isNotEqualTo(house2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseDTO.class);
        HouseDTO houseDTO1 = new HouseDTO();
        houseDTO1.setId(1L);
        HouseDTO houseDTO2 = new HouseDTO();
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
        houseDTO2.setId(houseDTO1.getId());
        assertThat(houseDTO1).isEqualTo(houseDTO2);
        houseDTO2.setId(2L);
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
        houseDTO1.setId(null);
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(houseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(houseMapper.fromId(null)).isNull();
    }
}
