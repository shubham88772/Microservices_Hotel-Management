package com.hotel.service;

import com.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {
    //Create
    Hotel create(Hotel hotel);
    //get all
    List<Hotel> getAllHotel();
    //Get single
    Hotel get(String id);
}
