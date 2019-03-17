package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "room_amenities")
public class RoomAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_ame_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @OneToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private AmenityEntity amenity;

    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
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
