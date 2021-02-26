package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.Enrollment;
import com.revature.models.Student;
import com.revature.services.EnrollmentService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EnrollmentController implements Controller {

	private EnrollmentService enService = new EnrollmentService();
	
	private Handler getAllEnrollments = ctx -> {
		
		ctx.json(enService.findAll());
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler getSingleEnrollment = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int enid = Integer.parseInt(idString);
		
		ctx.json(enService.findByID(enid));
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler updateEnrollment = ctx -> {
		
		Enrollment en = ctx.bodyAsClass(Enrollment.class);
		
		en = enService.updateEnrollment(en);
		
		ctx.json(en);
		ctx.status(200); 
		
		MDC.clear();
	};
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("/enrollments", this.getAllEnrollments);
		
		app.get("/enrollments/:id", this.getSingleEnrollment);
		
		app.post("/enrollments", this.updateEnrollment);
	}

}
