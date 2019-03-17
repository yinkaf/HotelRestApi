package com.xipsoft.hotelrestapi.resource;

public class HotelSearchParam {
    private String name;
    private String cityCode;
    private String description;

    public HotelSearchParam() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
