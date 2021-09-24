package com.lt.dao;

import java.util.*;

import org.apache.log4j.Logger;

import java.sql.*;
import com.lt.bean.*;
import com.lt.constants.*;
import com.lt.exceptions.*;
import com.lt.utils.DBUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class AdminDaoImpl implements AdminDao{

	private static Logger logger = Logger.getLogger(AdminDaoImpl.class);
	private PreparedStatement statement = null;

	Connection connection = DBUtils.getConnection();
	
	public boolean deleteCourse(String courseCode) throws CourseNotFoundException, CourseNotDeletedException{
		
		statement = null;
		try {
			String sql = Constants.DELETE_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,courseCode);
			int row = statement.executeUpdate();
			
			logger.info(row + " entries deleted.");
			if(row == 0) {
				logger.error(courseCode + " not in catalog!");
				throw new CourseNotFoundException(courseCode);
			}

			logger.info("Course with courseCode: " + courseCode + " deleted.");
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			throw new CourseNotDeletedException(courseCode);
		}
		return false;
		
	}

	
	public boolean addCourse(Course course) throws CourseFoundException{
		
		statement = null;
		try {
			
			String sql = Constants.ADD_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, course.getCourseId());
			statement.setString(2, course.getCourseName());
			statement.setString(3, course.getCourseDescription());
			statement.setString(4, course.getProfessorId());
			int row = statement.executeUpdate();
			
			logger.info(row + " course added");
			if(row == 0) {
				logger.error("Course with courseCode: " + course.getCourseId() + "not added to catalog.");
				throw new CourseFoundException(course.getCourseId());
			}
			
			logger.info("Course with courseCode: " + course.getCourseId() + " is added to catalog."); 
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			throw new CourseFoundException(course.getCourseId());
			
		}
		return false;
		
	}

	
	public List<Student> viewPendingAdmissions() {
		
		statement = null;
		List<Student> userList = new ArrayList<Student>();
		try {
			
			String sql = Constants.VIEW_PENDING_ADMISSION_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				
				Student user = new Student();
				user.setUserId(resultSet.getString(1));
				user.setName(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setRole(Role.stringToName(resultSet.getString(4)));
				user.setGender(Gender.stringToGender( resultSet.getString(5)));
				user.setAddress(resultSet.getString(6));
				user.setStudentId(resultSet.getInt(7));
				userList.add(user);
				
			}
			
			logger.info(userList.size() + " students have pending-approval.");
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			
		}
		
		return userList;
		
	}

	
	public void approveStudent(int studentId) throws StudentNotFoundForApprovalException {
		
		statement = null;
		try {
			String sql = Constants.APPROVE_STUDENT_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1,studentId);
			int row = statement.executeUpdate();
			
			logger.info(row + " student approved.");
			if(row == 0) {
				//logger.error("Student with studentId: " + studentId + " not found.");
				throw new StudentNotFoundForApprovalException(studentId);
			}
			
			//logger.info("Student with studentId: " + studentId + " approved by admin.");
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			
		}
		
	}

	
	public void addUser(User user) throws UserNotAddedException, UserIdAlreadyInUseException{
		
		statement = null;
		try {
			
			String sql = Constants.ADD_USER_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUserId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getRole().toString());
			statement.setString(5, user.getGender().toString());
			statement.setString(6, user.getAddress());
			int row = statement.executeUpdate();
			
			logger.info(row + " user added.");
			if(row == 0) {
				logger.error("User with userId: " + user.getUserId() + " not added.");
				throw new UserNotAddedException(user.getUserId()); 
			}

			logger.info("User with userId: " + user.getUserId() + " added."); 
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			throw new UserIdAlreadyInUseException(user.getUserId());
			
		}
		
	}

	
	public boolean addProfessor(Professor professor) throws UserIdAlreadyInUseException, ProfessorNotAddedException {
		boolean flag = false;
		try {
			
			this.addUser(professor);
			
		}catch (UserNotAddedException e) {
			
			logger.error(e.getMessage());
			throw new ProfessorNotAddedException(professor.getUserId());
			
		}catch (UserIdAlreadyInUseException e) {
			
			logger.error(e.getMessage());
			throw e;
			
		}
		
		
		statement = null;
		try {
			
			String sql = Constants.ADD_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, professor.getUserId());
			statement.setString(2, professor.getDepartment());
			statement.setString(3, professor.getDesignation());
			int row = statement.executeUpdate();

			logger.info(row + " professor added.");
			if(row == 0) {
				logger.error("Professor with professorId: " + professor.getUserId() + " not added.");
				throw new ProfessorNotAddedException(professor.getUserId());
			}else {
				flag = true;
				logger.info("Professor with professorId: " + professor.getUserId() + " added."); 
			}
			
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			throw new UserIdAlreadyInUseException(professor.getUserId());
			
		}
		return flag; 
		
	}
	
	
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException{
		
		statement = null;
		try {
			String sql = Constants.ASSIGN_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,professorId);
			statement.setString(2,courseCode);
			int row = statement.executeUpdate();
			
			logger.info(row + " course assigned.");
			if(row == 0) {
				logger.error(courseCode + " not found");
				throw new CourseNotFoundException(courseCode);
			}
			
			logger.info("Course with courseCode: " + courseCode + " is assigned to professor with professorId: " + professorId + ".");
		
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			throw new UserNotFoundException(professorId);
			
		}
		
	}
	
	
	public List<Course> viewCourses() {
		
		statement = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			
			String sql = Constants.VIEW_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Course course = new Course();
				course.setCourseId(resultSet.getString(1));
				course.setCourseName(resultSet.getString(2));
				course.setProfessorId(resultSet.getString(3));
				courseList.add(course);
				
			}
			
			logger.info(courseList.size() + " courses in table");
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			
		}
		
		return courseList; 
		
	}
	
	
	public List<Professor> viewProfessors() {
		
		statement = null;
		List<Professor> professorList = new ArrayList<Professor>();
		try {
			
			String sql = Constants.VIEW_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Professor professor = new Professor();
				professor.setUserId(resultSet.getString(1));
				professor.setName(resultSet.getString(2));
				professor.setGender(Gender.stringToGender(resultSet.getString(3)));
				professor.setDepartment(resultSet.getString(4));
				professor.setDesignation(resultSet.getString(5));
				professor.setAddress(resultSet.getString(6));
				professor.setRole(Role.PROFESSOR);
				professor.setPassword("*********");
				professorList.add(professor);
				
			}
			
			logger.info(professorList.size() + " professors in the institute.");
			
		}catch(SQLException se) {
			
			logger.error(se.getMessage());
			
		}
		return professorList;
	}
}
