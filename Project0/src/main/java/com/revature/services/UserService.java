package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.InstructorDAO;
import com.revature.repositories.InstructorDAOinterface;
import com.revature.repositories.StudentDAO;
import com.revature.repositories.StudentDAOinterface;
import com.revature.repositories.UserDAO;
import com.revature.repositories.UserDAOinterface;

public class UserService {

	private UserDAOinterface userDAO = new UserDAO();
	private StudentDAOinterface stdntDAO = new StudentDAO();
	private InstructorDAOinterface instrDAO = new InstructorDAO();
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	// If the login is successful, return the relevant User data
	// Otherwise, might throw a custom exception, to be handled elsewhere
	// Or maybe just return null
	public User login(String username, String password) {
		
		return null;
	}
	
	public User register(User u) {
		
		MDC.put("event", "Register");
		
		log.info("Registering new User");
		
		if (u.getUserid() != 0) {
			
			throw new RegisterUserFailedException("Received User object did not have ID = 0");
			
		}
		
		int generatedID = userDAO.insert(u);
		
		if (generatedID != -1 && generatedID != u.getUserid()) {
			
			u.setUserid(generatedID);
			
			if (String.valueOf(u.getRole()).equals("Instructor")) {
				
				instrDAO.insert(generatedID);
				
			} else if (String.valueOf(u.getRole()).equals("Student")) {
				
				stdntDAO.insert(generatedID);
			}
			
			
		} else {
			
			throw new RegisterUserFailedException("Failed to insert the User record");
		}		
		
		MDC.put("userId", Integer.toString(u.getUserid()));
		log.info("Successfully registered User");		
		
		return u;
	}
	
	public List<User> findAll() {
		
		return userDAO.findAll();
	}

	public User findByID(int id) {
		
		return userDAO.findByID(id);
	}
	
	public boolean delete(int id) {
		
		return userDAO.delete(id);
	}
}
