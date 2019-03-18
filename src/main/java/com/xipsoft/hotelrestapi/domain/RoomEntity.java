package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;

@Entity(name="rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int id;

    @Column(name = "hotel_id",nullable = false)
    private int hotelId;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
