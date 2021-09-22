package com.lt.business;

import java.util.UUID;

import com.lt.constants.*;

public interface NotificationInterface {
	
	/**
	 * Method to send the notification of payment
	 * @param type
	 * @param studentId
	 * @param modeOfPayment
	 * @param paymentMethod
	 * @param amount
	 * @return int the notification id the entry in database
	 */
	public int sendNotification(NotificationType type,int studentId,ModeOfPayment modeOfPayment,String paymentMethod,double amount);
	
	
	public UUID getReferenceId(int notificationId); 
}
