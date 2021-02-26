package com.revature.models;

import java.util.List;
import java.util.Objects;

public class Instructor {
	
	private int instructorID;
	private String fname;
	private String lname;
	private String email;
	private String deparment;
	private List<Course> courses;
	
	public Instructor() {
		super();
	}

	public Instructor(String fname, String lname, String email, String deparment) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.deparment = deparment;
	}

	public Instructor(List<Course> courses) {
		super();
		this.courses = courses;
	}

	public Instructor(int instructorID, String fname, String lname, String email, String deparment) {
		super();
		this.instructorID = instructorID;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.deparment = deparment;
	}

	public Instructor(int instructorID, String fname, String lname, String email, String deparment,
			List<Course> courses) {
		super();
		this.instructorID = instructorID;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.deparment = deparment;
		this.courses = courses;
	}

	public Instructor(String fname, String lname, String email, String deparment, List<Course> courses) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.deparment = deparment;
		this.courses = courses;
	}

	public int getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(int instructorID) {
		this.instructorID = instructorID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeparment() {
		return deparment;
	}

	public void setDeparment(String deparment) {
		this.deparment = deparment;
	}	
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courses, deparment, email, fname, instructorID, lname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Instructor))
			return false;
		Instructor other = (Instructor) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(deparment, other.deparment)
				&& Objects.equals(email, other.email) && Objects.equals(fname, other.fname)
				&& instructorID == other.instructorID && Objects.equals(lname, other.lname);
	}

	@Override
	public String toString() {
		return "Instructor [instructorID=" + instructorID + ", fname=" + fname + ", lname=" + lname + ", email=" + email
				+ ", deparment=" + deparment + ", courses=" + courses + "]";
	}	

}
