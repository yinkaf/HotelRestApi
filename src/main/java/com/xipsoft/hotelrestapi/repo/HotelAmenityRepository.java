package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.HotelAmenityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface HotelAmenityRepository extends CrudRepository<HotelAmenityEntity,Integer> {
    List<HotelAmenityEntity> findByHotelId(int id);
}
