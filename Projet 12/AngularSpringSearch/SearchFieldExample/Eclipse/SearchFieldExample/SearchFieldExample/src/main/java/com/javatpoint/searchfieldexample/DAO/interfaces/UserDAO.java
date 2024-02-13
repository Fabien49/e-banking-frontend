package com.javatpoint.searchfieldexample.DAO.interfaces;

import java.util.List;

import com.javatpoint.searchfieldexample.entity.User;

public interface UserDAO {
	
	public int SaveUser(User user);
	
	public List<User> getFilteredData(User user);

}
