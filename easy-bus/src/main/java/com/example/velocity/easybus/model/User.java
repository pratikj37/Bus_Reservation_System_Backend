package com.example.velocity.easybus.model;





import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	
	@Id
	private String id;
	
	private String name;
	@Indexed(unique=true)
	private String email;
	private String dob;
	private String password;
	
	@Indexed(unique=true)
    private String phoneNo;

	
	

	
	
	
	

}
