package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.RoomAmenity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface RoomAmenityRepository extends CrudRepository<RoomAmenity,Integer> {
}
