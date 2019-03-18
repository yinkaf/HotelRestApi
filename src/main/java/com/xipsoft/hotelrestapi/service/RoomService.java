package com.xipsoft.hotelrestapi.service;

import com.xipsoft.hotelrestapi.controller.exception.DataNotFoundException;
import com.xipsoft.hotelrestapi.domain.AmenityEntity;
import com.xipsoft.hotelrestapi.domain.HotelEntity;
import com.xipsoft.hotelrestapi.domain.RoomAmenityEntity;
import com.xipsoft.hotelrestapi.domain.RoomEntity;
import com.xipsoft.hotelrestapi.repo.AmenityRepository;
import com.xipsoft.hotelrestapi.repo.HotelRepository;
import com.xipsoft.hotelrestapi.repo.RoomAmenityRepository;
import com.xipsoft.hotelrestapi.repo.RoomRepository;
import com.xipsoft.hotelrestapi.resource.AmenityList;
import com.xipsoft.hotelrestapi.resource.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomAmenityRepository roomAmenityRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, RoomAmenityRepository roomAmenityRepository, AmenityRepository amenityRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomAmenityRepository = roomAmenityRepository;
        this.amenityRepository = amenityRepository;
    }

    public RoomEntity createRoom(Room roomResource) {
        HotelEntity hotel = hotelRepository.findById(roomResource.getHotelId()).orElseThrow(() -> new DataNotFoundException("Hotel not found", roomResource.getHotelId()));
        RoomEntity room = new RoomEntity();
        room.setHotelId(roomResource.getHotelId());
        room.setDescription(roomResource.getDescription());
        return roomRepository.save(room);

    }

    public List<RoomAmenityEntity> addEmrnities(int id, AmenityList amenities) {
        List<RoomAmenityEntity> saveRoomAmentities = new ArrayList<>();
        try {
            amenities.getItems().forEach(amenity -> {
                AmenityEntity amenityEntity = new AmenityEntity();
                amenityEntity.setShortDesc(amenity.getShortDesc());
                amenityEntity.setDescription(amenity.getDescription());
                AmenityEntity savedAmenity = amenityRepository.save(amenityEntity);
                RoomAmenityEntity roomAmenityEntity = new RoomAmenityEntity();
                roomAmenityEntity.setRoomId(id);
                roomAmenityEntity.setAmenity(savedAmenity);
                roomAmenityEntity.setAmount(amenity.getAmount());
                roomAmenityEntity.setChargeable(amenity.isChargeable());
                RoomAmenityEntity savedHotelAmenity = roomAmenityRepository.save(roomAmenityEntity);
                saveRoomAmentities.add(savedHotelAmenity);
            });
        }catch (DataIntegrityViolationException ex){

            throw new DataNotFoundException("Room not found",id);
        }
        return  saveRoomAmentities;
    }

    public List<RoomAmenityEntity> getAmenities(int roomId) {
       return roomAmenityRepository.findByRoomId(roomId);
    }

    public RoomEntity getRoom(int id) {
        return roomRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Room not found",id));
    }

    public List<RoomEntity> getRoomsForHotel(int id) {
        return roomRepository.findByHotelId(id).orElseThrow(()-> new DataNotFoundException("Rooms not found for hotel",id));
    }

    public void delete(int id) {
        roomRepository.deleteById(id);
    }
}
