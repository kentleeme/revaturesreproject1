package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.revature.models.Student;
import com.revature.models.UserRole;
import com.revature.services.AuthorizationService;
import com.revature.services.CourseService;
import com.revature.services.StudentService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	private static final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<Set<Student>> findAll() {
		
		Set<Student> allStudents = studentService.findAll();
		
		if(allStudents.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allStudents);
	}
	
	@GetMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<Student> findById(@PathVariable("id") int id) {
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(studentService.findById(id));
	}
	
	@PutMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<Student> update(@PathVariable("id") int id, @Valid @RequestBody Student s){
		MDC.put("event", "Student Update");
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(studentService.update(s));
	}
	
	@PostMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<Student> insert(@Valid @RequestBody Student s) {
		return ResponseEntity.ok(studentService.insert(s));
	}
	
	@DeleteMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<String> delete(@Valid @RequestBody Student s) {
		return ResponseEntity.ok("Successfully delete Student");
	}
	
	@PostMapping("/{id}/enroll")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<Student> enrollClass(@PathVariable("id") int id, @Valid @RequestBody Course c) {
		MDC.put("event", "Enroll");
		authorizationService.guardByUserId(id);
		log.info("Enroll in a class");
		return ResponseEntity.ok(studentService.enrollClass(id, c));
	}
	
	@DeleteMapping("/{id}/drop")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<Student> dropClass(@PathVariable("id") int id, @Valid @RequestBody Course c) {
		MDC.put("event", "Drop");		
		authorizationService.guardByUserId(id);
		studentService.dropClass(id, c);
		log.info("Drop a Class successfully");
		return ResponseEntity.ok(studentService.dropClass(id, c));
	}
	
	@PostMapping("/{id}/gpa")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<String> calculateGpa(@PathVariable("id") int id, @Valid @RequestBody String s){
		authorizationService.guardByUserId(id);
		
		String[] arrOfStr = s.split(" ");
		List<Character> letterGrades = new ArrayList<>();
		
		for (String a : arrOfStr) {
			letterGrades.add(a.charAt(0));
		}
		
		return ResponseEntity.ok(String.format("Expecting GPA: %.2f", studentService.calculateGpa(id, letterGrades)));
	}
	
	@GetMapping("/{id}/courses")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student})
	public ResponseEntity<Set<Course>> findAllCourses(@PathVariable("id") int id) {
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(courseService.findByStudentsEnrolledId(id));
	}
}
