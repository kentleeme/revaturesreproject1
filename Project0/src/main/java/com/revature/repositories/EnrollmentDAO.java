package com.revature.repositories;

import static org.mockito.Matchers.anySet;

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
import com.revature.models.EnrollStatus;
import com.revature.models.Enrollment;
import com.revature.models.Semester;
import com.revature.util.ConnectionUtil;

public class EnrollmentDAO implements EnrollmentDAOinterface {

	private static final Logger log = LoggerFactory.getLogger(EnrollmentDAO.class);

	@Override
	public Enrollment updateEnrollment(Enrollment en) {
		
		Enrollment updatedEn = new Enrollment();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "UPDATE FROM project0.enrollment SET status = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setObject(1, en.getStatus(), Types.OTHER);
			stmt.setInt(2, en.getEnrollmentID());
			
			stmt.executeQuery(sql);
			
			updatedEn = findByID(en.getEnrollmentID());
			
		} catch(SQLException e) {
			log.error("We failed to update this enrollmentID-"+en.getEnrollmentID(), e);
			return new Enrollment(); 
		}
		
		return updatedEn;
	}



	@Override
	public List<Enrollment> findAll() {
		
		List<Enrollment> allEnrollments = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.enrollment LEFT JOIN project0.courses ON project0.enrollment.course_id = project0.courses.course_id LEFT JOIN project0.students ON project0.enrollment.student_id = project0.students.student_id";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int enrollmentID = rs.getInt("enrollment_id");
				int courseID = rs.getInt("course_id");
				int studentID = rs.getInt("student_id");
				
				StudentDAO studao = new StudentDAO();
				String studentName = studao.findByID(studentID).getFname() + " " + studao.findByID(studentID).getLname();
				
				String courseName = rs.getString("coursename");
				
				int instructorid = rs.getInt("instructor_id"); 
				InstructorDAO instrdao = new InstructorDAO();
				String instructor = instrdao.findByID(instructorid).getFname() + " " + instrdao.findByID(instructorid).getLname();
				
				EnrollStatus status = EnrollStatus.valueOf(rs.getString("status"));
				
				Enrollment en = new Enrollment(enrollmentID, courseID, studentID, studentName, courseName, instructor, status);
				
				allEnrollments.add(en);
			}
			
		} catch(SQLException e) {
			log.error("We failed to display all enrollments", e);
			return new ArrayList<>(); 
		}
		
		return allEnrollments;
	}



	@Override
	public Enrollment findByID(int enrollmentid) {
		
		Enrollment en = new Enrollment();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			
			String sql = "SELECT * FROM project0.enrollment LEFT JOIN project0.courses ON project0.enrollment.course_id = project0.courses.course_id LEFT JOIN project0.students ON project0.enrollment.student_id = project0.students.student_id WHERE enrollment_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1,enrollmentid);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs != null) {
				
				rs.next();
				
				int courseID = rs.getInt("course_id");
				int studentID = rs.getInt("student_id");
				
				StudentDAO studao = new StudentDAO();
				String studentName = studao.findByID(studentID).getFname() + " " + studao.findByID(studentID).getLname();
				
				String courseName = rs.getString("coursename");
				
				int instructorid = rs.getInt("instructor_id"); 
				InstructorDAO instrdao = new InstructorDAO();
				String instructor = instrdao.findByID(instructorid).getFname() + " " + instrdao.findByID(instructorid).getLname();
				
				EnrollStatus status = EnrollStatus.valueOf(rs.getString("status"));
				
				en.setEnrollmentID(enrollmentid);
				en.setCourseID(courseID);
				en.setCourseName(courseName);
				en.setStudentID(studentID);
				en.setStudentName(studentName);
				en.setInstructor(instructor);
				en.setStatus(status);
			}
			else {
				return new Enrollment();
			}
			
		} catch(SQLException e) {
			log.error("We failed to display this enrollmentID-"+enrollmentid, e);
			return new Enrollment(); 
		}
		
		return en;
	}

}
