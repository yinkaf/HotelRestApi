package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Hotel {
    private int id;
    @NotNull(message = "Hotel name cannot be null")
    @Size(max = 50, message = "Hotel name cannot be longer than 50 characters")
    private String name;
    @NotNull(message = "Hotel description cannot be null")
    @Size(max = 500, message = "Hotel description cannot be longer than 500 characters")
    private String description;
    @NotNull(message = "City code cannot be null")
    @Size(max = 3, message = "City code cannot be longer than 3 characters")
    private String cityCode;

    public Hotel() {
    }

    public Hotel(int id, @NotNull(message = "Hotel name cannot be null") @Size(max = 50, message = "Hotel name cannot be longer than 50 characters") String name, @NotNull(message = "Hotel description cannot be null") @Size(max = 500, message = "Hotel description cannot be longer than 500 characters") String description, @NotNull(message = "City code cannot be null") @Size(max = 3, message = "City code cannot be longer than 3 characters") String cityCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityCode = cityCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
