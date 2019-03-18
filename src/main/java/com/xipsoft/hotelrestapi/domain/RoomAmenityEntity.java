package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "room_amenities")
public class RoomAmenityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_ame_id")
    private int id;

    @Column(name = "room_id", nullable = false)
    private int roomId;

    @OneToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private AmenityEntity amenity;

    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public AmenityEntity getAmenity() {
        return amenity;
    }

    public void setAmenity(AmenityEntity amenity) {
        this.amenity = amenity;
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
