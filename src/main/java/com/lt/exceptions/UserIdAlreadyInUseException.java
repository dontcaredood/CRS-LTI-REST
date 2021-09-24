/**
 * 
 */
package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserIdAlreadyInUseException extends Exception{
	private String userId;
	
	
	public UserIdAlreadyInUseException(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setProfessorId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String getMessage() {
		return "userId: " + userId + " is already in use.";
	}

}
