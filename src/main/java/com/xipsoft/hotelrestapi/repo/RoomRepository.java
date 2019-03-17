package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.RoomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface RoomRepository extends CrudRepository<RoomEntity,Integer> {
    Optional<List<RoomEntity>> findByHotelId(int id);
}
