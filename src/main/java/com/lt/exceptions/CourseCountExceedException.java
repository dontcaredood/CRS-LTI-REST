package com.lt.exceptions;
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
