package com.javatpoint.searchfieldexample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userId")
	private int userId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email_id" )
	public String emailId;
	
	@Column(name="qualification")
	public String qualification;
	
	public User() {}

	public User(int userId, String name, String emailId, String qualification) {
		super();
		this.userId = userId;
		this.name = name;
		this.emailId = emailId;
		this.qualification = qualification;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", emailId=" + emailId + ", qualification=" + qualification
				+ "]";
	}
	
	
	
	

}
