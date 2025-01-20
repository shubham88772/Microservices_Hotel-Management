package com.practice.rating.repositories;

import com.practice.rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepository extends JpaRepository<Rating,String> {
    //custom finder methods
//    @Query("select q from microservices_ratings q where q.userId = :userId")
    public List<Rating> findByUserId(String userId);

//    @Query("select q from microservices_ratings q where q.hotelId = :hotelId")
    public List<Rating> findByHotelId(String hotelId);


}
