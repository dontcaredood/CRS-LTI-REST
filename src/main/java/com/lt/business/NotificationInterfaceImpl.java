package com.lt.business;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.lt.constants.*;
import com.lt.dao.NotificationDaoImpl;

/**
 * 
 * @author JEDI-03
 * This method implements all the method related to the notification system
 */
public class NotificationInterfaceImpl implements NotificationInterface {

	
	private static volatile NotificationInterfaceImpl instance=null;
	NotificationDaoImpl notificationDaoInterface = NotificationDaoImpl.getInstance();
	private static Logger logger = Logger.getLogger(NotificationInterfaceImpl.class);
	private NotificationInterfaceImpl()
	{

	}
	
	public static NotificationInterfaceImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(NotificationInterfaceImpl.class){
				instance=new NotificationInterfaceImpl();
			}
		}
		return instance;
	}
	
	
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
