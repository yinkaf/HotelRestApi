package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.domain.HotelAmenityEntity;
import com.xipsoft.hotelrestapi.domain.HotelEntity;
import com.xipsoft.hotelrestapi.resource.AmenityList;
import com.xipsoft.hotelrestapi.resource.HotelSearchParam;
import com.xipsoft.hotelrestapi.service.HotelService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/hotels", produces = {"application/json"})

@Api(value="/api/hotels",description="Hotels Resource",produces ="application/json")
public class HotelRestController {

    private HotelService hotelService;

    @Autowired
    public HotelRestController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Creates a Hotel
     *
     * @param hotel the hotel data
     * @return the created hotel and a status of CREATED
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create a new Hotel",response=HotelEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=201,message="Hotel Created",response=HotelEntity.class),
            @ApiResponse(code=500,message="Internal Server Error")
    })
    public ResponseEntity<HotelEntity> newHotel(@Valid @RequestBody @ApiParam(name = "Hotel",value = "Hotel Resource",required = true) HotelEntity hotel) {
        return new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.CREATED);

    }

    /**
     * Add amenities to a hotel
     *
     * @param id        the id of the hotel, this will throw a DataNotFoundException if the hotel does not exist
     * @param amenities the list of amenities to add
     * @return the list am amenities added
     */
    @PostMapping(path = "{id}/amenities")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<HotelAmenityEntity>> addAmenities(@PathVariable("id") int id, @RequestBody @Valid AmenityList amenities) {

        return new ResponseEntity<>(hotelService.addEmenities(id, amenities), HttpStatus.CREATED);
    }

    /**
     * Get the list of amenities for a hotel, this will throw a EmptyResultDataAccessException if the hotel does not exist
     *
     * @param id the id of the hotel
     * @return the list of the added room amenities
     */
    @GetMapping(path = "{id}/amenity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HotelAmenityEntity>> getAmenities(@PathVariable("id") int id) {
        return new ResponseEntity<>(hotelService.getAmenities(id), HttpStatus.OK);

    }

    /**
     * Search for hotels by name, cityCode and description
     * the search parameters is case insensitive and also will match on contains
     *
     * @param param    the search parameter
     * @param pageable the paging data
     * @return the list of Hotels that match the parameter
     */
    @PostMapping(path = "search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<HotelEntity>> SearchHotel(@RequestBody HotelSearchParam param, Pageable pageable) {
        return new ResponseEntity<>(hotelService.search(param, pageable), HttpStatus.OK);

    }

    /**
     * Get all hotels
     *
     * @param pageable the paging data
     * @return the list of hotels
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<HotelEntity>> GetAll(Pageable pageable) {
        return new ResponseEntity<>(hotelService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Delete an hotel by id
     *
     * @param id the id of the hotel to delete
     * @return returns NO_CONTENT if delete is successfull otherwise it will throw EmptyResultDataAccessException is the hotel does not exist
     */

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        hotelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
