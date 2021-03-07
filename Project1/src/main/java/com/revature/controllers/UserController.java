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
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.AuthorizationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<Set<User>> findAll() {
		
		Set<User> allUsers = userService.findAll();
		
		if(allUsers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allUsers);
	}
	
	@GetMapping("/{username}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student, UserRole.Instructor})
	public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
		authorizationService.guardByUsername(username);
		return ResponseEntity.ok(userService.findByUsername(username));
	}
	
	@GetMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student, UserRole.Instructor})
	public ResponseEntity<User> findById(@PathVariable("id") int id) {
		authorizationService.guardByUserId(id);
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@DeleteMapping("/{id}")
	@Authorized(allowedRoles = {UserRole.Admin})
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		userService.delete(id);
		return ResponseEntity.ok("Successfully delete User");
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@Valid @RequestBody User u) {
		MDC.put("event", "Register");
		log.info("Register New User");
		return ResponseEntity.ok(userService.insert(u));
	}
	
	@PutMapping
	@Authorized(allowedRoles = {UserRole.Admin, UserRole.Student, UserRole.Instructor})
	public ResponseEntity<User> update(@RequestBody User u) {
		authorizationService.guardByUserId(u.getId());
		
		return ResponseEntity.accepted().body(userService.update(u));
	}
}
