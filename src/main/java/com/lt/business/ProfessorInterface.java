package com.lt.business;

import java.sql.SQLException;
import java.util.*;
import com.lt.bean.*;
import com.lt.exceptions.GradeNotAddedException;

public interface ProfessorInterface {

	
	/**
	 * Method to grade a Student
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 * @return boolean indicating if grade is added or not
	 * @throws GradeNotAddedException
	 */
	public boolean addGrade(int studentId,String courseCode,String grade) throws GradeNotAddedException;
	
	/**
	 * Method to view all the enrolled students
	 * @param profId: professor id 
	 * @return List of enrolled students
	 */
	public List<RegisteredCourses> viewEnrolledStudents(String profId) throws SQLException;
	
	/**
	 * Method to get list of all course a professor is teaching
	 * @param profId: professor id 
	 * @return List of courses the professor is teaching
	 */
	public List<Course> getCourses(String profId);
	
	/**
	 * Method to get the professor name with ID
	 * @param profId
	 * @return Professor name 
	 */
	public String getProfessorById(String profId);

}
