package com.lt.dao;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.bean.RegisteredCourses;
import com.lt.bean.Student;
import com.lt.business.StudentInterfaceImpl;
import com.lt.constants.Constants;
import com.lt.exceptions.StudentNotRegisteredException;
import com.lt.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl implements StudentDao {
	private static Logger logger = Logger.getLogger(StudentDaoImpl.class);
	
	private static volatile StudentDaoImpl instance=null;
	 StudentDaoImpl()
	{

	}
	
	public static StudentDaoImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(StudentDaoImpl.class){
				instance=new StudentDaoImpl();
			}
		}
		return instance;
	}

	public int addStudent(Student student) throws StudentNotRegisteredException{
		Connection connection=DBUtils.getConnection();
		int studentId=0;
		try
		{
			//open db connection
			PreparedStatement preparedStatement=connection.prepareStatement(Constants.ADD_USER_QUERY);
			preparedStatement.setString(1, student.getUserId());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setString(3, student.getPassword());
			preparedStatement.setString(4, student.getRole().toString());
			preparedStatement.setString(5, student.getGender().toString());
			preparedStatement.setString(6, student.getAddress());
			int rowsAffected=preparedStatement.executeUpdate();
			if(rowsAffected==1)
			{
				//add the student record
				//"insert into student (userId,branchName,batch,isApproved) values (?,?,?,?)";
				PreparedStatement preparedStatementStudent;
				preparedStatementStudent=connection.prepareStatement(Constants.ADD_STUDENT,Statement.RETURN_GENERATED_KEYS);
				preparedStatementStudent.setString(1,student.getUserId());
				preparedStatementStudent.setString(2, student.getBranchName());
				preparedStatementStudent.setString(3, "N");
				preparedStatementStudent.executeUpdate();
				ResultSet results=preparedStatementStudent.getGeneratedKeys();
				if(results.next())
					studentId=results.getInt(1);
			}
			
			
		}
		catch(Exception ex)
		{
			throw new StudentNotRegisteredException(student.getName());
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) {
		 * System.out.println(e.getMessage()+"SQL error"); e.printStackTrace(); } }
		 */
		return studentId;
	}
	

	public int getStudentId(String userId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_STUDENT_ID);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getInt("studentId");
			}
				
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return 0;
	}
	
	
	public boolean isApproved(int studentId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.IS_APPROVED);
			statement.setInt(1, studentId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				if(rs.getString("isApproved").equalsIgnoreCase("Y")) {
					return true;
				}else {
					return false;
				}
				
			}
				
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return false;
	}


}