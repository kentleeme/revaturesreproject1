package com.revature.repositories;

import java.util.List;

import com.revature.models.Course;

public interface CourseDAOinterface {
	
	public List<Course> findAll();
	public Course findByID(int id);
	public Course showStudents(int id);
	public List<Course> findByCourseName(String cname);
	public int insert(Course c);
	public boolean delete(int cid); 
	public Course update(Course c);
	public String getInstructorName(int id);

}
