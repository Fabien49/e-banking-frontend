package com.javatpoint.searchfieldexample.service.interfaces;

import java.util.List;

import com.javatpoint.searchfieldexample.entity.User;

public interface UserService {
	
	public int SaveUser(User user);
	
	public List<User> getFilteredData(User user);

}
