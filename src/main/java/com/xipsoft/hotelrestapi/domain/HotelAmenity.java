package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name ="hotel_amenities")
public class HotelAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_ame_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEnitity hotel;

    @OneToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private AmenityEntity amenity;

    private boolean chargeable;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public HotelEnitity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEnitity hotel) {
        this.hotel = hotel;
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
