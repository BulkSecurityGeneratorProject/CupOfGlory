package org.distribution.cupofglory.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the School entity.
 */
public class SchoolDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long directorId;

    private String directorLogin;

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

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long userId) {
        this.directorId = userId;
    }

    public String getDirectorLogin() {
        return directorLogin;
    }

    public void setDirectorLogin(String userLogin) {
        this.directorLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolDTO schoolDTO = (SchoolDTO) o;
        if (schoolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", director=" + getDirectorId() +
            ", director='" + getDirectorLogin() + "'" +
            "}";
    }
}
