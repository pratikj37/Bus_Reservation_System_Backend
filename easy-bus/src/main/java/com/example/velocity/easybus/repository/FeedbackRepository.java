package com.example.velocity.easybus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.velocity.easybus.model.Feedback;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

}
