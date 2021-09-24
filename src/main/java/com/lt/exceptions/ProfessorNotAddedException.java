package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class ProfessorNotAddedException extends Exception{
	private String professorId;
	
	public ProfessorNotAddedException(String professorId) {
		this.professorId = professorId;
	}
	
	/**
	 * Getter function for professorId
	 * @return
	 */
	public String getUserId() {
		return this.professorId;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "professorId: " + professorId + " not added!";
	}
}
