package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RoomAmenityResource {
    private int id;
    @Positive(message = "Must supply room id")
    private int roomId;
    @Positive(message = "Must supply amenity id")
    private int amenityId;
    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
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
