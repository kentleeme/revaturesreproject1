package com.revature.services;

import java.util.List;

import org.slf4j.MDC;

import com.revature.models.Course;
import com.revature.repositories.CourseDAO;
import com.revature.repositories.CourseDAOinterface;

public class CourseService {
	
	CourseDAOinterface cService = new CourseDAO();  

	public List<Course> findAll(){
		return cService.findAll();
	}
	
	public Course findByID(int id) {
		return cService.findByID(id);
	}
	
	public List<Course> findByCourseName(String cname){
		return cService.findByCourseName(cname);
	}
	
	public Course listOfStudents(int cid) {
		return cService.showStudents(cid);
	}
	
	public int insert(Course c) {
		return cService.insert(c);
	}
	
	public boolean delete(int cid) {
		return cService.delete(cid);
	}
	
	public Course update(Course c) {
		return cService.update(c);
	}
}
