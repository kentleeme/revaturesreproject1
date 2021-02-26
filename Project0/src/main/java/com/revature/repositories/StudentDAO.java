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
import com.revature.models.EnrollStatus;
import com.revature.models.Enrollment;
import com.revature.models.Instructor;
import com.revature.models.Semester;
import com.revature.models.Student;
import com.revature.util.ConnectionUtil;

public class StudentDAO implements StudentDAOinterface {

	private static final Logger log = LoggerFactory.getLogger(StudentDAO.class);
	
	@Override
	public List<Student> findAll() {
		
		List<Student> allStudents = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.students";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int id = rs.getInt("student_id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String degree = rs.getString("deg");
				
				Student stu = new Student(id, fname, lname, email, degree);
				
				allStudents.add(stu);
			}
			
		} catch(SQLException e) {
			log.error("We failed to retrieve all students", e);
			
			return new ArrayList<>();
		}
		
		return allStudents;
	}

	@Override
	public Student findByID(int id) {
		Student s = new Student();

		try (Connection conn = ConnectionUtil.getConnection()) {
			
			
			String sql = "SELECT * FROM project0.students WHERE student_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery(); 
			
			if (rs.next()) {
			
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String degree = rs.getString("deg");
					
				s.setFname(fname);
				s.setLname(lname);
				s.setEmail(email);
				s.setMajor(degree);
			
			}
			
		} catch(SQLException e) {
			log.error("Search failed for studentID-"+id, e);
			return new Student();
		}
		
		return s;
	}

	@Override
	public void insert(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.students (student_id) VALUES (?) RETURNING project0.students.student_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			stmt.executeQuery();
			
		} catch (SQLException e) {
			log.error("We failed to insert a new student ID="+id, e);
		}
	}

	@Override
	public Student update(Student s) {

		Student stu = new Student();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "UPDATE project0.students SET fname = ?, lname = ?, email = ?, deg = ?, gpa = ? WHERE project0.students.student_id = ? RETURNING project0.students.student_id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, s.getFname());
			stmt.setString(2, s.getLname());
			stmt.setString(3, s.getEmail());
			stmt.setString(4, s.getMajor());
			stmt.setFloat(5, s.getGpa());
			stmt.setInt(6, s.getStudentID());
			
			ResultSet rs; 
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				int id = rs.getInt(1);
				
				stu = this.findByID(id);
			}
			
		} catch(SQLException e) {
			log.error("We failed to update student's info", e);
			return new Student(); 
		}
		
		return stu;
	}
	
	@Override
	public boolean dropClass(int enrollmentid) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			

			String sql = "DELETE FROM project0.enrollment WHERE enrollment_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, enrollmentid);
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			log.error("We failed to drop this enrollmentID-"+enrollmentid, e);
			return false; 
		}
		
		return true;
	}

	@Override
	public int enrollClass(Enrollment en) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.enrollment (course_id, student_id, status) VALUES (?, ?, ?) RETURNING project0.enrollment.enrollment_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, en.getCourseID());
			stmt.setInt(2, en.getStudentID());
			stmt.setObject(3, EnrollStatus.Pending, Types.OTHER);
			
			ResultSet rs;
			
			if( (rs = stmt.executeQuery()) != null) {
				rs.next();
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			log.error("We failed to enroll studentID-"+en.getStudentID()+" to classID-"+en.getCourseID(), e);
			return -1;
		}
		return -1;
	}

	@Override
	public List<Course> showClasses(int studentid) {

		List<Course> allCourses = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT project0.courses.coursecode, project0.courses.coursename, project0.courses.sem, project0.courses.yr, project0.courses.capacity, project0.courses.instructor_id FROM project0.enrollment LEFT JOIN project0.courses ON project0.enrollment.course_id = project0.courses.course_id WHERE project0.enrollment.student_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, studentid);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String code = rs.getString("coursecode");
				String name = rs.getString("coursename");
				Semester sem = Semester.valueOf(rs.getString("sem"));
				int yr = rs.getInt("yr");
				int cap = rs.getInt("capacity");
				int instructorid = rs.getInt("instructor_id");
				
				InstructorDAO instrDAO = new InstructorDAO();
				Instructor instr = instrDAO.findByID(instructorid);
						
				String instructorname = instr.getFname() + " " + instr.getLname();
				
				Course c = new Course(code,name,sem,yr,cap,instructorid,instructorname);
				
				allCourses.add(c);
			}	
			
			
		} catch(SQLException e) {
			log.error("We failed to show all classes for studentID-"+studentid, e);
			return new ArrayList<>();
		}
		
		return allCourses;
	}

	@Override
	public float calculateGPA(int id, List<Character> lettergrade) {
		
		float currentGpa = this.findByID(id).getGpa();
		float count = 0;
		float sum = 0;
		
		for (char grade: lettergrade) {
			switch(grade) {
			case 'A': 
				sum += 4;
				break;
			case 'B':
				sum += 3;
				break;
			case 'C':
				sum += 2;
				break;
			case 'D':
				sum += 1;
				break;
			default:
				break;
			}
			count++;
		}
		
		if (count != 0) {
			float termGpa = sum/count;
		
			float finalGpa = (currentGpa + termGpa) / 2;
			
			if (currentGpa != 0)
				return finalGpa;
			else
				return termGpa;
		}
		else {
			return currentGpa;
		}
	}

}
