package com.example.velocity.easybus.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="booking")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {
	
	@Id
	private String id;
	
	private String passengerName;
	private int numberOfseats;
	private double price;
	private double totalCost;
	private long phoneNumber;
	private String nameOnCard;
	private int creditDebitCard;
	private int expMonth;
	private int expYear;
	private int cvv;
	private Date dateOfBooking;
	
	private String email;
	private String departureLocation;
	private String arrivalLocation;
	private String busType;
	private String departureTime;
	private String arrivalTime;
	private String busId;
	private String status;
	private String journeyDate;
	public Long seat;
	
	
	
	

	
	
	
	
	
}
