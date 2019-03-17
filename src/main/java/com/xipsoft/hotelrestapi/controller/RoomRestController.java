package com.xipsoft.hotelrestapi.controller;

import com.xipsoft.hotelrestapi.repo.HotelRepository;
import com.xipsoft.hotelrestapi.repo.RoomRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/room")
public class RoomRestController {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
}
