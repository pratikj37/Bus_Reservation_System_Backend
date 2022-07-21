package com.example.velocity.easybus.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.velocity.easybus.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
	
	List<Booking> findByEmail(String email);

	
	@Query("{'busId' : :#{#busId}, 'journeyDate' : :#{#journeyDate}}")
	List<Booking> getBookedSeats(@Param("busId") String busId, @Param("journeyDate") String journeyDate);
	
	@Query(value="{email:?0}",count=true)
	Integer findBookingCountOfUser(String email);




	
}


