/**
 * 
 */
package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class StudentNotFoundForApprovalException extends Exception {
	private int studentId;
	
	public StudentNotFoundForApprovalException(int studentId) {
		this.studentId = studentId;
	}
	
	/**
	 * Getter function for studentId
	 * @return
	 */
	public int getStudentId() {
		return this.studentId;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "studentId: " + studentId + " not found for approval!" ;
	}
}
