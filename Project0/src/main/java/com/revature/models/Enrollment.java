package com.revature.models;

import java.util.Objects;

public class Enrollment {

	private int enrollmentID;
	private int courseID;
	private int studentID;
	private String studentName;
	private String courseName;
	private String instructor;
	private EnrollStatus status;
	
	public Enrollment() {
		super();
	}

	public Enrollment(int courseID, int studentID, EnrollStatus status) {
		super();
		this.courseID = courseID;
		this.studentID = studentID;
		this.status = status;
	}

	public Enrollment(int enrollmentID, int courseID, int studentID) {
		super();
		this.enrollmentID = enrollmentID;
		this.courseID = courseID;
		this.studentID = studentID;
	}

	public Enrollment(int enrollmentID, int courseID, int studentID, EnrollStatus status) {
		super();
		this.enrollmentID = enrollmentID;
		this.courseID = courseID;
		this.studentID = studentID;
		this.status = status;
	}

	public Enrollment(int enrollmentID, int courseID, int studentID, String courseName, String instructor,
			EnrollStatus status) {
		super();
		this.enrollmentID = enrollmentID;
		this.courseID = courseID;
		this.studentID = studentID;
		this.courseName = courseName;
		this.instructor = instructor;
		this.status = status;
	}
	
	public Enrollment(int enrollmentID, int courseID, int studentID, String studentName, String courseName,
			String instructor, EnrollStatus status) {
		super();
		this.enrollmentID = enrollmentID;
		this.courseID = courseID;
		this.studentID = studentID;
		this.studentName = studentName;
		this.courseName = courseName;
		this.instructor = instructor;
		this.status = status;
	}

	public int getEnrollmentID() {
		return enrollmentID;
	}

	public void setEnrollmentID(int enrollmentID) {
		this.enrollmentID = enrollmentID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public EnrollStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollStatus status) {
		this.status = status;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseID, courseName, enrollmentID, instructor, status, studentID, studentName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Enrollment))
			return false;
		Enrollment other = (Enrollment) obj;
		return courseID == other.courseID && Objects.equals(courseName, other.courseName)
				&& enrollmentID == other.enrollmentID && Objects.equals(instructor, other.instructor)
				&& status == other.status && studentID == other.studentID
				&& Objects.equals(studentName, other.studentName);
	}

	@Override
	public String toString() {
		return "Enrollment [enrollmentID=" + enrollmentID + ", courseID=" + courseID + ", studentID=" + studentID
				+ ", studentName=" + studentName + ", courseName=" + courseName + ", instructor=" + instructor
				+ ", status=" + status + "]";
	}

}
