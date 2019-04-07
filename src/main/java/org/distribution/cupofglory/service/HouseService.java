package org.distribution.cupofglory.service;

import org.distribution.cupofglory.service.dto.HouseDTO;
import org.distribution.cupofglory.service.dto.HouseScoreDTO;
import org.distribution.cupofglory.service.exceptions.ForbiddenSchoolAccessException;
import org.distribution.cupofglory.service.exceptions.UnknownHouseException;
import org.distribution.cupofglory.service.exceptions.UnknownSchoolException;

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

    /**
     * Add or remove points for a house
     *
     * @param houseScoreDTO the dto with all the informations needed to manage the score
     */
    void manageScore(HouseScoreDTO houseScoreDTO) throws UnknownSchoolException, ForbiddenSchoolAccessException, UnknownHouseException;
}
