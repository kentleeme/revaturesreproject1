package com.revature.models;

import java.util.List;
import java.util.Objects;

public class Student {

	private int studentID;
	private String fname;
	private String lname;
	private String email;
	private String major;
	private float gpa;
	private List<Course> classes;
	
	public Student() {
		super();
	}

	public Student(String fname, String lname, String email, String major, float gpa) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.major = major;
		this.gpa = gpa;
	}

	public Student(int studentID, String fname, String lname, String email, String major) {
		super();
		this.studentID = studentID;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.major = major;
	}

	public Student(int studentID, String fname, String lname, String email, String major, float gpa) {
		super();
		this.studentID = studentID;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.major = major;
		this.gpa = gpa;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
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

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public float getGpa() {
		return gpa;
	}

	public void setGpa(float gpa) {
		this.gpa = gpa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, fname, gpa, lname, major, studentID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		return Objects.equals(email, other.email) && Objects.equals(fname, other.fname)
				&& Float.floatToIntBits(gpa) == Float.floatToIntBits(other.gpa) && Objects.equals(lname, other.lname)
				&& Objects.equals(major, other.major) && studentID == other.studentID;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", fname=" + fname + ", lname=" + lname + ", email=" + email
				+ ", major=" + major + ", gpa=" + gpa + "]";
	}

	public List<Course> getClasses() {
		return classes;
	}

	public void setClasses(List<Course> classes) {
		this.classes = classes;
	}
	
	
}
