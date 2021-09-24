/**
 * 
 */
package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

	private String userId;

	/***
	 * Getter function for UserId
	 * @param userId
	 */
	public UserNotFoundException(String userId) {
		this.userId = userId;
	}

	/**
	 * Message thrown by exception
	 */
	@Override
	public String getMessage() {
		return "User with userId: " + userId + " not found.";
	}

}
