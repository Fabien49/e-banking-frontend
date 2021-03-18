package com.macrosoftas.testHibernate.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macrosoftas.testHibernate.model.Cours;
import com.macrosoftas.testHibernate.repository.CoursRepository;


@Service
@Transactional
public class EmployeeServiceImplementation implements EmployeeServiceInterface{

	@Autowired
	protected CoursRepository coursRepository;

	public Cours saveEmployee(Cours emp) {
		// TODO Auto-generated method stub
		return coursRepository.save(emp);
	}

	public Boolean deleteEmployee(String empId) {
		// TODO Auto-generated method stub
		Cours temp = coursRepository.getOne(empId);
		if(temp!=null){
			 coursRepository.delete(temp);
			 return true;
		}
		return false;
	}

	public Cours editEmployee(Cours emp) {
		// TODO Auto-generated method stub
		return coursRepository.save(emp);
	}

	public Collection<Cours> getAllEmployees() {
		// TODO Auto-generated method stub
		Iterable<Cours> itr = coursRepository.findAll();
		return (Collection<Cours>)itr;
	}

	public Cours findEmployee(String empId) {
		// TODO Auto-generated method stub
		return coursRepository.getOne(empId);
	}
	

}
