package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.util.ConnectionUtil;

public class UserDAO implements UserDAOinterface {

	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	
	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.users";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("pw");
				UserRole role = UserRole.valueOf(rs.getString("role"));
				
				User u = new User(id, username, password, role);
				
				allUsers.add(u);
			}
			
		} catch(SQLException e) {
			log.error("We failed to retrieve all users", e);
			
			return new ArrayList<>();
		}
		
		return allUsers;
	}

	@Override
	public User findByUsername(String username) {
		
		User u = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.users WHERE username LIKE '%?%'";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery(); 
			
			while (rs.next()) {
			
				int id = rs.getInt("id");
				
				UserRole role = UserRole.valueOf(rs.getString("role"));
					
				u.setUserid(id);
				u.setUsername(username);
				u.setRole(role);	
			
			}
			
		} catch(SQLException e) {
			log.error("Search failed", e);
			return new User();
		}
		
		return u;
	}

	@Override
	public User findByID(int id) {
		
		User u = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.users WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery(); 
			
			if (rs != null) {
				rs.next();
			
				String username = rs.getString("username");
				String password = rs.getString("pw");				
				UserRole role = UserRole.valueOf(rs.getString("role"));
					
				u.setUserid(id);
				u.setUsername(username);
				u.setPassword(password);
				u.setRole(role);	
			
			}
			
		} catch(SQLException e) {
			log.error("Search failed", e);
			return new User();
		}
		
		return u;
	}

	@Override
	public int insert(User u) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.users (username, pw, role) VALUES (?, ?, ?) RETURNING project0.users.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setObject(3, u.getRole(), Types.OTHER);
			
			ResultSet rs;
			
			if( (rs = stmt.executeQuery()) != null) {
				rs.next();
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			log.error("We failed to insert a new user", e);
			return -1;
		}
		return -1;
		
	}

	@Override
	public boolean update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "UPDATE FROM project0.users SET username = ?, pw = ?, role = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setObject(3, u.getRole(), Types.OTHER);
			stmt.setInt(4, u.getUserid());
			
			stmt.executeQuery(sql);
			
		} catch(SQLException e) {
			log.error("We failed to update this user", e);
			return false; 
		}
		
		return true;
	}

	@Override
	public boolean delete(int id) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "DELETE FROM project0.users WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			log.error("We failed to delete this user", e);
			return false; 
		}
		
		return true;
	}

}
