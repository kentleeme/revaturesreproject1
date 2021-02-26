package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.Instructor;
import com.revature.models.Student;
import com.revature.repositories.InstructorDAO;
import com.revature.repositories.InstructorDAOinterface;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class InstructorController implements Controller {

	private InstructorDAOinterface instrDAO = new InstructorDAO();
	
	private Handler getAllInstructors = ctx -> {

		ctx.json(instrDAO.findAllInstructors());
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler getSingleInstructor = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(instrDAO.findByID(id));
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler updateInstructor = ctx -> {
		
		Instructor instr = ctx.bodyAsClass(Instructor.class);
		
		instr = instrDAO.update(instr);
		
		ctx.json(instr);
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler getAllClasses = ctx -> {

		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(instrDAO.findAllCourses(id));
		ctx.status(200);
		
		MDC.clear();
	};
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("instructors", this.getAllInstructors);
		
		app.get("instructors/:id", this.getSingleInstructor);
		
		app.patch("instructors/:id", this.updateInstructor);
		
		app.get("instructors/:id/classes", this.getAllClasses);
		
	}

}
