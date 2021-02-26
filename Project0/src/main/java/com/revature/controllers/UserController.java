package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller {

	private UserService userService = new UserService();
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private Handler login = ctx -> {
		
		MDC.put("event", "Log In");		
		
		User u = ctx.bodyAsClass(User.class);
		MDC.put("userId", Integer.toString(u.getUserid()));
		
		String username = u.getUsername();
		String password = u.getPassword();
		UserRole role = u.getRole();
		log.info("Signing In..");
		
		if (userService.findByID(u.getUserid()).getUsername().equals(username) && userService.findByID(u.getUserid()).getPassword().equals(password)) {
			
			ctx.req.getSession();
			ctx.sessionAttribute("username", username);
			ctx.sessionAttribute("password", password);
			ctx.sessionAttribute("role", role);
			
			String name = ctx.sessionAttribute("username");
			ctx.json("Logged in successfully. Hello, "+name);
			ctx.status(200);
			log.info("Sign In Successfully");
			
		}
		else {
			ctx.json("Incorrect Username or Password");
			ctx.status(400);
			log.info("Sign In Unsuccessfully");
			
		}
		MDC.clear();
	};

	private Handler getAllUsers = ctx -> {

		ctx.json(userService.findAll());
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler getSingleUser = ctx -> {
		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(userService.findByID(id));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler createUser = ctx -> {
		User u = ctx.bodyAsClass(User.class);
		
		u = userService.register(u);
		
		ctx.json(u);
		ctx.status(201); 
		
		MDC.clear();
	};
	
	private Handler deleteUser = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(userService.delete(id));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("/users", this.getAllUsers);
		
		app.get("/users/:id", this.getSingleUser);
		
		app.post("/users", this.createUser);
		
		app.delete("/users/:id", this.deleteUser);
		
		app.post("/login", this.login);
		
	}

	
}
