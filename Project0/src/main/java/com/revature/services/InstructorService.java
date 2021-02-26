package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.Instructor;
import com.revature.repositories.InstructorDAO;
import com.revature.repositories.InstructorDAOinterface;

public class InstructorService {

	private InstructorDAOinterface instrDAO = new InstructorDAO();
	
	private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
	
	public List<Instructor> findAllInstructors(){
		return instrDAO.findAllInstructors();
	}
	
	public Instructor findAllCourses(int id) {
		MDC.put("event", "Query");
		MDC.put("userId", Integer.toString(id));
		return instrDAO.findAllCourses(id);
	}
	
	public Instructor findByID(int id) {
		return instrDAO.findByID(id);
	}
	
	public Instructor update(Instructor instructor) {
		MDC.put("event", "Update");
		MDC.put("userId", Integer.toString(instructor.getInstructorID()));
		return instrDAO.update(instructor);
	}
}
