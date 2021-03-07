package com.revature.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "instructors", schema = "project1")
@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = {"coursesAssigned"}) @ToString(exclude = {"coursesAssigned"})
public class Instructor {

	@Id
	@Column(name = "user_id", nullable = false, unique = true, updatable = false)
	private int id;
	
	private String firstName;
	private String lastName;
	
	private Gender gender;
		
	@Email
	private String email;
	
	private String deparment;
	
	//Instructor has user account
//	@OneToOne(fetch = FetchType.LAZY)
//	@MapsId
//  @JoinColumn(name = "user_id")
//  private User account;
	
	//Instructor has courses
	@JsonManagedReference("Teacher")
	@OneToMany(mappedBy = "instructorAssigned", cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Set<Course> coursesAssigned;
	
	public Instructor(int instructorId) {
		super();
		this.id = instructorId;
	}
}
