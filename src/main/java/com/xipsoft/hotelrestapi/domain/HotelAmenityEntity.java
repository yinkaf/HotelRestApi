package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name ="hotel_amenities")
public class HotelAmenityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_ame_id")
    private int id;

    @Column(name = "hotel_id", nullable = false)
    private int hotelId;

    @OneToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private AmenityEntity amenity;

    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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
