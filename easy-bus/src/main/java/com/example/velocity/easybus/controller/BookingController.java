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
import com.example.velocity.easybus.repository.BookingRepository;
import com.example.velocity.easybus.repository.BusRepository;
import com.example.velocity.easybus.repository.GuestBookingRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/bookin")
public class BookingController {
	
	@Autowired
	private BusRepository brepo;
	@Autowired
	private BookingRepository bookrepo;
	
	@Autowired
	private GuestBookingRepository ubrepo;
	
	
	
	//Post : http://localhost:9090/bus/bookin/makebook/62be92b7b998325d41341f45/rps@gmail.com
		@PostMapping("/makebook/{id}/{email}")
		public Booking saveBooking(@PathVariable(value="id") String bId,@PathVariable(value="email") String email,@Validated @RequestBody Booking b) throws ResourceNotFoundException
		{
			Bus busd=brepo.findById(bId).orElseThrow(()-> new ResourceNotFoundException("Booking not found for this Id :" +bId));
			double price=busd.getPrice();
			
			int seatsAvailable=busd.getSeatsAvailable();//get no of seats in bus
			
			String departureLocation=busd.getDepartureLocation();
			String arrivalLocation=busd.getArrivalLocation();
			
			String departureTime=busd.getDepartureTime();
			String arrivalTime=busd.getArrivalTime();
			String busType=busd.getBusType();
			Date date = new Date();
			
			//System.out.println(email);
			
			//System.out.println(price);
			
			Booking bookd=new Booking();
			
			bookd.setBusId(bId);
			bookd.setPassengerName(b.getPassengerName());
			bookd.setNumberOfseats(b.getNumberOfseats());
			bookd.setPrice(price);
			bookd.setTotalCost(b.getTotalCost());
			bookd.setEmail(email);
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
			bookd.setSeat(b.getSeat());
			bookd.setStatus("true");
			bookd.setJourneyDate(busd.getDate());
			int temp=b.getNumberOfseats();
			int temp2=seatsAvailable-temp;
			busd.setSeatsAvailable(temp2); // Decreasing seats in bus
			
			brepo.save(busd);
			bookrepo.save(bookd);
			return b;
		}
	
	
	
	
	//GET : http://localhost:9090/bookin/makebook/rps@gmail.com
	
	@GetMapping("/makebook/{email}")
	public List<Booking> getBookingByEmail(@PathVariable(value="email") String email)
	{
		//invokes findByMadein() custom method of MongoRespository
		return (List<Booking>) bookrepo.findByEmail(email);  //invokes findAll() method of Mongo repository
	}
	
	
	
	// For Cancellation of the booked ticket
	
		//DELETE - http://localhost:9090/bus/bookin/makebook/62c056eb9a5fd053393cd841
	@DeleteMapping("/makebook/{id}")
	public Map<String,Boolean> deleteBooking(@PathVariable(value="id") String bId)
			throws ResourceNotFoundException
	{
		Booking book=bookrepo.findById(bId).
				orElseThrow(()-> new ResourceNotFoundException
						("Booking not found for this Id :" +bId));
		
		bookrepo.delete(book); //	delete() method is predefined in MongoRepository
		
		Map<String,Boolean> response=new HashMap<>();
		response.put("Delete the Booking",Boolean.TRUE);
		return response;
	}
	 
	//Post : http://localhost:9090/bookin/makebook
		@PostMapping("/makebook")
		public Booking saveBus(@Validated @RequestBody Booking booking)
		{
			return bookrepo.save(booking);
		}
		
		@GetMapping("/makebook")
		public List<Booking> getAllBookings()
		{
			return bookrepo.findAll();  //invokes findAll() method of Mongo repository
		}
		
		
		@GetMapping("/latest")
		public Booking getLatestBooking()
		{
			List<Booking> allBooking=bookrepo.findAll();  //invokes findAll() method of Mongo repository
			System.out.println(allBooking);
			int temp=allBooking.size();
			System.out.println(allBooking.get(temp-1));
			Booking latest=allBooking.get(temp-1);
			return latest;

		}

		
		
	}

