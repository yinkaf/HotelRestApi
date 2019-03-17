package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RoomResource {
    private int id;
    @NotNull(message = "Room description cannot be null")
    @Size(max = 50, message = "Room description cannot be longer that 50 characters")
    private String description;
    @Positive(message = "Must supply a hotel id")
    private int hotelId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
