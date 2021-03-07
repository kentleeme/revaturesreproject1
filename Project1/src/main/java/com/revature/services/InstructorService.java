package com.revature.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Instructor;
import com.revature.repositories.InstructorDAO;

@Service
public class InstructorService {

	@Autowired
	private InstructorDAO instructorDAO;
	
	public Set<Instructor> findAll() {
		return instructorDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public Instructor findById(int id) {
		return instructorDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No instructor found with id " + id));
	}
	
	public Instructor insert(Instructor i) {
		return instructorDAO.save(i);
	}

	public Instructor update(Instructor i) {
		MDC.clear();
		return instructorDAO.save(i);
	}
	
	public void delete(Instructor i) {
		instructorDAO.delete(i);
	}
}
