package org.distribution.cupofglory.repository;

import org.distribution.cupofglory.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the House entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    /**
     * Add or remove points from the house's score
     *
     * @param id     house id
     * @param points number of points
     * @return number of house updated
     */
    @Modifying
    @Query("UPDATE House h SET h.score = h.score + :points WHERE h.id = :id")
    int updateScore(@Param("id") long id, @Param("points") int points);
}
