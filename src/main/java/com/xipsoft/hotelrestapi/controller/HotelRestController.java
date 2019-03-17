package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.controller.exception.DataNotFoundException;
import com.xipsoft.hotelrestapi.domain.AmenityEntity;
import com.xipsoft.hotelrestapi.domain.HotelAmenityEntity;
import com.xipsoft.hotelrestapi.domain.HotelEntity;
import com.xipsoft.hotelrestapi.repo.AmenityRepository;
import com.xipsoft.hotelrestapi.repo.HotelAmenityRepository;
import com.xipsoft.hotelrestapi.repo.HotelRepository;
import com.xipsoft.hotelrestapi.resource.AmenityList;
import com.xipsoft.hotelrestapi.resource.HotelSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/hotel", produces = {"application/json"})
public class HotelRestController {
    private HotelRepository hotelRepository;
    private HotelAmenityRepository hotelAmenityRepository;
    private AmenityRepository amenityRepository;

    @Autowired
    public HotelRestController(HotelRepository hotelRepository, AmenityRepository amenityRepository,HotelAmenityRepository hotelAmenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
        this.hotelAmenityRepository = hotelAmenityRepository;
    }

    /**
     * Creates a Hotel
     * @param hotel the hotel data
     * @return the created hotel and a status of CREATED
     */
    @PostMapping
    public ResponseEntity<?> newHotel(@Valid @RequestBody HotelEntity hotel) {

        HotelEntity savedHotel = hotelRepository.save(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);

    }
    /**
     * Add amenities to a hotel
     * @param id the id of the hotel, this will throw a DataNotFoundException if the hotel does not exist
     * @param amenities the list of amenities to add
     * @return the list am amenities added
     */
    @PostMapping(path = "{id}/amenity")
    public ResponseEntity<?> addAmenities(@PathVariable("id") int id, @RequestBody @Valid AmenityList amenities){
        List<HotelAmenityEntity> savedHotelAmenityEntityList = new ArrayList<>();
       HotelEntity hotel =  hotelRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Hotel not found",id));
       amenities.getItems().forEach(amenity -> {
           AmenityEntity amenityEntity = new AmenityEntity();
           amenityEntity.setShortDesc(amenity.getShortDesc());
           amenityEntity.setDescription(amenity.getDescription());
           AmenityEntity savedAmenity =  amenityRepository.save(amenityEntity);
           HotelAmenityEntity hotelAmenityEntity = new HotelAmenityEntity();
           hotelAmenityEntity.setHotel(hotel);
           hotelAmenityEntity.setAmenity(savedAmenity);
           hotelAmenityEntity.setAmount(amenity.getAmount());
           hotelAmenityEntity.setChargeable(amenity.isChargeable());
           HotelAmenityEntity savedHotelAmenity = hotelAmenityRepository.save(hotelAmenityEntity);
           savedHotelAmenityEntityList.add(savedHotelAmenity);
       });
       return new ResponseEntity<>(savedHotelAmenityEntityList,HttpStatus.CREATED);
    }

    /**
     * Get the list of amenities for a hotel, this will throw a EmptyResultDataAccessException if the hotel does not exist
     * @param id the id of the hotel
     * @return the list of the added room amenities
     */
    @GetMapping(path = "{id}/amenity")
    public ResponseEntity<?> getAmenities(@PathVariable("id") int id){
        return new ResponseEntity<>(hotelAmenityRepository.findByHotelId(id),HttpStatus.OK);

    }

    /**
     * Search for hotels by name, cityCode and description
     * the search parameters is case insensitive and also will match on contains
     * @param param the search parameter
     * @param pageable the paging data
     * @return the list of Hotels that match the parameter
     */
    @PostMapping(path = "search")
    public ResponseEntity<Page<HotelEntity>> SearchHotel(@RequestBody HotelSearchParam param, Pageable pageable) {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setName(param.getName());
        hotelEntity.setCityCode(param.getCityCode());
        hotelEntity.setDescription(param.getDescription());
        Example<HotelEntity> example = Example.of(hotelEntity, ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("description", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING)));

        return new ResponseEntity<>(hotelRepository.findAll(example, pageable), HttpStatus.OK);

    }

    /**
     * Get all hotels
     * @param pageable the paging data
     * @return the list of hotels
     */
    @GetMapping
    public ResponseEntity<Page<HotelEntity>> GetAll(Pageable pageable) {
        return new ResponseEntity<>(hotelRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Delete an hotel by id
     * @param id the id of the hotel to delete
     * @return returns NO_CONTENT if delete is successfull otherwise it will throw EmptyResultDataAccessException is the hotel does not exist
     */

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        hotelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
