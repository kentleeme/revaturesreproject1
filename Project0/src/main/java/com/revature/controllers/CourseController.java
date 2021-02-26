package com.revature.controllers;

import org.slf4j.MDC;

import com.revature.models.Course;
import com.revature.models.Enrollment;
import com.revature.repositories.CourseDAO;
import com.revature.repositories.CourseDAOinterface;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class CourseController implements Controller {

	private CourseDAOinterface cService = new CourseDAO(); 
	
	private Handler getAllCourses = ctx -> {
		
		ctx.json(cService.findAll());
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler getSingleCourse = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int cid = Integer.parseInt(idString);
		
		ctx.json(cService.findByID(cid));
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler updateCourse = ctx -> {
		
		Course c = ctx.bodyAsClass(Course.class);
		
		c = cService.update(c);
		
		ctx.json(c);
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler addCourse = ctx -> {
		
		Course c = ctx.bodyAsClass(Course.class);
		
		int cid = cService.insert(c);
		
		ctx.json(cService.findByID(cid));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler deleteCourse = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int cid = Integer.parseInt(idString);
		
		ctx.json(cService.delete(cid));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler getAllStudentsInCourse = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int cid = Integer.parseInt(idString);
		
		ctx.json(cService.showStudents(cid));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("/courses", this.getAllCourses);
		
		app.get("/courses/:id", this.getSingleCourse);
		
		app.post("/courses", this.addCourse);
		
		app.patch("/courses", this.updateCourse);
		
		app.delete("/courses/:id", this.deleteCourse);
		
		app.get("/courses/:id/students", this.getAllStudentsInCourse);
		
	}

}
