package com.revature.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "courses", schema = "project1")
@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = {"studentsEnrolled"}) @ToString(exclude = {"studentsEnrolled"})
public class Course {
	
	@Id
	@Column(name = "course_id", nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;
	
	@Length(min=6, max=8)
	private String courseCode;
	private String courseName;
	private Semester semester;
	
	@Max(2030) @Min(2020)
	private int year;
	@Max(30) @Min(10)
	private int capacity;
	
	//Course has instructor
	@JsonBackReference("Teacher")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = true)	
    private Instructor instructorAssigned;
	
	//Course has students
	@ManyToMany(mappedBy = "coursesTaken", cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Student> studentsEnrolled;
}
