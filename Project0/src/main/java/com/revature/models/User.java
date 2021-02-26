package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = -7845364104875421709L;
	
	private int userid;
	private String username;
	private String password;
	private UserRole role;
	
	public User() {
		super();
	}

	public User(int userid, String username, String password, UserRole role) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public User(String username, String password, UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, role, userid, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && role == other.role && userid == other.userid
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}	
	
	
}
