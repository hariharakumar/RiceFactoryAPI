package com.projects.ricefactory.dto;

/**
 * Created by hearlapati on 2/28/17.
 */
public class RiceType {


    private Long id;
    private String displayName;
    private String internalName;
    private Float price_per_kg;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public Float getPrice_per_kg() {
        return price_per_kg;
    }

    public void setPrice_per_kg(Float price_per_kg) {
        this.price_per_kg = price_per_kg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
