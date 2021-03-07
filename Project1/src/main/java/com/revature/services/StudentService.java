package com.revature.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.CourseNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Course;
import com.revature.models.Student;
import com.revature.repositories.StudentDAO;
import com.revature.repositories.CourseDAO;

@Service
public class StudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private CourseDAO courseDAO;
	
	public Set<Student> findAll() {
		return studentDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public Student findById(int id) {
		return studentDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No student found with id " + id));
	}
	
	public Student insert(Student s) {
		return studentDAO.save(s);
	}

	public Student update(Student s) {
		return studentDAO.save(s);
	}
	
	public void delete(Student s) {
		studentDAO.delete(s);
		MDC.clear();
	}
	
	public Set<Student> findByCoursesTakenCourseId(int id){
		return studentDAO.findByCoursesTakenCourseId(id);
	}
	
	public float calculateGpa(int id, List<Character> lettergrade) {
		Student stu = studentDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No student found with id " + id));
		float currentGpa = stu.getGpa();
		float count = 0;
		float sum = 0;
		
		for (char grade: lettergrade) {
			switch(grade) {
			case 'A': 
				sum += 4;
				break;
			case 'B':
				sum += 3;
				break;
			case 'C':
				sum += 2;
				break;
			case 'D':
				sum += 1;
				break;
			default:
				break;
			}
			count++;
		}
		
		if (count != 0) {
			float termGpa = sum/count;
		
			float finalGpa = (currentGpa + termGpa) / 2;
			
			if (currentGpa != 0)
				return finalGpa;
			else
				return termGpa;
		}
		else {
			return currentGpa;
		}
	}
	
	public Student enrollClass(int id, Course c) {
		Student stu = studentDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No student found with id " + id));
		stu.getCoursesTaken().add(c);		
		Course co = courseDAO.findById(c.getCourseId())
				.orElseThrow ( () -> new CourseNotFoundException("No course found with id "+c.getCourseId()));
		co.getStudentsEnrolled().add(stu);
		MDC.clear();
		return studentDAO.save(stu);
	}
	
	public Student dropClass(int id, Course c) {
		Student stu = studentDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No student found with id " + id));
		stu.getCoursesTaken().remove(c);
		Course co = courseDAO.findById(c.getCourseId())
				.orElseThrow ( () -> new CourseNotFoundException("No course found with id "+c.getCourseId()));
		co.getStudentsEnrolled().remove(stu);
		MDC.clear();
		return studentDAO.save(stu);
	}
}
