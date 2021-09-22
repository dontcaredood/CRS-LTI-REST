package com.lt.bean;

public class GradeCard extends Grade{
	public GradeCard(String courseCode, String courseName, String grade) {
		super(courseCode, courseName, grade);
		// TODO Auto-generated constructor stub
	}
	private String gradeStatus;
	private String remarks;
	private String studentDepartment;

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getStudentDepartment() {
		return studentDepartment;
	}
	public void setStudentDepartment(String studentDepartment) {
		this.studentDepartment = studentDepartment;
	}
	public String getGradeStatus() {
		return gradeStatus;
	}
	public void setGradeStatus(String gradeStatus) {
		this.gradeStatus = gradeStatus;
	}
	
	}
