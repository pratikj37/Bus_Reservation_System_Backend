package com.example.velocity.easybus.model;






import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="bus")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bus {
	
	@Id
	private String id;
	
	private int busNo;
	private String departureLocation;
	private String arrivalLocation;
	
	private String departureTime;
	private String arrivalTime;
	
	private String date;
	
	private String busType;
	private String driverContact;
	
	private double price;
	private int seatsAvailable;
	

	




	

	
	
	
}
