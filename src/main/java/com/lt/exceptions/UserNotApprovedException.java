package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotApprovedException extends Exception{
	private String userId;
	
	/**
	 * Constructor
	 * @param userId
	 */
	public UserNotApprovedException(String userId) {
		this.userId = userId;
	}

	/**
	 * Getter for userId
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

}
