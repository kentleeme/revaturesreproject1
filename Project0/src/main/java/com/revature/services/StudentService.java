package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.Course;
import com.revature.models.Enrollment;
import com.revature.models.Student;
import com.revature.repositories.StudentDAO;
import com.revature.repositories.StudentDAOinterface;

public class StudentService {

	private StudentDAOinterface stdntDAO = new StudentDAO();
	
	private static final Logger log = LoggerFactory.getLogger(StudentService.class);
	
	public List<Student> findAll(){
		return stdntDAO.findAll();
	}
	
	public Student findByID(int id) {
		return stdntDAO.findByID(id);
	}
	
	public Student update(Student s) {
		MDC.put("event", "Update");
		MDC.put("userId", Integer.toString(s.getStudentID()));
		return stdntDAO.update(s);
	}
	
	public boolean dropClass(int enrollmentid) {
		MDC.put("event", "Drop");
		MDC.put("enrollmentId", Integer.toString(enrollmentid));
		return stdntDAO.dropClass(enrollmentid);		
	}
	
	public int enrollClass(Enrollment en) {
		MDC.put("event", "Enroll");
		MDC.put("userId", Integer.toString(en.getStudentID()));
		MDC.put("enrollmentId", Integer.toString(en.getEnrollmentID()));
		return stdntDAO.enrollClass(en);
	}
	
	public List<Course> showClasses(int studentid) {
		return stdntDAO.showClasses(studentid);
	}
	
	public float calculateGpa(int id, List<Character> letterGrades) {
		return stdntDAO.calculateGPA(id, letterGrades);
	}
}
