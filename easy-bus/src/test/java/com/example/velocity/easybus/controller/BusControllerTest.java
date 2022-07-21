package com.example.velocity.easybus.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.velocity.easybus.exception.ResourceNotFoundException;
import com.example.velocity.easybus.model.User;
import com.example.velocity.easybus.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserRepository urepo;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSaveUser() throws ParseException  {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		
		User user = new User("user21", "Sanjana", "sanju@gmail.com", "2022-07-15", "testing", "8372833292");

		when(urepo.save(any(User.class))).thenReturn(user);

		String response = userController.saveUser(user);

		assertEquals("User registered Successfully", response);
	}

	@Test
	void testGetAllUsers() {
		when(urepo.findAll()).thenReturn(Stream.of(
				new User("user21", "Sanjana", "sanju@gmail.com", "2022-07-15", "testing", "8372833292"),
				new User("user21", "Sanjana", "sanju@gmail.com", "2022-07-15", "testing", "8372833292"))
				.collect(Collectors.toList()));
		assertEquals(2, userController.getAllUsers().size());
	}

	/*@Test
	void testLoginUser() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginAdmin() {
	}
		

	@Test
	void testUpdateDetails() {
		fail("Not yet implemented");
	}*/

	@Test
	void testGetUserById() throws ResourceNotFoundException {
		User user1 = new User("user21", "Sanjana", "gourishettysanju@gmail.com", "2022-07-10", "testing", "9381156109");

		when(urepo.save(any(User.class))).thenReturn(user1);
		ResponseEntity<User> u =userController.getUserById("user21");
	    assertEquals("Sanjana", u);
    }
}