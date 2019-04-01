package org.distribution.cupofglory.service.mapper;

import org.distribution.cupofglory.domain.*;
import org.distribution.cupofglory.service.dto.HouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity House and its DTO HouseDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class})
public interface HouseMapper extends EntityMapper<HouseDTO, House> {

    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "school.name", target = "schoolName")
    HouseDTO toDto(House house);

    @Mapping(source = "schoolId", target = "school")
    House toEntity(HouseDTO houseDTO);

    default House fromId(Long id) {
        if (id == null) {
            return null;
        }
        House house = new House();
        house.setId(id);
        return house;
    }
}
