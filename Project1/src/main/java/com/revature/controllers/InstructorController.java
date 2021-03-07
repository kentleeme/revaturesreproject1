package com.revature.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.MDC;
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
import com.revature.models.UserRole;
import com.revature.services.AuthorizationService;
import com.revature.services.CourseService;
import com.revature.services.InstructorService;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<Set<Instructor>> findAll() {
		
		Set<Instructor> allInstructors = instructorService.findAll();
		
		if(allInstructors.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allInstructors);
	}
	
	@GetMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Instructor})
	public ResponseEntity<Instructor> findById(@PathVariable("id") int id) {
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(instructorService.findById(id));
	}
	
	@GetMapping("/{id}/courses")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Instructor})
	public ResponseEntity<Set<Course>> findAllCourses(@PathVariable("id") int id) {
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(courseService.findByInstructorAssignedId(id));
	}
	
	@PutMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Instructor})
	public ResponseEntity<Instructor> update(@PathVariable("id") int id, @Valid @RequestBody Instructor i){
		MDC.put("event", "Instructor Update");
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(instructorService.update(i));
	}
	
	@PostMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<Instructor> insert(@Valid @RequestBody Instructor i) {
		return ResponseEntity.ok(instructorService.insert(i));
	}
	
	@DeleteMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<String> delete(@Valid @RequestBody Instructor i) {
		return ResponseEntity.ok("Successfully delete Instructor");
	}
}
