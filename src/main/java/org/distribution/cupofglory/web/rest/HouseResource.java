package org.distribution.cupofglory.web.rest;
import org.distribution.cupofglory.service.HouseService;
import org.distribution.cupofglory.service.dto.HouseScoreDTO;
import org.distribution.cupofglory.web.rest.errors.BadRequestAlertException;
import org.distribution.cupofglory.web.rest.util.HeaderUtil;
import org.distribution.cupofglory.service.dto.HouseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing House.
 */
@RestController
@RequestMapping("/api")
public class HouseResource {

    private final Logger log = LoggerFactory.getLogger(HouseResource.class);

    private static final String ENTITY_NAME = "house";

    private final HouseService houseService;

    public HouseResource(HouseService houseService) {
        this.houseService = houseService;
    }

    /**
     * POST  /houses : Create a new house.
     *
     * @param houseDTO the houseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new houseDTO, or with status 400 (Bad Request) if the house has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/houses")
    public ResponseEntity<HouseDTO> createHouse(@Valid @RequestBody HouseDTO houseDTO) throws URISyntaxException {
        log.debug("REST request to save House : {}", houseDTO);
        if (houseDTO.getId() != null) {
            throw new BadRequestAlertException("A new house cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HouseDTO result = houseService.save(houseDTO);
        return ResponseEntity.created(new URI("/api/houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /houses : Updates an existing house.
     *
     * @param houseDTO the houseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated houseDTO,
     * or with status 400 (Bad Request) if the houseDTO is not valid,
     * or with status 500 (Internal Server Error) if the houseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/houses")
    public ResponseEntity<HouseDTO> updateHouse(@Valid @RequestBody HouseDTO houseDTO) throws URISyntaxException {
        log.debug("REST request to update House : {}", houseDTO);
        if (houseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HouseDTO result = houseService.save(houseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, houseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /houses : get all the houses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of houses in body
     */
    @GetMapping("/houses")
    public List<HouseDTO> getAllHouses() {
        log.debug("REST request to get all Houses");
        return houseService.findAll();
    }

    /**
     * GET  /houses/:id : get the "id" house.
     *
     * @param id the id of the houseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the houseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/houses/{id}")
    public ResponseEntity<HouseDTO> getHouse(@PathVariable Long id) {
        log.debug("REST request to get House : {}", id);
        Optional<HouseDTO> houseDTO = houseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(houseDTO);
    }

    /**
     * DELETE  /houses/:id : delete the "id" house.
     *
     * @param id the id of the houseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/houses/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        log.debug("REST request to delete House : {}", id);
        houseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/houses/score")
    public ResponseEntity<Void> manageScore(@Valid @RequestBody HouseScoreDTO houseScoreDTO) {

        this.houseService.manageScore(houseScoreDTO);
        // TODO catch errors et return 403 or 500

        return ResponseEntity.ok().build();
    }
}
