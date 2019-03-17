package com.xipsoft.hotelrestapi.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/room")
public class RoomRestController {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private RoomAmenityRepository roomAmenityRepository;
    private AmenityRepository amenityRepository;

    @Autowired
    public RoomRestController(RoomRepository roomRepository, HotelRepository hotelRepository, RoomAmenityRepository roomAmenityRepository, AmenityRepository amenityRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomAmenityRepository = roomAmenityRepository;
        this.amenityRepository = amenityRepository;
    }

    /**
     * Creates a Room
     * will throw DataNotFound if the hotel for the room is not found
     * @param roomResource the room data
     * @return the created room and a status of CREATED
     */
    @PostMapping
    public ResponseEntity<?> createRoom(@Valid @RequestBody Room roomResource){
        HotelEntity hotel = hotelRepository.findById(roomResource.getHotelId()).orElseThrow(() -> new DataNotFoundException("Hotel not found",roomResource.getHotelId()));
        RoomEntity room = new RoomEntity();
        room.setHotel(hotel);
        room.setDescription(roomResource.getDescription());
          RoomEntity savedRoom =   roomRepository.save(room);
           return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);

    }

    /**
     * Add amenities to a room
     * @param id the id of the room, this will throw a DataNotFoundException if the room does not exist
     * @param amenities the list of amenities to add
     * @return the list am amenities added
     */
    @PostMapping(path = "{id}/amenity")
    public ResponseEntity<?> addAmenities(@PathVariable("id") int id, @Valid @RequestBody AmenityList amenities){
        List<RoomAmenityEntity> saveRoomAmentities = new ArrayList<>();
        RoomEntity room =  roomRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Room not found",id));
        amenities.getItems().forEach(amenity -> {
            AmenityEntity amenityEntity = new AmenityEntity();
            amenityEntity.setShortDesc(amenity.getShortDesc());
            amenityEntity.setDescription(amenity.getDescription());
            AmenityEntity savedAmenity =  amenityRepository.save(amenityEntity);
            RoomAmenityEntity roomAmenityEntity = new RoomAmenityEntity();
            roomAmenityEntity.setRoom(room);
            roomAmenityEntity.setAmenity(savedAmenity);
            roomAmenityEntity.setAmount(amenity.getAmount());
            roomAmenityEntity.setChargeable(amenity.isChargeable());
            RoomAmenityEntity savedHotelAmenity = roomAmenityRepository.save(roomAmenityEntity);
            saveRoomAmentities.add(savedHotelAmenity);
        });
        return new ResponseEntity<>(saveRoomAmentities,HttpStatus.CREATED);
    }

    /**
     * Get the list of amenities for a room, this will throw a EmptyResultDataAccessException if the room does not exist
     * @param roomId the id of the room
     * @return the list of the added room amenities
     */
    @GetMapping(path = "{roomId}/amenity")
    public ResponseEntity<?> getAmenities(@PathVariable("roomId") int roomId){
        return new ResponseEntity<>(roomAmenityRepository.findByRoomId(roomId),HttpStatus.OK);

    }
    /**
     * Get a room with id
     * @param id the id of the room
     * @return return a room or throws DataNotFoundException
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getRoom(@PathVariable("id") int id){
        return new ResponseEntity<>(roomRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Room not found",id)),HttpStatus.OK);
    }

    /**
     * Get all the rooms for a hotel with  id
     * @param id the id of the hotel
     * @return list of rooms
     */
    @GetMapping(path = "hotel/{id}")
    public ResponseEntity<?> getRoomsForHotel(@PathVariable("id") int id){
        return new ResponseEntity<>(roomRepository.findByHotelId(id).orElseThrow(()-> new DataNotFoundException("Rooms not found for hotel",id)),HttpStatus.OK);
    }

    /**
     * Deletes a room wit id
     * @param id the id of the room to delete
     * @return a status of NO_CONTENT if successful or NOT_FOUND if the room does not exist
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") int id){
        roomRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
