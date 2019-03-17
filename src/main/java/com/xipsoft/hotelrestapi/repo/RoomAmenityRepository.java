package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.RoomAmenityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface RoomAmenityRepository extends CrudRepository<RoomAmenityEntity,Integer> {
    List<RoomAmenityEntity> findByRoomId(int roomId);
}
