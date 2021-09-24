package com.lt.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.bean.RegisteredCourses;
import com.lt.bean.Student;
import com.lt.constants.Constants;
import com.lt.exceptions.ProfessorNotAddedException;
import com.lt.utils.DBUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
@Repository
@Scope("singleton")
public class ProfessorDaoImpl implements ProfessorDao {


	private static Logger logger = Logger.getLogger(ProfessorDaoImpl.class);
	
	public List<Course> getCoursesByProfessor(String profId) {
		Connection connection=DBUtils.getConnection();
		List<Course> courseList=new ArrayList<Course>();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_COURSES);
			
			statement.setString(1, profId);
			
			ResultSet results=statement.executeQuery();
			while(results.next())
			{
				courseList.add(new Course(results.getString("courseCode"),results.getString("courseName"),results.getString("courseDesc"),results.getString("professorId")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		return courseList;
		
	}

	public List<RegisteredCourses> getEnrolledStudents(String profId) {
		Connection connection=DBUtils.getConnection();
		List<RegisteredCourses> enrolledStudents=new ArrayList<RegisteredCourses>();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_ENROLLED_STUDENTS);
			statement.setString(1, profId);
			
			ResultSet results = statement.executeQuery();
			while(results.next())
			{
				//public EnrolledStudent(String courseCode, String courseName, int studentId) 
				enrolledStudents.add(new RegisteredCourses(results.getString("courseCode"),results.getString("courseName"),results.getInt("studentId")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		return enrolledStudents;
	}
	
	
	public Boolean addGrade(int studentId,String courseCode,String grade) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.ADD_GRADE);
			
			statement.setString(1, grade);
			statement.setString(2, courseCode);
			statement.setInt(3, studentId);
			
			int row = statement.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		return false;
	}
	


	public String getProfessorById(String profId) throws ProfessorNotAddedException
	{
		String prof_Name = null;
		Connection connection=DBUtils.getConnection();
		try 
		{
			PreparedStatement statement = connection.prepareStatement(Constants.GET_PROF_NAME);
			
			statement.setString(1, profId);
			ResultSet rs = statement.executeQuery();
			rs.next();
			
			prof_Name = rs.getString(1);
			if(prof_Name.equalsIgnoreCase("") || prof_Name.isEmpty()) {
				throw new ProfessorNotAddedException(profId);
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
			throw new ProfessorNotAddedException(profId);
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) {
		 * e.printStackTrace(); } }
		 */
		
		return prof_Name;
	}

}