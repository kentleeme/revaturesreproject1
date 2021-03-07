package com.revature.controllers;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.filters.DurationFilter;
import com.revature.models.LoginTemplate;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginTemplate loginTemplate) {
		MDC.put("event", "Login");
		return ResponseEntity.ok(userService.login(loginTemplate.getUsername(), loginTemplate.getPassword()));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		MDC.put("event", "Logout");
		userService.logout();
		
		return ResponseEntity.accepted().build();
	}
}