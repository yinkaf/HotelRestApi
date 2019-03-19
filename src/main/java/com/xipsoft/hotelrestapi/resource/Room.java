package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Room {
    @NotNull(message = "Room description cannot be null")
    @Size(max = 50, message = "Room description cannot be longer that 50 characters")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
