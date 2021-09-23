package com.lt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.lt.constants.Constants;
import com.lt.exceptions.UserNotFoundException;
import com.lt.utils.DBUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static volatile UserDaoImpl instance=null;
	 UserDaoImpl()
	{

	}
	
	public static UserDaoImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(UserDaoImpl.class){
				instance=new UserDaoImpl();
			}
		}
		return instance;
	}

	
	public boolean updatePassword(String userId, String newPassword) {
		Connection connection= DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_PASSWORD);
			
			statement.setString(1, newPassword);
			statement.setString(2, userId);
			
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
	
	
	public boolean verifyCredentials(String userId, String password) throws UserNotFoundException {
		Connection connection = (Connection) DBUtils.getConnection();
		try
		{
			//open db connection
			PreparedStatement preparedStatement=connection.prepareStatement(Constants.VERIFY_CREDENTIALS);
			preparedStatement.setString(1,userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next())
				throw new UserNotFoundException(userId);
			else if(password.equals(resultSet.getString("password")))
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			logger.info("Something went wrong, please try again! "+ ex.getMessage());
		}
		/*
		 * finally { try { connection.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		return false;
	}

	
	public boolean updatePassword(String userID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public String getRole(String userId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_ROLE);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getString("role");
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
		return null;
	}

	

}
