package com.lt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.lt.constants.*;
import com.lt.utils.DBUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class NotificationDaoImpl implements NotificationDao{

	private static Logger logger = Logger.getLogger(NotificationDaoImpl.class);
	
	public int sendNotification(NotificationType type, int studentId,ModeOfPayment modeOfPayment,String paymentMethod,double amount) throws SQLException{
		int notificationId=0;
		Connection connection=DBUtils.getConnection();
		try
		{
			//INSERT_NOTIFICATION = "insert into notification(studentId,type,referenceId) values(?,?,?);";
			PreparedStatement ps = connection.prepareStatement(Constants.INSERT_NOTIFICATION,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, studentId);
			ps.setString(2,type.toString());
			if(type==NotificationType.PAYMENT)
			{
				//insert into payment, get reference id and add here
				UUID referenceId=addPayment(studentId, modeOfPayment,paymentMethod,amount);
				ps.setString(3, referenceId.toString());	
				logger.info("Payment successful, Reference ID: "+referenceId);
			}
			else {
				ps.setString(3,"");
			}
				
			ps.executeUpdate();
			ResultSet results=ps.getGeneratedKeys();
			if(results.next())
				notificationId=results.getInt(1);
			
			switch(type)
			{
			case REGISTRATION:
				logger.info("Registration successfull. Administration will verify the details and approve it!");
				break;
			case REGISTRATION_APPROVAL:
				logger.info("Student with id "+studentId+" has been approved!");
				break;
			case PAYMENT:
				logger.info("Student with id "+studentId+" fee has been paid");
			}
			
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return notificationId;
	}


	public UUID addPayment(int studentId, ModeOfPayment modeOfPayment,String methodOfPayment,double amount) throws SQLException
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
			statement.setString(3,methodOfPayment);
			statement.setString(4,referenceId.toString());
			statement.setDouble(5, amount);
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
