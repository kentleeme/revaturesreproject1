package com.revature.repositories;

import java.util.List;

import com.revature.models.Course;
import com.revature.models.Enrollment;
import com.revature.models.Student;

public interface StudentDAOinterface {

	public List<Student> findAll();
	public Student findByID(int id);
	public void insert(int id);
	public Student update(Student s);
	
	public boolean dropClass(int enrollmentid);
	public int enrollClass(Enrollment en);
	public List<Course> showClasses(int studentid);
	public float calculateGPA(int id, List<Character> letterGrades);
}
