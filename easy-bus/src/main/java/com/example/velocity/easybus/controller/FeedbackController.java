package com.example.velocity.easybus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.velocity.easybus.model.Feedback;
import com.example.velocity.easybus.repository.FeedbackRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	private FeedbackRepository frepo;
	
	@PostMapping("/feed")
	public Feedback saveFeedback(@Validated @RequestBody Feedback feedback)
	{
		return frepo.save(feedback);
	}
	
	@GetMapping("/feed")
	public List<Feedback> getAllFeedback()
	{
		return frepo.findAll();  //invokes findAll() method of Mongo repository
	}

}
