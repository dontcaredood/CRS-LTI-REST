package com.lt.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.Professor;
import com.lt.bean.Student;
import com.lt.bean.User;
import com.lt.business.AdminInterfaceImpl;
import com.lt.exceptions.CourseFoundException;
import com.lt.exceptions.ProfessorNotAddedException;
import com.lt.exceptions.StudentNotFoundForApprovalException;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	
	AdminInterfaceImpl adminInterfaceImpl = AdminInterfaceImpl.getInstance();
	//@Autowired
	//private AdminInterfaceImpl adminInterfaceImpl;
	
	@RequestMapping(value="/addProfessor", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> addProfessor(@RequestBody Professor professor) throws ValidationException, ProfessorNotAddedException {
		try{
			adminInterfaceImpl.addProfessor(professor);
			return new ResponseEntity<>("Professor succesfully added : " + professor.getUserId(), HttpStatus.OK);
			}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value="/addCourse", method=RequestMethod.POST) 
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> addCourse(@RequestBody Course course) throws ValidationException, CourseFoundException {
		try {
			adminInterfaceImpl.addCourses(course);
			return new ResponseEntity<>("Course succesfully added : " + course.getCourseId(), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}
		
	}
	
	
	@RequestMapping(value="/approveStudent", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> approveStudent(@RequestParam int studentId) throws ValidationException, StudentNotFoundForApprovalException{
		try{
			List<Student> studentList = adminInterfaceImpl.showPendingForApprovalStudent();
		adminInterfaceImpl.approveStudent(studentId);
		return new ResponseEntity<>("Student with studentId: " + studentId + " approved", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@RequestMapping(value="/showCourses", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Course> showCourses(){
		return adminInterfaceImpl.showCourses();
		
	}
	
	@RequestMapping(value="/showProfessors", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Professor> showProfessors() {
		return adminInterfaceImpl.showProfessors();
		
	}
	
	@RequestMapping(value="/showPendingRequests", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Student> showPendingRequest() {
		return adminInterfaceImpl.showPendingForApprovalStudent();
		
	}
	

}
