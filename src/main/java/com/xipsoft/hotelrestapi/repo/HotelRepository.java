package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.HotelEnitity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface HotelRepository extends PagingAndSortingRepository<HotelEnitity,Integer> {
}
