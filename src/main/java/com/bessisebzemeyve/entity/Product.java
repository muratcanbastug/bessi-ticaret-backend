package com.bessisebzemeyve.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
public class Product extends EntityBase{
    @Column(name = "NAME", length = 255, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "PRODUCT_UNITS",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")})
    private Set<Unit> units;

    @Column(name = "TYPE")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }
}
