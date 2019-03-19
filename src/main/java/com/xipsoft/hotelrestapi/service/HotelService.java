package com.xipsoft.hotelrestapi.service;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelAmenityRepository hotelAmenityRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, AmenityRepository amenityRepository, HotelAmenityRepository hotelAmenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
        this.hotelAmenityRepository = hotelAmenityRepository;
    }

    public HotelEntity createHotel(HotelEntity hotelEntity) {
        return hotelRepository.save(hotelEntity);

    }

    public List<HotelAmenityEntity> addEmenities(int id, AmenityList amenities) {
        List<HotelAmenityEntity> savedHotelAmenityEntityList = new ArrayList<>();
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
                HotelAmenityEntity hotelAmenityEntity = new HotelAmenityEntity();
                hotelAmenityEntity.setHotelId(id);
                hotelAmenityEntity.setAmenity(savedAmenity);
                hotelAmenityEntity.setAmount(amenity.getAmount());
                hotelAmenityEntity.setChargeable(amenity.isChargeable());
                HotelAmenityEntity savedHotelAmenity = hotelAmenityRepository.save(hotelAmenityEntity);
                savedHotelAmenityEntityList.add(savedHotelAmenity);
            });
        }catch (DataIntegrityViolationException ex){
            throw new DataNotFoundException("Hotel not found",id);
        }
        return savedHotelAmenityEntityList;
    }

    public List<HotelAmenityEntity> getAmenities(int id) {
        return hotelAmenityRepository.findByHotelId(id);
    }

    public Page<HotelEntity> search(HotelSearchParam param, Pageable pageable){
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
        return hotelRepository.findAll(example,pageable);

    }

    public Page<HotelEntity> getAll(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public void delete(int id) {
        hotelRepository.deleteById(id);
    }

    public void deleteHotelAmenity(int id) {
        hotelAmenityRepository.deleteById(id);
    }
}
