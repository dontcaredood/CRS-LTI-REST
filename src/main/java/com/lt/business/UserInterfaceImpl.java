package com.lt.business;
import com.lt.dao.*;
import com.lt.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInterfaceImpl implements UserInterface{
	
	private static volatile UserInterfaceImpl instance=null;
	UserDaoImpl userDaoImpl =  UserDaoImpl.getInstance();
	private UserInterfaceImpl()
	{

	}
	
	public static UserInterfaceImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(UserInterfaceImpl.class){
				instance=new UserInterfaceImpl();
			}
		}
		return instance;
	}
	public boolean updatePassword(String userID,String newPassword) {
		return userDaoImpl.updatePassword(userID, newPassword);
	}


	public boolean verifyCredentials(String userID, String password) throws UserNotFoundException {
		//DAO class
		try
		{
			return userDaoImpl.verifyCredentials(userID, password);		
		}
		finally
		{
			
		}
	}
	
	
	public String getRole(String userId) {
		return userDaoImpl.getRole(userId);
	}


	



}
