package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.domain.HotelEnitity;
import com.xipsoft.hotelrestapi.repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/hotel", produces = {"application/json"})
public class HotelRestController {
    private HotelRepository hotelRepository;

    @Autowired
    public HotelRestController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newHotel(@Valid @RequestBody HotelEnitity hotel){

        HotelEnitity savedHotel =  hotelRepository.save(hotel);
      return new ResponseEntity<>(savedHotel,HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<HotelEnitity>> GetAll(Pageable pageable){
        return new ResponseEntity<>(hotelRepository.findAll(pageable),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        hotelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
