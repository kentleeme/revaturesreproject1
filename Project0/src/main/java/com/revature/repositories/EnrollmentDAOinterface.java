package com.revature.repositories;

import java.util.List;

import com.revature.models.Course;
import com.revature.models.Enrollment;

public interface EnrollmentDAOinterface {

	public List<Enrollment> findAll();
	public Enrollment findByID(int enrollmentid);
	public Enrollment updateEnrollment(Enrollment en);
	
}
