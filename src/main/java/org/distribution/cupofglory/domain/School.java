package org.distribution.cupofglory.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A School.
 */
@Entity
@Table(name = "school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("schools")
    private User director;

    @OneToMany(mappedBy = "school")
    private Set<House> houses = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public School name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getDirector() {
        return director;
    }

    public School director(User user) {
        this.director = user;
        return this;
    }

    public void setDirector(User user) {
        this.director = user;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public School houses(Set<House> houses) {
        this.houses = houses;
        return this;
    }

    public School addHouse(House house) {
        this.houses.add(house);
        house.setSchool(this);
        return this;
    }

    public School removeHouse(House house) {
        this.houses.remove(house);
        house.setSchool(null);
        return this;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        School school = (School) o;
        if (school.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), school.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
