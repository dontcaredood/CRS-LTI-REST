package com.lt.business;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.lt.constants.*;
import com.lt.dao.NotificationDaoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class NotificationInterfaceImpl implements NotificationInterface {

	@Autowired
	private NotificationDaoImpl notificationDaoInterface;
	private static Logger logger = Logger.getLogger(NotificationInterfaceImpl.class);
	
	
	public int sendNotification(NotificationType type, int studentId,ModeOfPayment modeOfPayment,String paymentMethod,double amount) {
		int notificationId=0;
		try
		{
			notificationId=notificationDaoInterface.sendNotification(type, studentId,modeOfPayment,paymentMethod,amount);
			
		}
		catch(SQLException ex)
		{
			logger.error("Error occured "+ex.getMessage());
		}
		return notificationId;
	}

	
	public UUID getReferenceId(int notificationId) {
		// TODO Auto-generated method stub
		return null;
	}

}
