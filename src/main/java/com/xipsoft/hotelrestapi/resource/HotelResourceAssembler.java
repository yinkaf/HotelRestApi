package com.xipsoft.hotelrestapi.resource;

import com.xipsoft.hotelrestapi.domain.HotelEnitity;
import org.springframework.stereotype.Component;

@Component
public class HotelResourceAssembler extends ResourceAssembler<HotelEnitity,HotelResource> {
    @Override
    public HotelResource toResource(HotelEnitity domainObject) {
        return domainObject == null?null: new HotelResource(domainObject.getId(),domainObject.getName(),domainObject.getDescription(),domainObject.getCityCode());
    }
}
