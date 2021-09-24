package com.lt.business;
import com.lt.dao.*;
import com.lt.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class UserInterfaceImpl implements UserInterface{
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	public boolean updatePassword(String userID,String newPassword) {
		return userDaoImpl.updatePassword(userID, newPassword);
	}


	public boolean verifyCredentials(String userID, String password) throws UserNotFoundException {
		//DAO class
		try
		{
			return userDaoImpl.verifyCredentials(userID, password);		
		}catch (Exception e) {
			throw new UserNotFoundException(userID);
		}
	}
	
	
	public String getRole(String userId) throws UserNotFoundException {
		return userDaoImpl.getRole(userId);
	}

}
