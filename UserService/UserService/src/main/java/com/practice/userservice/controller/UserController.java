package com.practice.userservice.controller;

import com.practice.userservice.entities.User;
import com.practice.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=userService.saveUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user=userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    //Rating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){
//        logger.info("Fallback is executed because service is down:");
        log.info("Fallback is executed because service is down:",ex.getMessage());
        User user= User.builder()
                .email("shubhamkr@gmail.com")
                .about("Dummy user because of down service")
                .userId("13432")
                .name("Shubham")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping
    public  ResponseEntity<List<User>> getAllUser(){
        List<User> allUser=userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
