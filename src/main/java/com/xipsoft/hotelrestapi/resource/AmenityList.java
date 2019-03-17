package com.xipsoft.hotelrestapi.resource;

import javax.validation.Valid;
import java.util.List;

public class AmenityList {
    @Valid
    private List<Amenity> items;

    public List<Amenity> getItems() {
        return items;
    }

    public void setItems(List<Amenity> items) {
        this.items = items;
    }
}
