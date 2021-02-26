package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.MDC;

import com.revature.models.Enrollment;
import com.revature.models.Student;
import com.revature.services.EnrollmentService;
import com.revature.services.StudentService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class StudentController implements Controller {
	
	private StudentService stdntService = new StudentService();
	private EnrollmentService enService = new EnrollmentService();
	
	private Handler getAllStudents = ctx -> {

		ctx.json(stdntService.findAll());
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler getSingleStudent = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(stdntService.findByID(id));
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler updateStudent = ctx -> {
		
		Student stu = ctx.bodyAsClass(Student.class);
		
		stu = stdntService.update(stu);
		
		ctx.json(stu);
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler getAllClasses = ctx -> {

		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		ctx.json(stdntService.showClasses(id));
		ctx.status(200);
		
		MDC.clear();
	};
	
	private Handler enrollClass = ctx -> {
				
		Enrollment en = ctx.bodyAsClass(Enrollment.class);
		
		int number = stdntService.enrollClass(en);
		
		ctx.json(enService.findByID(number));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler dropClass = ctx -> {
		
		String idString = ctx.pathParam("enrollmentid");
		
		int enrollmentid = Integer.parseInt(idString);
		
		ctx.json(stdntService.dropClass(enrollmentid));
		ctx.status(200); 
		
		MDC.clear();
	};
	
	private Handler calculateGPA = ctx -> {
		
		String idString = ctx.pathParam("id");
		
		int id = Integer.parseInt(idString);
		
		String grades = ctx.body();
		String[] arrOfStr = grades.split(" ");
		List<Character> letterGrades = new ArrayList<>();
		
		for (String a : arrOfStr) {
			letterGrades.add(a.charAt(0));
		}
		ctx.json(stdntService.calculateGpa(id, letterGrades));
		ctx.status(200); 
		
		MDC.clear();
	};

	@Override
	public void addRoutes(Javalin app) {
		app.get("/students", this.getAllStudents);
		
		app.get("/students/:id", this.getSingleStudent);
		
		app.patch("/students/:id", this.updateStudent);
		
		app.get("/students/:id/classes", this.getAllClasses);
		
		app.delete("/students/:enrollmentid", this.dropClass);
		
		app.post("/students/enroll", this.enrollClass);
		
		app.get("/students/:id/gpa", this.calculateGPA);
		
	}

	
}
