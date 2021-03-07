package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Course;

public interface CourseDAO extends JpaRepository<Course, Integer>{

	public Set<Course> findByInstructorAssignedId(int id);
	
	public Set<Course> findByStudentsEnrolledId(int id);
	
	//public Instructor assignInstructor(int id, Instructor i);
}
