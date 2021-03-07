package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Instructor;

public interface InstructorDAO extends JpaRepository<Instructor, Integer>{

	
}
