package com.lt.bean;

public class Grade {
	
	private String courseCode;
	private String courseName;
	private String grade;
	
	
	public Grade(String courseCode, String courseName, String grade) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.grade = grade;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	
}
