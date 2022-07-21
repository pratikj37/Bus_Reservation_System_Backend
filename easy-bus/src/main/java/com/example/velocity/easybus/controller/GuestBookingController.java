package com.example.velocity.easybus.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.velocity.easybus.exception.ResourceNotFoundException;
import com.example.velocity.easybus.model.Booking;
import com.example.velocity.easybus.model.Bus;
import com.example.velocity.easybus.model.Guest;
import com.example.velocity.easybus.repository.BusRepository;
import com.example.velocity.easybus.repository.GuestBookingRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/unauthorize")
public class GuestBookingController {
	
	@Autowired
	private BusRepository brepo;
	@Autowired
	private GuestBookingRepository ubrepo;
	
	//Post : http://localhost:9090/bus/bookin/makebook/62be92b7b998325d41341f45
	@PostMapping("/book/{id}")
	public Booking saveBooking(@PathVariable(value="id") String bId,@Validated @RequestBody Booking b) throws ResourceNotFoundException
	{
		Bus busd=brepo.findById(bId).orElseThrow(()-> new ResourceNotFoundException("Booking not found for this Id :" +bId));
		
		int seatsAvailable=busd.getSeatsAvailable();//get no of seats in bus
		
		String departureLocation=busd.getDepartureLocation();
		String arrivalLocation=busd.getArrivalLocation();
		
		String departureTime=busd.getDepartureTime();
		String arrivalTime=busd.getArrivalTime();
		String busType=busd.getBusType();
		Date date = new Date();
		
		
		Guest bookd=new Guest();
		
		bookd.setBusId(bId);
		bookd.setPassengerName(b.getPassengerName());
		bookd.setNumberOfseats(b.getNumberOfseats());

		bookd.setTotalCost(b.getTotalCost());
		bookd.setEmail(b.getEmail());
		bookd.setDepartureLocation(departureLocation);
		bookd.setArrivalLocation(arrivalLocation);
		bookd.setArrivalTime(arrivalTime);
		bookd.setDepartureTime(departureTime);
		bookd.setPhoneNumber(b.getPhoneNumber());
		bookd.setCreditDebitCard(b.getCreditDebitCard());
		bookd.setNameOnCard(b.getNameOnCard());
		bookd.setCvv(b.getCvv());
		bookd.setExpMonth(b.getExpMonth());
		bookd.setExpYear(b.getExpYear());
		bookd.setDateOfBooking(date);
		bookd.setBusType(busType);
		bookd.setTotalCost(b.getTotalCost());
		bookd.setJourneyDate(busd.getDate());
		int temp=b.getNumberOfseats();
		int temp2=seatsAvailable-temp;
		busd.setSeatsAvailable(temp2); // Decreasing seats in bus
		
		brepo.save(busd);
		ubrepo.save(bookd);
		return b;
	}


	@DeleteMapping("/book/{id}")
	public Map<String,Boolean> deleteBooking(@PathVariable(value="id") String bId)
			throws ResourceNotFoundException
	{
		Guest book=ubrepo.findById(bId).
				orElseThrow(()-> new ResourceNotFoundException
						("Booking not found for this Id :" +bId));
		
		ubrepo.delete(book); //	delete() method is predefined in MongoRepository
		
		Map<String,Boolean> response=new HashMap<>();
		response.put("Delete the Booking",Boolean.TRUE);
		return response;
	}
	
	//GET: http://localhost:9090/bus/unauthorize/latestbooking
	@GetMapping("/latestbooking")
	public Guest getLatestBooking()
	{
		List<Guest> allBooking=ubrepo.findAll();  //invokes findAll() method of Mongo repository
		System.out.println(allBooking);
		int temp=allBooking.size();
		System.out.println(allBooking.get(temp-1));
		Guest latest=allBooking.get(temp-1);
		return latest;
		
	}
	
	@GetMapping("/book")
	public List<Guest> getAllGuest()
	{
		return ubrepo.findAll();								//invokes findByMadein() custom method of MongoRespository
		 							 //invokes findAll() method of Mongo repository
	}
}
