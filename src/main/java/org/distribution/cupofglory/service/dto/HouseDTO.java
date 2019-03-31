package org.distribution.cupofglory.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the House entity.
 */
public class HouseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    @Size(min = 7, max = 7)
    private String color;

    private Integer score;


    private Long schoolId;

    private String schoolName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HouseDTO houseDTO = (HouseDTO) o;
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
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", score=" + getScore() +
            ", school=" + getSchoolId() +
            ", school='" + getSchoolName() + "'" +
            "}";
    }
}
