package com.revature.repositories;

import java.util.List;

import com.revature.models.Course;
import com.revature.models.Instructor;

public interface InstructorDAOinterface {
	
	public List<Instructor> findAllInstructors();
	public Instructor findAllCourses(int id);
	public Instructor findByID(int id);
	public void insert(int id);
	public Instructor update(Instructor instructor);

}
