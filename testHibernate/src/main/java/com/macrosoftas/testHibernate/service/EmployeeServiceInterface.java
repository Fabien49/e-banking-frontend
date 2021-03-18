package com.macrosoftas.testHibernate.service;

import java.util.Collection;

import com.macrosoftas.testHibernate.model.Cours;


public interface EmployeeServiceInterface {

	public Cours saveEmployee(Cours emp);
	public Boolean deleteEmployee(String empId);
	public Cours editEmployee(Cours emp);
	public Cours findEmployee(String empId);
	public Collection<Cours> getAllEmployees();
}
