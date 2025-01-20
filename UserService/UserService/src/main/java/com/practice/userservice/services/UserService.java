package com.practice.userservice.services;

import com.practice.userservice.entities.User;

import java.util.List;

public interface UserService {
    //Save User
    User saveUser(User user);
    //get all user
    List<User> getAllUser();
    //get user by id
    User getUser(String userId);
//    //delete user
//    User deleteUser(String userId);

}
