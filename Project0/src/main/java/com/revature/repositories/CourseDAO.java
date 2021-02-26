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

import com.revature.models.Course;
import com.revature.models.Semester;
import com.revature.models.Student;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.util.ConnectionUtil;

public class CourseDAO implements CourseDAOinterface {

	private static final Logger log = LoggerFactory.getLogger(CourseDAO.class);
	
	@Override
	public int insert(Course c) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql2 = "SELECT * FROM project0.instructors WHERE instructor_id = ?";
			
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, c.getInstructorID());
			
			ResultSet rs2 = stmt2.executeQuery();
			
			if (rs2 == null) {
				log.info("We failed to update this course, intructor ID-{} does not exist", c.getInstructorID());
				return -1;
			}
			
			String sql = "INSERT INTO project0.courses (coursecode,coursename,sem,yr,capacity,instructor_id)"
					+"VALUES (?, ?, ?, ?, ?, ?) RETURNING project0.courses.course_id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, c.getCourseCode());
			stmt.setString(2, c.getCourseName());
			stmt.setObject(3, c.getSem(), Types.OTHER);
			stmt.setInt(4, c.getYear());
			stmt.setInt(5, c.getCapacity());
			stmt.setInt(6, c.getInstructorID());
			
			ResultSet rs;
			
			if( (rs = stmt.executeQuery()) != null) {
				rs.next();
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			log.error("We failed to insert a new course", e);
			return -1;
		}
		return -1;
	}

	@Override
	public boolean delete(int cid) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "DELETE FROM project0.courses WHERE course_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, cid);
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			log.error("We failed to delete this course", e);
			return false; 
		}
		
		return true;
	}

	@Override
	public Course update(Course c) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql2 = "SELECT * FROM project0.instructors WHERE instructor_id = ?";
			
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, c.getInstructorID());
			
			ResultSet rs = stmt2.executeQuery();
			
			if (rs == null) {
				log.info("We failed to update this course, intructor ID-{} does not exist", c.getInstructorID());
				return new Course();
			}
			
			String sql = "UPDATE FROM project0.courses SET coursecode = ?, coursename = ?, sem = ?,"
					+ "yr = ?, capacity = ?, instructor_id = ? WHERE course_id = ? RETURNING project0.courses.course_id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, c.getCourseCode());
			stmt.setString(2, c.getCourseName());
			stmt.setObject(3, c.getSem(), Types.OTHER);
			stmt.setInt(4, c.getYear());
			stmt.setInt(5, c.getCapacity());
			stmt.setInt(6, c.getInstructorID());
			stmt.setInt(7, c.getCourseID());
			
			stmt.executeQuery(sql);
			
			ResultSet rs2;
			
			if( (rs2 = stmt.executeQuery()) != null) {
				rs2.next();
				
				return this.findByID(rs2.getInt(1));
			}
			
		} catch(SQLException e) {
			log.error("We failed to update this course info courseID-" + c.getCourseID(), e);
			return new Course(); 
		}
		
		return new Course();
	}

	@Override
	public List<Course> findAll() {
		
		List<Course> allCourses = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.courses LEFT JOIN project0.instructors ON project0.courses.instructor_id = project0.instructors.instructor_id";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int course_id = rs.getInt("course_id");
				String coursecode = rs.getString("coursecode");
				String coursename = rs.getString("coursename");
				Semester sem = Semester.valueOf(rs.getString("sem"));
				int yr = rs.getInt("yr");
				int capacity = rs.getInt("capacity");
				int instructor_id = rs.getInt("instructor_id");
				String name = rs.getString("fname")+" "+rs.getString("lname");

				Course c = new Course(course_id,coursecode,coursename,sem,yr,capacity,instructor_id,name);
				
				allCourses.add(c);
			}
			
		} catch(SQLException e) {
			log.error("We failed to retrieve all courses", e);
			
			return new ArrayList<>();
		}
		
		return allCourses;
	}

	@Override
	public Course findByID(int id) {
		
		Course c = new Course();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.courses LEFT JOIN project0.instructors ON project0.courses.instructor_id = project0.instructors.instructor_id WHERE course_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if( rs != null) {
				rs.next();
				
				c.setCourseID(id);
				c.setCourseCode(rs.getString("coursecode"));
				c.setCourseName(rs.getString("coursename"));
				c.setCapacity(rs.getInt("capacity"));
				c.setInstructorID(rs.getInt("instructor_id"));
				c.setYear(rs.getInt("yr"));
				Semester sem = Semester.valueOf(rs.getString("sem"));
				c.setSem(sem);
				String fullname = rs.getString("fname")+ " " + rs.getString("lname");
				c.setInstructorName(fullname);
			}
			else {
				log.info("Couldn't find ID-{}", id);
				return new Course();
			}
			
		} catch(SQLException e) {
			log.error("Search failed for courseID-"+id, e);
			return new Course();
		}
		
		return c;
	}

	@Override
	public List<Course> findByCourseName(String cname) {
		
		List<Course> allCourses = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM project0.courses LEFT JOIN project0.instructors"
						+"ON project0.courses.instructor_id = project0.instructors.instructor_id"
						+"WHERE coursename LIKE '%?%'";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cname);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int course_id = rs.getInt("coure_id");
				String coursecode = rs.getString("coursecode");
				String coursename = rs.getString("coursename");
				Semester sem = Semester.valueOf(rs.getString("sem"));
				int yr = rs.getInt("yr");
				int capacity = rs.getInt("capacity");
				int instructor_id = rs.getInt("instructor_id");
				String name = rs.getString("fname")+" "+rs.getString("lname");

				Course c = new Course(course_id,coursecode,coursename,sem,yr,capacity,instructor_id,name);
				
				allCourses.add(c);
			}
			
		} catch(SQLException e) {
			log.error("Search failed", e);
			
			return new ArrayList<>();
		}
		
		return allCourses;
	}

	@Override
	public Course showStudents(int id) {
		Course c = new Course();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.enrollment LEFT JOIN project0.courses ON project0.courses.course_id = project0.enrollment.course_id LEFT JOIN project0.students ON project0.enrollment.student_id = project0.students.student_id WHERE project0.enrollment.course_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery(); 
			
			if (rs != null) {
			
				rs.next();
				
				c.setCourseID(id);
				c.setCourseCode(rs.getString("coursecode"));
				c.setCourseName(rs.getString("coursename"));
				c.setCapacity(rs.getInt("capacity"));
				c.setInstructorID(rs.getInt("instructor_id"));
				c.setYear(rs.getInt("yr"));
				Semester sem = Semester.valueOf(rs.getString("sem"));
				c.setSem(sem);
				String fullname = this.getInstructorName(id);
				c.setInstructorName(fullname);
				
				List<Student> stu = new ArrayList<>();
				
			
				do {
					StudentDAO stdntDAO = new StudentDAO();
					int stuid = rs.getInt("student_id");
					Student s = stdntDAO.findByID(stuid);
					stu.add(s); } while( rs.next() );
				
				c.setStudents(stu);
			}
			
		} catch(SQLException e) {
			log.error("Can't show all students for courseID-"+id, e);
			return new Course();
		}
		
		return c;
	}

	@Override
	public String getInstructorName(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.courses LEFT JOIN project0.instructors ON project0.courses.instructor_id = project0.instructors.instructor_id WHERE course_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs; 
			
			if( (rs = stmt.executeQuery()) != null) {
				rs.next();
				
				return rs.getString("fname")+ " " + rs.getString("lname");
			}
			else {
				log.info("Couldn't find courseID-{}", id);
				return "";
			}
			
		} catch(SQLException e) {
			log.error("Search failed for courseID-"+id, e);
			return "";
		}
	}

}
