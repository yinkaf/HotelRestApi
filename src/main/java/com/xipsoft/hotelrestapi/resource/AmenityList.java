package com.xipsoft.hotelrestapi.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AmenityList {
    @Valid
    @NotNull
    private List<Amenity> items;

    public AmenityList() {
    }

    public AmenityList(List<Amenity> amenities) {
        this.items = amenities;
    }

    public List<Amenity> getItems() {
        return items;
    }

    public void setItems(List<Amenity> items) {
        this.items = items;
    }
}
