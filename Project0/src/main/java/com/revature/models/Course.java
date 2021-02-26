package com.revature.models;

import java.util.List;
import java.util.Objects;

public class Course {

	private int courseID;
	private String courseCode;
	private String courseName;
	private Semester sem;
	private int year;
	private int capacity;
	private int instructorID;
	private String instructorName;
	private List<Student> students;
	
	public Course() {
		super();
	}

	public Course(int courseID, String courseCode, String courseName, Semester sem, int year, int capacity,
			int instructorID, String instructorName, List<Student> students) {
		super();
		this.courseID = courseID;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.sem = sem;
		this.year = year;
		this.capacity = capacity;
		this.instructorID = instructorID;
		this.instructorName = instructorName;
		this.students = students;
	}

	public Course(int courseID, String courseCode, String courseName, Semester sem, int year, int capacity,
			int instructorID, String instructorName) {
		super();
		this.courseID = courseID;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.sem = sem;
		this.year = year;
		this.capacity = capacity;
		this.instructorID = instructorID;
		this.instructorName = instructorName;
	}

	public Course(int courseID, String courseCode, String courseName, Semester sem, int year, int capacity) {
		super();
		this.courseID = courseID;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.sem = sem;
		this.year = year;
		this.capacity = capacity;
	}

	public Course(String courseCode, String courseName, Semester sem, int year, int capacity, int instructorID,
			String instructorName) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.sem = sem;
		this.year = year;
		this.capacity = capacity;
		this.instructorID = instructorID;
		this.instructorName = instructorName;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Semester getSem() {
		return sem;
	}

	public void setSem(Semester sem) {
		this.sem = sem;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(int instructorID) {
		this.instructorID = instructorID;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capacity, courseCode, courseID, courseName, instructorID, instructorName, sem, students,
				year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		return capacity == other.capacity && Objects.equals(courseCode, other.courseCode) && courseID == other.courseID
				&& Objects.equals(courseName, other.courseName) && instructorID == other.instructorID
				&& Objects.equals(instructorName, other.instructorName) && sem == other.sem
				&& Objects.equals(students, other.students) && year == other.year;
	}

	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseCode=" + courseCode + ", courseName=" + courseName + ", sem="
				+ sem + ", year=" + year + ", capacity=" + capacity + ", instructorID=" + instructorID
				+ ", instructorName=" + instructorName + ", students=" + students + "]";
	}

	
}
