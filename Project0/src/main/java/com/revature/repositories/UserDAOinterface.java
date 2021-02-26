package com.revature.repositories;

import java.util.List;

import com.revature.models.User;

public interface UserDAOinterface {

	public List<User> findAll();
	public User findByUsername(String username);
	public User findByID(int id);
	public int insert(User u);
	public boolean update(User u);
	public boolean delete(int id);
	
}
