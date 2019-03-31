package org.distribution.cupofglory.service;

import org.distribution.cupofglory.service.dto.HouseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing House.
 */
public interface HouseService {

    /**
     * Save a house.
     *
     * @param houseDTO the entity to save
     * @return the persisted entity
     */
    HouseDTO save(HouseDTO houseDTO);

    /**
     * Get all the houses.
     *
     * @return the list of entities
     */
    List<HouseDTO> findAll();


    /**
     * Get the "id" house.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HouseDTO> findOne(Long id);

    /**
     * Delete the "id" house.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
