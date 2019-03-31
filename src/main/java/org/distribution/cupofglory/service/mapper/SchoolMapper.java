package org.distribution.cupofglory.service.mapper;

import org.distribution.cupofglory.domain.*;
import org.distribution.cupofglory.service.dto.SchoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity School and its DTO SchoolDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {

    @Mapping(source = "director.id", target = "directorId")
    @Mapping(source = "director.login", target = "directorLogin")
    SchoolDTO toDto(School school);

    @Mapping(source = "directorId", target = "director")
    @Mapping(target = "houses", ignore = true)
    School toEntity(SchoolDTO schoolDTO);

    default School fromId(Long id) {
        if (id == null) {
            return null;
        }
        School school = new School();
        school.setId(id);
        return school;
    }
}
