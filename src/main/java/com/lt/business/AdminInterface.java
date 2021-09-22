package com.lt.business;

import java.util.ArrayList;
import java.util.List;

import com.lt.bean.Course;
import com.lt.bean.GradeCard;
import com.lt.bean.Professor;
import com.lt.bean.Student;

public interface AdminInterface {

	/**
	 * Method to add professor in database
	 * @param professor
	 * @return boolean to check if the professor is added
	 */
	public boolean addProfessor(Professor professor);
	
	/**
	 * Method to approve student in database
	 * @param studentId
	 * @return boolean to check if the student is approved
	 */
	public boolean approveStudent(int studentId);
	
	/**
	 * Method to add courses in database
	 * @param course
	 * @return boolean to check if the course is added
	 */
	public boolean addCourses(Course course);
	
	/**
	 * Method to remove courses from database
	 * @param courseCode
	 * @return boolean to check if the course is removed
	 */
	public boolean removeCourse(String courseCode);
	
	/**
	 * Method to generate the grade card of the student
	 * @return the list of the grades of a student
	 */
	public List<GradeCard> generateReport();
	
	/**
	 * Method to view the list of the courses of the student
	 * @return the list of the courses of a student
	 */
	public ArrayList<Course> showCourses();
	
	/**
	 * Method to show all the professors
	 * @return the list of all the professors
	 */
	public ArrayList<Professor> showProfessors();
	
	/**
	 * Method to show all the pending approvals od student
	 * @return the list of pending approvals
	 */
	public List<Student> showPendingForApprovalStudent();
	
}
