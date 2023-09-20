package com.bessisebzemeyve.model;

import java.util.Set;

public class ProductResponseDTO {
    private String name;
    private Set<String> units;
    private String type;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<String> getUnits() {
        return units;
    }

    public void setUnits(Set<String> units) {
        this.units = units;
    }

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
}
