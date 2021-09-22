/**
 * 
 */
package com.lt.exceptions;


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
