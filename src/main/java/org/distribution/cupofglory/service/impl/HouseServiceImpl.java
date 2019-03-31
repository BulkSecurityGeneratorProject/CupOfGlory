package org.distribution.cupofglory.service.impl;

import org.distribution.cupofglory.service.HouseService;
import org.distribution.cupofglory.domain.House;
import org.distribution.cupofglory.repository.HouseRepository;
import org.distribution.cupofglory.service.dto.HouseDTO;
import org.distribution.cupofglory.service.mapper.HouseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing House.
 */
@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    private final Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);

    private final HouseRepository houseRepository;

    private final HouseMapper houseMapper;

    public HouseServiceImpl(HouseRepository houseRepository, HouseMapper houseMapper) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
    }

    /**
     * Save a house.
     *
     * @param houseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HouseDTO save(HouseDTO houseDTO) {
        log.debug("Request to save House : {}", houseDTO);
        House house = houseMapper.toEntity(houseDTO);
        house = houseRepository.save(house);
        return houseMapper.toDto(house);
    }

    /**
     * Get all the houses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HouseDTO> findAll() {
        log.debug("Request to get all Houses");
        return houseRepository.findAll().stream()
            .map(houseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one house by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HouseDTO> findOne(Long id) {
        log.debug("Request to get House : {}", id);
        return houseRepository.findById(id)
            .map(houseMapper::toDto);
    }

    /**
     * Delete the house by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete House : {}", id);
        houseRepository.deleteById(id);
    }
}
