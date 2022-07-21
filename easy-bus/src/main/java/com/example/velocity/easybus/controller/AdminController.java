package com.example.velocity.easybus.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.velocity.easybus.model.Booking;
import com.example.velocity.easybus.model.Guest;
import com.example.velocity.easybus.model.User;
import com.example.velocity.easybus.repository.BookingRepository;
import com.example.velocity.easybus.repository.GuestBookingRepository;
import com.example.velocity.easybus.repository.UserRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/revenue")
public class AdminController {
	
	@Autowired
	private BookingRepository bookrepo;
	
	@Autowired
	private GuestBookingRepository ubrepo;
	
	@Autowired
	private UserRepository urepo;
	
	
	//Get : http://localhost:9090/bus/revenue/profit
	@GetMapping("/profit")
	public long getProfit()
	{
		List<Booking> allBookings= bookrepo.findAll();
		List<Long> allProfits = new ArrayList<Long>();
		for(Booking b:allBookings) {
			allProfits.add((long) b.getTotalCost());

		}
		//System.out.println(allProfits);
		
		
		List<Guest> allUnBookings=ubrepo.findAll();
		List<Long> allProfits2=new ArrayList<Long>();
		for(Guest b:allUnBookings) {
			allProfits2.add((long) b.getTotalCost());

		}
		
		long sum=0;
		long sum1=0;
		for (int  i = 0; i < allProfits.size(); i++)
            sum += allProfits.get(i);
		//System.out.println(sum);
		
		for (int  j = 0; j < allProfits2.size(); j++)
            sum1+= allProfits2.get(j);
	//	System.out.println(sum1);
		
		long totalRevenue=sum+sum1;
		
		return totalRevenue;
	}
	
	/*@GetMapping("/usercount/{email}")
	public int userCount(@PathVariable(value="email") String email)
	{
		
		Integer count=bookrepo.findBookingCountOfUser(email);
		return count;
	}*/
	
	
	@GetMapping("/inactive")
	public List<User> inactive(){
		List<User> allUsers = urepo.findAll();
		List<User> inactive = new ArrayList<User>();
		for(User u:allUsers) {
			int count = bookrepo.findBookingCountOfUser(u.getEmail());
			
			if(count==0)
				inactive.add(u);
		}
		return inactive;
	}

}
