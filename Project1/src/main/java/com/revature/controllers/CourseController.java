package com.revature.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Course;
import com.revature.models.Instructor;
import com.revature.models.Student;
import com.revature.models.UserRole;
import com.revature.services.CourseService;
import com.revature.services.StudentService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	private static final Logger log = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@GetMapping
	public ResponseEntity<Set<Course>> findAll() {
		
		Set<Course> allCourses = courseService.findAll();
		
		if(allCourses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allCourses);
	}
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable("id") int id) {
		
		return ResponseEntity.ok(courseService.findById(id));
	}
	
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Instructor})
	@GetMapping("/{id}/students")
	public ResponseEntity<Set<Student>> findAllStudents(@PathVariable("id") int id) {		
		return ResponseEntity.ok(studentService.findByCoursesTakenCourseId(id));
	}
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@PutMapping("/{id}")
	public ResponseEntity<Course> update(@PathVariable("id") int id, @Valid @RequestBody Course c){
		return ResponseEntity.ok(courseService.update(c));
	}
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@PostMapping
	public ResponseEntity<Course> insert(@Valid @RequestBody Course c) {
		MDC.put("event", "Create Course");
		log.info("Create New Course");
		return ResponseEntity.ok(courseService.insert(c));
	}
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@PostMapping("/{id}/assign")
	public ResponseEntity<Course> assignInstructor(@PathVariable("id") int id, @Valid @RequestBody Instructor i) {
		MDC.put("event", "Assign");
		log.info("Assign Instructor to a Course");
		return ResponseEntity.ok(courseService.assignInstructor(id, i));
	}
	
	@Authorized(allowedRoles = {UserRole.Admin})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int cid) {
		MDC.put("event", "Delete Course");
		courseService.delete(cid);
		return ResponseEntity.ok("Successfully delete Course");
	}
}
