package com.example.velocity.easybus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.velocity.easybus.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	public Optional<User> findByEmail(String email);

}
