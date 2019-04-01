package org.distribution.cupofglory.repository;

import org.distribution.cupofglory.domain.School;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the School entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("select school from School school where school.director.login = ?#{principal.username}")
    List<School> findByDirectorIsCurrentUser();

}
