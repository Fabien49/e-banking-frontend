package com.macrosoftas.testHibernate.service;

import com.macrosoftas.testHibernate.model.Profs;

public interface UserService {
	public Profs findUserByEmail(String email);
	public void saveUser(Profs user);
}
