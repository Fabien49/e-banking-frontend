package com.javatpoint.searchfieldexample.DAO.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javatpoint.searchfieldexample.DAO.interfaces.UserDAO;
import com.javatpoint.searchfieldexample.entity.User;


@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public int SaveUser(User user) {
		
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			int userId = (Integer) session.save(user);
			return userId;
		}
		catch(Exception exception)
		{
			
			System.out.println("Excption while saving data into DB " + exception);
			return 0;
		}
		finally
		{
			session.flush();
		}
		
	}

	public List<User> getFilteredData(User user) {
		
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			ArrayList<Object> list_field = new ArrayList<Object>();
			ArrayList<Object> list_value = new ArrayList<Object>();
			
			if(user.getName()==null || user.getName()==""){}else{list_field.add("name");list_value.add(user.getName());}
			if(user.getEmailId()==null || user.getEmailId()==""){}else{list_field.add("emailId");list_value.add(user.getEmailId());}

			switch (list_field.size()) {
			
			case 0:
					Query<User> query0 = session.createQuery("from User");
					return query0.list();
			case 1:
				
				Query query1 = session.createQuery("from User where " + list_field.get(0) +" = :value0");
				query1.setParameter("value0", list_value.get(0));
				return query1.list();
				
			case 2:
				Query query2 = session.createQuery("from User where " + list_field.get(0) +" =:value0 and " + list_field.get(1) + " =:value1");
				query2.setParameter("value0", list_value.get(0));
				query2.setParameter("value1", list_value.get(1));
				return query2.list();
				
			} 
						
			return null;
		}
		
		catch(Exception exception)
		{
			System.out.println("Error while getting Filtered Data :: " + exception.getMessage());
			return null;
		}
		finally
		{
			session.flush();
		}
		
		
	}

}
