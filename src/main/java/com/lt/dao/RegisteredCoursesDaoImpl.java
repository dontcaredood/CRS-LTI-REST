package com.lt.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.business.RegisteredCoursesInterfaceImpl;
import com.lt.constants.Constants;
import com.lt.constants.ModeOfPayment;
import com.lt.utils.DBUtils;

public class RegisteredCoursesDaoImpl implements RegisteredCoursesDao{

	private static Logger logger = Logger.getLogger(RegisteredCoursesDaoImpl.class);
	private PreparedStatement stmt = null;
	
	private static volatile RegisteredCoursesDaoImpl instance = null;

	private RegisteredCoursesDaoImpl() {
	}

	public static RegisteredCoursesDaoImpl getInstance() {
		if (instance == null) {
			synchronized (RegisteredCoursesDaoImpl.class) {
				instance = new RegisteredCoursesDaoImpl();
			}
		}
		return instance;
	}
	
	@Override
	public boolean addCourse(String courseCode, int studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		

		try 
		{
			stmt = conn.prepareStatement(Constants.ADD_COURSE);
			stmt.setInt(1, studentId);
			stmt.setString(2, courseCode);

			stmt.executeUpdate();
			/*
			 * stmt = conn.prepareStatement(Constants.DECREMENT_COURSE_SEATS);
			 * stmt.setString(1, courseCode); stmt.executeUpdate();
			 */
			return true;
		}
		catch (SQLException e) 
		{
			logger.info(e.getMessage());
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		return false;
		
	}
	
	
	
	@Override
	public int numOfRegisteredCourses(int studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		
		int count = 0;
		try {

			stmt = conn.prepareStatement(Constants.NUMBER_OF_REGISTERED_COURSES);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
			}
			return count;

		}
		catch (SQLException se) 
		{

			logger.error(se.getMessage());

		} 
		catch (Exception e)
		{

			logger.error(e.getMessage());
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		
		return count;
	}


	
	@Override
	public boolean seatAvailable(String courseCode) throws SQLException {

		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(Constants.GET_SEATS);
			stmt.setString(1, courseCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return (rs.getInt("seats") > 0);
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		
		return true;
		

	}
	


	@Override
	public boolean isRegistered(String courseCode, int studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		
		boolean check = false;
		try
		{
			stmt = conn.prepareStatement(Constants.IS_REGISTERED);
			stmt.setString(1, courseCode);
			stmt.setInt(2, studentId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				check = true;
			}
		}
		catch(Exception e)
		{
			logger.info(e.getClass());
			logger.info(e.getMessage());
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		
		return check;
		
	}


	@Override
	public boolean dropCourse(String courseCode, int studentId) throws SQLException {
	
		Connection conn = DBUtils.getConnection();
		
		
			try
			{
				stmt = conn.prepareStatement(Constants.DROP_COURSE_QUERY);
				stmt.setString(1, courseCode);
				stmt.setInt(2, studentId);
				stmt.execute();
				
//				stmt = conn.prepareStatement(Constants.INCREMENT_SEAT_QUERY);
//				stmt.setString(1, courseCode);
//				stmt.execute();
				
				stmt.close();
				
				return true;
			}
			catch(Exception e)
			{
				logger.info("Exception found" + e.getMessage());
			}
//			finally
//			{
//	
//				stmt.close();
//				conn.close();
//			}
			
		
		return false;
		
	}
	
	
	
	@Override
	public double calculateFee(int studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		double fee = 0;
		try
		{
			stmt = conn.prepareStatement(Constants.CALCULATE_FEES);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			fee = rs.getDouble(1);
		}
		catch(SQLException e)
		{
			logger.error(e.getErrorCode());
			logger.info(e.getMessage());
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		
		return fee;
	}

	
	@Override
	public List<Grade> viewGradeCard(int studentId) throws SQLException {
		
		Connection conn = DBUtils.getConnection();
		List<Grade> grade_List = new ArrayList<>();
		try
		{
			stmt = conn.prepareStatement(Constants.VIEW_GRADE);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				String courseCode = rs.getString("courseCode");
				String courseName = rs.getString("courseName");
				String grade = rs.getString("grade");
				Grade obj = new Grade(courseCode, courseName,grade);
				grade_List.add(obj);
			}
		}
		catch(SQLException e)
		{
			logger.info(e.getMessage());
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
		}
//		finally
//		{
//			stmt.close();
//			conn.close();
//			
//		}
		
		return grade_List;
	}

	
	@Override
	public List<Course> viewCourses(int studentId) throws SQLException {
		
		List<Course> availableCourseList = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		
		try 
		{
			stmt = conn.prepareStatement(Constants.VIEW_AVAILABLE_COURSES);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				availableCourseList.add(new Course(rs.getString("courseCode"), rs.getString("courseName"),rs.getString("courseDesc"),
						rs.getString("professorId")));

			}
			

		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		/*
		 * finally { stmt.close(); conn.close(); }
		 */
		return availableCourseList;
		
	}

	
	@Override
	public List<Course> viewRegisteredCourses(int studentId) throws SQLException {

		Connection conn = DBUtils.getConnection();
		List<Course> registeredCourseList = new ArrayList<Course>();
		try 
		{
			stmt = conn.prepareStatement(Constants.VIEW_REGISTERED_COURSES);
			stmt.setInt(1, studentId);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				registeredCourseList.add(new Course(rs.getString("courseCode"), rs.getString("courseName"),rs.getString("courseDesc"),
						rs.getString("professorId")));

			}
		} 
		catch (SQLException e) 
		{
			logger.info(e.getMessage());

		} 
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}
		
		return registeredCourseList;
	}


	@Override
	public boolean getRegistrationStatus(int studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(Constants.GET_REGISTRATION_STATUS);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);

		} 
		catch (SQLException e) 
		{
			logger.info(e.getMessage());

		} 
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}

		return status;
	}
	
	@Override
	public void setRegistrationStatus(int studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(Constants.SET_REGISTRATION_STATUS);
			stmt.setInt(1, studentId);
			stmt.executeUpdate();

		} 
		catch (SQLException e) 
		{
			logger.info(e.getMessage());

		} 
//		finally
//		{
//			stmt.close();
//			conn.close();
//		}

	}


	public UUID addPayment(int studentId, String Method,ModeOfPayment modeOfPayment,double amount) throws SQLException
	{
		UUID referenceId;
		Connection connection=DBUtils.getConnection();
		try
		{
			referenceId=UUID.randomUUID();
			//INSERT_NOTIFICATION = "insert into notification(studentId,type,referenceId) values(?,?,?);";
			PreparedStatement statement = connection.prepareStatement(Constants.INSERT_PAYMENT);
			statement.setInt(1, studentId);
			statement.setString(2, modeOfPayment.toString());
			statement.setString(3,referenceId.toString());
			statement.setDouble(4, amount);
			statement.executeUpdate();
			//check if record is added
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return referenceId;
	}
	

}
