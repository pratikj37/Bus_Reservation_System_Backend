package com.example.velocity.easybus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.velocity.easybus.model.Guest;

public interface GuestBookingRepository extends MongoRepository<Guest, String> {

}
