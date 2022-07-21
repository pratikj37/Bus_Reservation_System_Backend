package com.example.velocity.easybus.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="feedback")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feedback {

	@Id
	private String id;
	private String email;
	private String comments;
	
	
	
	
}
