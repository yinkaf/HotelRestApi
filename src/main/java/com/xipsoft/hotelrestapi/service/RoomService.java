package com.xipsoft.hotelrestapi.service;

import com.xipsoft.hotelrestapi.controller.exception.DataNotFoundException;
import com.xipsoft.hotelrestapi.domain.AmenityEntity;
import com.xipsoft.hotelrestapi.domain.RoomAmenityEntity;
import com.xipsoft.hotelrestapi.domain.RoomEntity;
import com.xipsoft.hotelrestapi.repo.AmenityRepository;
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
    private final RoomAmenityRepository roomAmenityRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomAmenityRepository roomAmenityRepository, AmenityRepository amenityRepository) {
        this.roomRepository = roomRepository;
        this.roomAmenityRepository = roomAmenityRepository;
        this.amenityRepository = amenityRepository;
    }

    public RoomEntity createRoom(int hotelId, Room roomResource) {
        try {
            RoomEntity room = new RoomEntity();
            room.setHotelId(hotelId);
            room.setDescription(roomResource.getDescription());
            return roomRepository.save(room);
        }catch (DataIntegrityViolationException e){
            throw new DataNotFoundException("Hotel not found",hotelId);
        }

    }

    public List<RoomAmenityEntity> addEmrnities( int roomId, AmenityList amenities) {
        List<RoomAmenityEntity> saveRoomAmentities = new ArrayList<>();
        try {
            amenities.getItems().forEach(amenity -> {
                AmenityEntity amenityEntity = amenityRepository.findByShortDesc(amenity.getShortDesc());
                if(amenityEntity == null) {
                    amenityEntity = new AmenityEntity();
                    amenityEntity.setShortDesc(amenity.getShortDesc());
                    amenityEntity.setDescription(amenity.getDescription());
                }else{
                    if(!amenityEntity.getDescription().equals((amenity.getDescription()))) {
                        amenityEntity.setDescription(amenity.getDescription());
                    }
                }
                AmenityEntity savedAmenity = amenityRepository.save(amenityEntity);
                RoomAmenityEntity roomAmenityEntity = new RoomAmenityEntity();
                roomAmenityEntity.setRoomId(roomId);
                roomAmenityEntity.setAmenity(savedAmenity);
                roomAmenityEntity.setAmount(amenity.getAmount());
                roomAmenityEntity.setChargeable(amenity.isChargeable());
                RoomAmenityEntity savedHotelAmenity = roomAmenityRepository.save(roomAmenityEntity);
                saveRoomAmentities.add(savedHotelAmenity);
            });
        }catch (DataIntegrityViolationException ex){

            throw new DataNotFoundException("Room not found",roomId);
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

    public void deleteRoomAmenity(int id) {
        roomAmenityRepository.deleteById(id);
    }
}
