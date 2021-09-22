package com.lt.dao;

import java.util.HashMap;
import java.util.List;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.bean.RegisteredCourses;
import com.lt.bean.Student;

public interface ProfessorDao {

	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param userId, prof id of the professor
	 * @return get the courses offered by the professor.
	 */
	public List<Course> getCoursesByProfessor(String userId);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 */
	public List<RegisteredCourses> getEnrolledStudents(String profId);
	
	/**
	 * Method to Grade a student using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code for the corresponding 
	 * @return: returns the status after adding the grade
	 */
	public Boolean addGrade(int studentId,String courseCode,String grade);


	/**
	 * Method to Get professor name by id
	 * @param profId
	 * @return Professor Id in string
	 */
	public String getProfessorById(String profId);

	
} 