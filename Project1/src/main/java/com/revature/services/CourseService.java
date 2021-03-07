package com.revature.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.CourseNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Course;
import com.revature.models.Instructor;
import com.revature.repositories.CourseDAO;
import com.revature.repositories.InstructorDAO;

@Service
public class CourseService {

	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private InstructorDAO instructorDAO;
	
	public Set<Course> findAll() {
		return courseDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public Course findById(int id) {
		return courseDAO.findById(id)
				.orElseThrow( () -> new CourseNotFoundException("No course found with id " + id));
	}

	public Course insert(Course c) {
		MDC.clear();
		return courseDAO.save(c);
	}
	
	public Course update(Course c) {
		return courseDAO.save(c);
	}
	
	public void delete(int cid) {
		Course c = courseDAO.findById(cid)
				.orElseThrow( () -> new CourseNotFoundException("No course found with id " + cid));
		courseDAO.delete(c);
		MDC.clear();
	}
	
	public Set<Course> findByInstructorAssignedId(int id) {
		return courseDAO.findByInstructorAssignedId(id);
	}
	
	public Set<Course> findByStudentsEnrolledId(int id) {
		return courseDAO.findByStudentsEnrolledId(id);
	}
	
	public Course assignInstructor(int id, Instructor i) {
		Course c = courseDAO.findById(id)
				.orElseThrow( () -> new CourseNotFoundException("No course found with id " + id));
		c.setInstructorAssigned(i);		
		Instructor instr = instructorDAO.findById(i.getId())
				.orElseThrow( () -> new UserNotFoundException("No instructor found with id " + i.getId()));
		instr.getCoursesAssigned().add(c);
		MDC.clear();
		return courseDAO.save(c);
	}
}
