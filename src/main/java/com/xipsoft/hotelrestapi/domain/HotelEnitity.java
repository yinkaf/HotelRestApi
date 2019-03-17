package com.xipsoft.hotelrestapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name="hotels")
public class HotelEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_id")
    private int id;

    @NotNull(message = "Hotel name cannot be null")
    @Size(max = 50, message = "Hotel name cannot be longer than 50 characters")
    @Column(name = "hotel_name",length = 50, nullable = false)
    private String name;

    @NotNull(message = "Hotel description cannot be null")
    @Size(max = 500, message = "Hotel description cannot be longer than 500 characters")
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @NotNull(message = "City code cannot be null")
    @Size(max = 3, message = "City code cannot be longer than 3 characters")
    @Column(name = "city_code", length = 3, nullable = false)
    private String cityCode;

    @OneToMany(mappedBy = "hotel")
    List<HotelAmenity> amenities;
    @OneToMany(mappedBy = "hotel")
    List<RoomEntity> rooms;

    public int getId() {
        return id;
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

    public List<HotelAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<HotelAmenity> amenities) {
        this.amenities = amenities;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}
