package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class HotelAmenityResource {
    private int id;
    @Positive(message = "Must supply Amenity")
    private int amenityId;
    @Positive(message = "Must supply hotel")
    private int hotelId;
    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
