package com.revature.services;

import java.util.List;

import org.slf4j.MDC;

import com.revature.models.Enrollment;
import com.revature.repositories.EnrollmentDAO;
import com.revature.repositories.EnrollmentDAOinterface;

public class EnrollmentService {

	EnrollmentDAOinterface enService = new EnrollmentDAO();
	
	public List<Enrollment> findAll(){
		return enService.findAll();
	}
	
	public Enrollment findByID(int enrollmentid) {
		return enService.findByID(enrollmentid);
	}
	
	public Enrollment updateEnrollment(Enrollment en) {
		MDC.put("event", "Update");
		MDC.put("enrollmentID", Integer.toString(en.getEnrollmentID()));
		return enService.updateEnrollment(en);
	}
}
