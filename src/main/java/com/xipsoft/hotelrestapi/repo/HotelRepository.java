package com.xipsoft.hotelrestapi.repo;

import com.xipsoft.hotelrestapi.domain.HotelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface HotelRepository extends PagingAndSortingRepository<HotelEntity,Integer> , QueryByExampleExecutor<HotelEntity> {
}
