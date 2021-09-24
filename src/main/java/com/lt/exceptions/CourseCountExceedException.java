package com.lt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseCountExceedException extends Exception{
	
	private int num; 
	public CourseCountExceedException(int num )
	{	
		this.num = num;
	} 
	public String getMessage() 
	{
		return "You have already registered for " + num + " courses";
	}
 
}
