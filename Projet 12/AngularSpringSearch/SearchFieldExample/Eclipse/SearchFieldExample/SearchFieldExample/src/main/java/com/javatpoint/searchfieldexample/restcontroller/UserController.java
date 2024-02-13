package com.javatpoint.searchfieldexample.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.searchfieldexample.entity.User;
import com.javatpoint.searchfieldexample.service.interfaces.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@PostMapping("/saveUser")
	public int saveAdminDetail(@RequestBody User user) {
		
		return userService.SaveUser(user);
	}
	
	@PostMapping("/filterData")
	public List<User> getFilteredData(@RequestBody User user) {
		
		return userService.getFilteredData(user);
	}
}
