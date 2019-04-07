package org.distribution.cupofglory.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the House score entity.
 */
public class HouseScoreDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Integer points;

    @NotNull
    private Long schoolId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HouseScoreDTO houseDTO = (HouseScoreDTO) o;
        if (houseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), houseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
            "id=" + getId() +
            ", score=" + getPoints() +
            ", school=" + getSchoolId() +
            "}";
    }
}
