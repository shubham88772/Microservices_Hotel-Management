package com.practice.userservice.impl;

import com.practice.userservice.entities.Hotel;
import com.practice.userservice.entities.Rating;
import com.practice.userservice.entities.User;
import com.practice.userservice.exceptions.ResourceNotFoundException;
import com.practice.userservice.external.services.HotelService;
import com.practice.userservice.repositories.UserRepository;
import com.practice.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server"));
//       //http://localhost:8083/ratings/users/46fb6a48-5f25-4012-bf38-54e3dac59768


//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+ user.getUserId(), ArrayList.class);
        logger.info("{}", ratingsOfUser);

//        logger.info("{}", ratingsOfUser);
        List<Rating> ratingList = ratingsOfUser.stream().map(rating -> {
            //api call to hotel service
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();

            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;

//
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());
//
//
//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            //api call to hotel service to get hotel
//            //http://localhost:8082/hotels/608c1361-c95c-4553-b9ce-f259f24444a3
//
//
////            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
//            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//
//
//            //set the hotel to rating
//            rating.setHotel(hotel);
//
//
//            //return the rating
//            return rating;
//        }).collect(Collectors.toList());
//
//
//        user.setRatings(ratingList);
//        return user;
    }

//    @Override
//    public User deleteUser(String userId) {
//        return userRepository.deleteById(userId);
//    }
}
