package com.lt.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.RegisteredCourses;
import com.lt.business.ProfessorInterfaceImpl;
import com.lt.exceptions.GradeNotAddedException;

@RestController
@RequestMapping("/professor")
@CrossOrigin
public class ProfessorController {

	public static Logger logger = LogManager.getLogger(ProfessorController.class);

	@Autowired
	private ProfessorInterfaceImpl professorImpl;
	/*
	 * Method to View Enrolled Student
	 *
	 * @PathVariable professorId
	 *
	 * @return ResponseEntity
	 *
	 * @throws ValidationException,SQLException
	 */
	@RequestMapping(value = "/viewenrolledstudents/{professorId}", method = RequestMethod.GET)
	@ExceptionHandler({ GradeNotAddedException.class })
	@ResponseBody
	public List<RegisteredCourses> viewEnrolledStudents(@PathVariable(value = "professorId") String professorId) {
		try {
			logger.info("Professor ID: " + professorId);
			List<RegisteredCourses> list = professorImpl.viewEnrolledStudents(professorId);
			return list;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	/*
	 * Method to get Courses
	 *
	 * @PathVariable professorId
	 *
	 * @return ResponseEntity
	 *
	 * @throws ValidationException,SQLException
	 */
	@RequestMapping(value = "/getcourses/{professorId}", method = RequestMethod.GET)
	@ExceptionHandler({ GradeNotAddedException.class })
	@ResponseBody
	public ResponseEntity<List<Course>> getCourses(@PathVariable(value = "professorId") String professorId) {
		List<Course> list = professorImpl.getCourses(professorId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/*
	 * Method to View Professor by ID
	 *
	 * @PathVariable professorId
	 *
	 * @return ResponseEntity
	 *
	 * @throws ValidationException,SQLException
	 */
	@RequestMapping(value = "/getprofessorbyid/{professorId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getProfessorById(@PathVariable(value = "professorId") String professorId) {
		try {
			return new ResponseEntity<>(professorImpl.getProfessorById(professorId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Method to Add Grade
	 *
	 * @RequestParam studentId
	 * 
	 * @RequestParam courseCode
	 *
	 * @RequestParam grade
	 *
	 * @return ResponseEntity
	 *
	 * @throws ValidationException,SQLException
	 */

	@RequestMapping(value = "/addgrade", method = RequestMethod.POST)
	@ExceptionHandler({ GradeNotAddedException.class })
	public ResponseEntity<String> addGrade(@RequestParam int studentId, @RequestParam String courseCode,
			@RequestParam String grade) throws GradeNotAddedException {
		try {
			boolean result = professorImpl.addGrade(studentId, courseCode, grade);
			return new ResponseEntity<>(result?"Grade Added for"+studentId+".":"Grade Not added", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

}
