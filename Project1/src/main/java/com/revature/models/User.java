package com.revature.models;

import javax.persistence.*;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "project1")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {

	@Id
	@Column(name = "user_id", nullable = false, unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Length(min=4)
	private String username;
	
	@Length(min=6)
	@Column(name = "pw")
	private String password;
	
	private UserRole role;
	
	
//	@OneToOne(mappedBy="account",cascade = { CascadeType.MERGE,	CascadeType.REFRESH }, fetch = FetchType.LAZY)	  
//	@PrimaryKeyJoinColumn 
//	private Student student;
	  
//	@OneToOne(mappedBy="account",cascade = { CascadeType.MERGE,	CascadeType.REFRESH }, fetch = FetchType.LAZY)	  
//	@PrimaryKeyJoinColumn 
//	private Instructor instructor;
	 
}
