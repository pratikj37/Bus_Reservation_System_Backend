package com.example.velocity.easybus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.velocity.easybus.exception.ResourceNotFoundException;

import com.example.velocity.easybus.model.User;
import com.example.velocity.easybus.repository.UserRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/usd")
public class UserController {
	
	@Autowired
	private UserRepository urepo;
	
	//When user registers the details 
	
	//POST - http://localhost:9090/bus/usd/registerUser
	@PostMapping("/registerUser")
	public String saveUser(@RequestBody User user)
	{
		 urepo.save(user);
		 return "User registered Successfully";
		
	}

	//When Admin wants to find All the users
	
	//GET - http://localhost:9090/bus/usd/findallUsers
	@GetMapping("/findallUsers")
	public List<User> getAllUsers()
	{
		return urepo.findAll();
	}
	
	
	
	//Login check of user
	
	//POST - http://localhost:9090/bus/usd/userCheck
	@PostMapping("/userCheck")
	public Boolean loginUser(@Validated @RequestBody User user)throws ResourceNotFoundException
	{
		Boolean isLogin=false;

		String email=user.getEmail();
		String password=user.getPassword();

		User u=urepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Unknown User"));

		if(email.equals(u.getEmail()) && password.equals(u.getPassword()))
		{
			isLogin=true;
		}

		return isLogin;
	}

	//Admin Login Check In this case which is static in this case 
	//admin email is Khalid@gmail.com  and password is developer\
	
	//POST - http://localhost:9090/bus/usd/admin
	@PostMapping("/admin")
	public Boolean loginAdmin(@Validated @RequestBody User user) {
		Boolean b = false;
		String email = user.getEmail();
		String password = user.getPassword();
		//User d = urepo.findByEmail(email);

		if (email.equals("Khalid@gmail.com") && password.equals("developer")) {
			b = true;
		}
		return b;
	}
	
	//User can update the details
	
	 //PUT - http://localhost:9090/bus/usd/editdetails/khalid@ims.com
	@PutMapping("/editdetails/{email}")
	public ResponseEntity<User> updateDetails(@PathVariable(value="email") String email,@Validated @RequestBody User u) 
			throws ResourceNotFoundException
	{
		User user=urepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found for this mail :" +email));

		user.setName(u.getName());
		user.setDob(u.getDob());
		user.setPassword(u.getPassword());
		user.setPhoneNo(u.getPhoneNo());

		final User updateDetails=urepo.save(user);

		return ResponseEntity.ok(updateDetails);
	}
	
	// Get the details of particular login user
	//GET - http://localhost:9090/bus/usd/getuserdetails/khalid@ims.com
	@GetMapping("/getuserdetails/{email}")
	public ResponseEntity<User> getUserById(@PathVariable(value="email") String email)
			throws ResourceNotFoundException
	{
		User user=urepo.findByEmail(email).
				orElseThrow(() -> new ResourceNotFoundException
				("User Not found for this Id:"+email));
		
		return ResponseEntity.ok().body(user);
		
	}
	 
}
