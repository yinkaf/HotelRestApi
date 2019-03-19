package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.domain.RoomAmenityEntity;
import com.xipsoft.hotelrestapi.domain.RoomEntity;
import com.xipsoft.hotelrestapi.resource.AmenityList;
import com.xipsoft.hotelrestapi.resource.Room;
import com.xipsoft.hotelrestapi.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/rooms")
public class RoomRestController {

    final private RoomService roomService;

    @Autowired
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }


    /**
     * Add amenities to a room
     *
     * @param id        the id of the room, this will throw a DataNotFoundException if the room does not exist
     * @param amenities the list of amenities to add
     * @return the list am amenities added
     */
    @PostMapping(path = "{id}/amenities")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<RoomAmenityEntity>> addAmenities(@PathVariable("id") int id, @Valid @RequestBody AmenityList amenities) {

        return new ResponseEntity<>(roomService.addEmrnities(id, amenities), HttpStatus.CREATED);
    }


    /**
     * Delete a room amenity this will throw a EmptyResultDataAccessException if the room amenity does not exist
     *
     * @param id the id of the room amenity
     * @return NO_CONTENT status code
     */
    @DeleteMapping(path = "amenities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRoomAmenity( @PathVariable("id") int id) {
        roomService.deleteRoomAmenity(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);

    }

    /**
     * Get the list of amenities for a room, this will throw a EmptyResultDataAccessException if the room does not exist
     *
     * @param roomId the id of the room
     * @return the list of the added room amenities
     */
    @GetMapping(path = "{roomId}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RoomAmenityEntity>> getAmenities(@PathVariable("roomId") int roomId) {
        return new ResponseEntity<>(roomService.getAmenities(roomId), HttpStatus.OK);

    }

    /**
     * Get a room with id
     *
     * @param id the id of the room
     * @return return a room or throws DataNotFoundException
     */
    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RoomEntity> getRoom(@PathVariable("id") int id) {
        return new ResponseEntity<>(roomService.getRoom(id), HttpStatus.OK);
    }


    /**
     * Deletes a room wit id
     *
     * @param id the id of the room to delete
     * @return a status of NO_CONTENT if successful or NOT_FOUND if the room does not exist
     */
    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int id) {
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
