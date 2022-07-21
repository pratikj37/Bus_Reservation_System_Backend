package com.example.velocity.easybus.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.velocity.easybus.exception.ResourceNotFoundException;
import com.example.velocity.easybus.model.Bus;
import com.example.velocity.easybus.repository.BusRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/details")
public class BusController {

	@Autowired
	private BusRepository brepo;
	
	// create a new bus
	//POST-  http://localhost:9090/bus/details/createbus
	
	@PostMapping("/createbus")
	public Bus saveBus(@Validated @RequestBody Bus bus)
	{
		return brepo.save(bus);
	}
	
	//get all bus details
	//GET -  http://localhost:9090/bus/details/createbus
	
	@GetMapping("/createbus")
	public List<Bus> getAllBuses()
	{
		return brepo.findAll();  //invokes findAll() method of Mongo repository
	}
	
	
	//PUT - http://localhost:9090/bus/details/createbus/62be92b7b998325d41341f45
	@PutMapping("/createbus/{id}")
	public ResponseEntity<Bus> updateBus(@PathVariable(value="id") String bId,@Validated @RequestBody Bus b) 
			throws ResourceNotFoundException
	{
		Bus bus=brepo.findById(bId).orElseThrow(()-> new ResourceNotFoundException("Bus not found for this Id :" +bId));

		bus.setBusNo(b.getBusNo());
		bus.setDepartureLocation(b.getDepartureLocation());
		bus.setArrivalLocation(b.getArrivalLocation());
		bus.setDepartureTime(b.getDepartureTime());
		bus.setBusType(b.getBusType());
		bus.setDriverContact(b.getDriverContact());
		bus.setPrice(b.getPrice());
		bus.setDate(b.getDate());
		bus.setSeatsAvailable(b.getSeatsAvailable());

		final Bus updateBus=brepo.save(bus);

		return ResponseEntity.ok(updateBus);
	}
	
	
	
	// Get bus by busNo
	//GET - http://localhost:9090/bus/details/getbybusId/62be92b7b998325d41341f45
	@GetMapping("/getbybusId/{id}")
	public ResponseEntity<Bus> getBusById(@PathVariable(value="id") String bId)
			throws ResourceNotFoundException
	{
		Bus bus=brepo.findById(bId).
				orElseThrow(() -> new ResourceNotFoundException
				("Bus Not found for this Id:"+bId));
		
		return ResponseEntity.ok().body(bus);
		
	}
	
	// Get bus by busNo
	// GET - http://localhost:9090/bus/details/getbyid/105
	
	@GetMapping("/getbybusNo/{busNo}")
	public List<Bus> getBusbyBusNo(@PathVariable(value="busNo") int busNo)
	{
		return (List<Bus>) brepo.findBybusNo(busNo);
	}
	
	//get buses by departure and arrival
	// GET - http://localhost:9090/bus/details/find/kurnool/hyderabad
	
	@GetMapping("/find/{departureLocation}/{arrivalLocation}/{date}")
	public List<Bus> getBusbylocation(@PathVariable("departureLocation") String dL,@PathVariable("arrivalLocation") String aL,@PathVariable("date") String date)
	{
		return brepo.findBylocation(dL, aL,date);
	}
	
	//DELETE - http://localhost:9090/bus/details/createbus/62be92b7b998325d41341f45
	@DeleteMapping("/createbus/{id}")
	public Map<String,Boolean> deleteBus(@PathVariable(value="id") String bId)
			throws ResourceNotFoundException
	{
		Bus bus=brepo.findById(bId).
				orElseThrow(()-> new ResourceNotFoundException
						("Bus not found for this Id :" +bId));
		
		brepo.delete(bus); //	delete() method is predefined in MongoRepository
		
		Map<String,Boolean> response=new HashMap<>();
		response.put("Delete the Bus",Boolean.TRUE);
		return response;
	}
	
	
	
	
	
}
