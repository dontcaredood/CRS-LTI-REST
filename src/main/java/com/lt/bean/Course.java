package com.lt.bean;

public class Course {
	public Course() {}
	public Course(String courseId, String courseName, String courseDesc, String professorId) 
	{
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.setProfessorId(professorId);
		this.courseDescription = courseDesc;
	}
	
	private String courseId;
	private String courseName;
	private String courseDescription;
	private String professorId;
	
	public final String getCourseName() {
		return courseName;
	}
	public final void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	
	
	
}
