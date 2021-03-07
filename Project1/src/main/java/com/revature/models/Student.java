package com.revature.models;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "students", schema = "project1")
@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = {"coursesTaken"}) @ToString(exclude = {"coursesTaken"})
public class Student {

	@Id
	@Column(name = "user_id", nullable = false, unique = true, updatable = false)
	private int id;
	
	private String firstName;
	private String lastName;
	
	private Gender gender;
		
	@Email
	private String email;
	
	private String major;
	private float gpa;
	
	//Student has user account
//	@OneToOne(fetch = FetchType.LAZY)
//	@MapsId
//    @JoinColumn(name = "user_id")
//    private User account;
	
	//Student has courses
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "enrollment", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> coursesTaken;
	
	public Student(int studentId) {
		super();
		this.id = studentId;
	}
}
