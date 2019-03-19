package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.AmenityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AmenityRepository extends CrudRepository<AmenityEntity,Integer> {
    AmenityEntity findByShortDesc(String desc);
}
