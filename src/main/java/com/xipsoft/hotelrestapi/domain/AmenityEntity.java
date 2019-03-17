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
}
