/**
 * 
 */
package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class StudentNotFoundException extends Exception {
	private int studentId;
	
	public StudentNotFoundException(int studentId) {
		this.studentId = studentId;
	}
	
	
	public int getStudentId() {
		return this.studentId;
	}
	

	
	
	public String getMessage() {
		return "studentId: " + studentId + " not found !" ;
	}
}
