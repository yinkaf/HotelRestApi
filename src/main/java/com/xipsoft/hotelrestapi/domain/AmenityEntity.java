package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;

@Entity(name = "amenity_mst")
public class AmenityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="amenity_id")
    private int id;

    @Column(name = "short_desc", length = 10, nullable = false)
    private String shortDesc;

    @Column(name = "description", length = 200, nullable = false)
    private String description;


    public int getId() {
        return id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
