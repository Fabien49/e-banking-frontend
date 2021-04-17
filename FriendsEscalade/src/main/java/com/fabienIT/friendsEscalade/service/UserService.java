package com.fabienIT.friendsEscalade.service;


import com.fabienIT.friendsEscalade.model.User;


public interface UserService {

	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void saveMembre(User user);
	public User findUser(Long id);



}
