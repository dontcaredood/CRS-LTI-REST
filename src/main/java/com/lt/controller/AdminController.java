package com.lt.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.Professor;
import com.lt.bean.Student;
import com.lt.bean.User;
import com.lt.business.AdminInterfaceImpl;
import com.lt.exceptions.CourseFoundException;
import com.lt.exceptions.CourseNotDeletedException;
import com.lt.exceptions.CourseNotFoundException;
import com.lt.exceptions.ProfessorNotAddedException;
import com.lt.exceptions.StudentNotFoundForApprovalException;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	@Autowired
	AdminInterfaceImpl adminInterfaceImpl;

	/**
	 * This method adds the professor in the database
	 * @param professor
	 * @param user
	 * @return the response whether the professor is added or not
	 * @throws ValidationException
	 * @throws ProfessorNotAddedException
	 */
	@RequestMapping(value="/addProfessor", method=RequestMethod.POST)
	public ResponseEntity<String> addProfessor(@RequestBody Professor professor) throws ValidationException, ProfessorNotAddedException {
		try{
			adminInterfaceImpl.addProfessor(professor);
			return new ResponseEntity<>("Professor succesfully added : " + professor.getUserId(), HttpStatus.OK);
			}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * This method add the course in the database
	 * @param course
	 * @return the response whether the course is added or not
	 * @throws ValidationException
	 * @throws CourseFoundException
	 */
	@RequestMapping(value="/addCourse", method=RequestMethod.POST) 
	public ResponseEntity<String> addCourse(@RequestBody Course course) throws ValidationException, CourseFoundException {
		try {
			adminInterfaceImpl.addCourses(course);
			return new ResponseEntity<>("Course succesfully added : " + course.getCourseId(), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}
		
	}
	
	/**
	 * This method approves the student
	 * @param studentId
	 * @return the response whether the student is approved or not with the studentId
	 * @throws ValidationException
	 * @throws StudentNotFoundForApprovalException
	 */
	@RequestMapping(value="/approveStudent", method=RequestMethod.POST)
	public ResponseEntity<String> approveStudent(@RequestParam int studentId) throws ValidationException, StudentNotFoundForApprovalException{
		try{
			//List<Student> studentList = adminInterfaceImpl.showPendingForApprovalStudent();
			adminInterfaceImpl.approveStudent(studentId);
			return new ResponseEntity<>("Student with studentId: " + studentId + " approved", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	/**
	 * This method shows the list of courses
	 * @return the list of the courses present in the database
	 */
	@RequestMapping(value="/showCourses", method=RequestMethod.GET)
	public List<Course> showCourses() throws ValidationException, CourseNotFoundException{
		return adminInterfaceImpl.showCourses();
		
	}
	
	
	/**
	 * This method shows the list of professors
	 * @return the list of the professors present in the database
	 */
	@RequestMapping(value="/showProfessors", method=RequestMethod.GET)
	public List<Professor> showProfessors() {
		return adminInterfaceImpl.showProfessors();
		
	}
	
	
	/**
	 * This method shows the list of pending requests
	 * @return the list of the courses present in the database
	 */
	@RequestMapping(value="/showPendingRequests", method=RequestMethod.GET)
	public List<Student> showPendingRequest() {
		return adminInterfaceImpl.showPendingForApprovalStudent();
		
	}
	
	/**
	 * This method is to delete courses
	 * @param courseId
	 * @return the response whether the course is deleted
	 * @throws ValidationException
	 * @throws CourseNotFoundException
	 * @throws CourseNotDeletedException
	 */
	@RequestMapping(value="/deleteCourses", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteCourse(@RequestParam String courseId) throws ValidationException, CourseNotFoundException, CourseNotDeletedException{
		try{
			adminInterfaceImpl.removeCourse(courseId);
			return new ResponseEntity<>("Course "+courseId+" deleted", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	

}
