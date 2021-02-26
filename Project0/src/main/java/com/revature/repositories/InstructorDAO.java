package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Course;
import com.revature.models.Instructor;
import com.revature.models.Semester;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.util.ConnectionUtil;

public class InstructorDAO implements InstructorDAOinterface {

	private static final Logger log = LoggerFactory.getLogger(InstructorDAO.class);
	
	@Override
	public List<Instructor> findAllInstructors() {
		List<Instructor> allInstructors = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.instructors";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int id = rs.getInt("instructor_id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String department = rs.getString("dep");
				
				Instructor inst = new Instructor(id, fname, lname, email, department);
				
				allInstructors.add(inst);
			}
			
		} catch(SQLException e) {
			log.error("We failed to retrieve all users", e);
			
			return new ArrayList<>();
		}
		
		return allInstructors;
	}

	@Override
	public Instructor findAllCourses(int id) {

		Instructor instructor = findByID(id);
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT project0.courses.course_id, project0.courses.coursecode, project0.courses.coursename, project0.courses.sem, project0.courses.yr, project0.courses.capacity FROM project0.courses WHERE instructor_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			List<Course> allCourses = new ArrayList<>();
			
			while(rs.next()) {
				
				int courseID = rs.getInt("course_id");
				String coursecode = rs.getString("coursecode");
				String coursename = rs.getString("coursename");				
				Semester sem = Semester.valueOf(rs.getString("sem"));				
				int year = rs.getInt("yr");
				int capacity = rs.getInt("capacity");
				
				
				Course c = new Course(courseID, coursecode, coursename, sem, year, capacity);
					
				allCourses.add(c);
			}
			
			instructor.setCourses(allCourses);
			
		} catch(SQLException e) {
			log.error("We failed to retrieve courses for instructorID-" + id, e);
			return null;
		}
		
		return instructor;
	}

	@Override
	public Instructor findByID(int id) {
		
		Instructor i = new Instructor();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.instructors WHERE instructor_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery(); 
			
			if (rs.next()) {
			
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String department = rs.getString("dep");
					
				i.setFname(fname);
				i.setLname(lname);
				i.setEmail(email);
				i.setDeparment(department);
			
			}
			
		} catch(SQLException e) {
			log.error("Search failed", e);
			return new Instructor();
		}
		
		return i;
	}

	@Override
	public void insert(int id) {

		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.instructors (instructor_id) VALUES (?) RETURNING project0.instructors.instructor_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			stmt.executeQuery();
			
		} catch (SQLException e) {
			log.error("We failed to insert a new instructor ID="+id, e);
		}
	}

	@Override
	public Instructor update(Instructor instructor) {
		
		Instructor i = new Instructor();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "UPDATE project0.instructors SET fname = ?, lname = ?, email = ?, dep = ? WHERE instructor_id = ? RETURNING project0.instructors.instructor_id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, instructor.getFname());
			stmt.setString(2, instructor.getLname());
			stmt.setString(3, instructor.getEmail());
			stmt.setString(4, instructor.getDeparment());
			stmt.setInt(5, instructor.getInstructorID());
			
			ResultSet rs; 
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				int id = rs.getInt(1);
				
				i = this.findByID(id);
			}
		} catch(SQLException e) {
			log.error("We failed to update instructor's info", e);
			return new Instructor(); 
		}
		
		return i;
	}

}
