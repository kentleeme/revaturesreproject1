package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Student;

public interface StudentDAO extends JpaRepository<Student, Integer>{

	public Set<Student> findByCoursesTakenCourseId(int id);
	
	//public float calculateGPA(int id, List<Character> lettergrade);
	
}
