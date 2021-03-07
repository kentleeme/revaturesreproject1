package com.revature.services;

import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.filters.DurationFilter;
import com.revature.models.Instructor;
import com.revature.models.Student;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private HttpServletRequest req;
	
	public Set<User> findAll() {
		return userDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public User findById(int id) {
		return userDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No user found with id " + id));
	}
	
	public User findByUsername(String username) {
		return userDAO.findByUsername(username)
				.orElseThrow( () -> new UserNotFoundException("No user found with username " + username));
	}
	
	public User insert(User u) {
		//return userDAO.save(u);
		
		  User newUser = userDAO.save(u); 
		  if (String.valueOf(newUser.getRole()).equals("Instructor")) { 
			  Instructor instr =  new Instructor();
			  instr.setId(newUser.getId());
			  instructorService.insert(instr);
		  
		  } else if (String.valueOf(u.getRole()).equals("Student")) { 
			  Student stu = new Student(); 
			  stu.setId(newUser.getId());
			  studentService.insert(stu); 
		  } 
		  MDC.clear();
		  return newUser;
		 
	}
	
	public void delete(int id) {
		User u = userDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No user found with id " + id));;
		userDAO.delete(u);
	}
	
	public User update(User u) {
		return userDAO.save(u);
	}
	
	public User login(String username, String password) {
		User exists = userDAO.findByUsername(username)
							.orElseThrow(() -> new UserNotFoundException(String.format("No User with username = %s", username)));
		
		if (exists.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", exists);
			log.info("Login Successfully as {}", username);
			MDC.clear();
			return exists;
		} else {
			throw new WrongPasswordException("Username or Password does not match the record.");
		}			
		
	}
	
	public void logout() {
		
		HttpSession session = req.getSession(false);
		
		if(session == null) {
			log.info("Logout Successfully");
			MDC.clear();
			return;
		}
		log.info("Logout Successfully");
		MDC.clear();
		session.invalidate();
	}
}