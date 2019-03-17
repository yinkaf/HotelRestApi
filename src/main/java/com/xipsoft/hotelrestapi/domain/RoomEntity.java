package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;

@Entity(name="rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "hotel_id",nullable = false)
    private HotelEnitity hotel;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    public int getId() {
        return id;
    }

    public HotelEnitity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEnitity hotel) {
        this.hotel = hotel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
