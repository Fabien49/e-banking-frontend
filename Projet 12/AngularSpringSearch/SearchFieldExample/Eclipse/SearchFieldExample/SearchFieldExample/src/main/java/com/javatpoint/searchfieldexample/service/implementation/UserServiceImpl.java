package com.javatpoint.searchfieldexample.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatpoint.searchfieldexample.DAO.interfaces.UserDAO;
import com.javatpoint.searchfieldexample.entity.User;
import com.javatpoint.searchfieldexample.service.interfaces.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Transactional
	public int SaveUser(User user) {
		return userDAO.SaveUser(user) ;
	}

	@Transactional
	public List<User> getFilteredData(User user) {
		return userDAO.getFilteredData(user);
	}

}
