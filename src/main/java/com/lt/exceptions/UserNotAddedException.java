package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotAddedException extends Exception{
	private String userId;
	
	public UserNotAddedException(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Getter function for UserId
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "UserId: " + userId + " is already in use!";
	}
}
