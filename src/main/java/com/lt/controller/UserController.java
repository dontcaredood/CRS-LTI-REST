package com.lt.controller;

import javax.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;
import javax.validation.constraints.*;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lt.bean.Student;
import com.lt.business.StudentInterfaceImpl;
import com.lt.business.UserInterfaceImpl;
import com.lt.constants.Role;
import com.lt.exceptions.GradeNotAddedException;
import com.lt.exceptions.StudentNotRegisteredException;
import com.lt.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	@Autowired
	private UserInterfaceImpl userInterfaceImpl ;
	@Autowired
	private StudentInterfaceImpl studentInterfaceImpl;
	private static Logger logger = Logger.getLogger(UserController.class);

	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ExceptionHandler({ValidationException.class})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password)  throws ValidationException {
		try 
		{
			boolean loggedin=userInterfaceImpl.verifyCredentials(userName, password);
				if(loggedin)
				{
					String role=userInterfaceImpl.getRole(userName);
					Role userRole=Role.stringToName(role);
					switch(userRole)
					{
					
					case STUDENT:
						int studentId=studentInterfaceImpl.getStudentId(userName);
						boolean isApproved=studentInterfaceImpl.isApproved(studentId);
						if(!isApproved)	
						{
							return new ResponseEntity<>("Student Login unsuccessful for "+userName+" has not been approved by the administration!" ,HttpStatus.OK);
						}
						break;
						
					case ADMIN:
							return new ResponseEntity<>("Login successful! Admin",HttpStatus.OK );
					case PROFESSOR:
						return new ResponseEntity<>("Login successful! Professor", HttpStatus.OK );
						
					}
					return new ResponseEntity<>("Login successful", HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<>("Invalid credentials!", HttpStatus.NOT_FOUND);
				}
		}
		catch (UserNotFoundException e) 
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}		
	}
	
	@RequestMapping(value="/verify", method = RequestMethod.GET)
	@ExceptionHandler({UserNotFoundException.class})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> verifyCredentials(@RequestParam String userName, @RequestParam String password) throws UserNotFoundException {
		
		try{
			logger.info("Verify User: "+userName);
			boolean flag = userInterfaceImpl.verifyCredentials(userName, password);
			return new ResponseEntity<>(flag?"TRUE":"FALSE", HttpStatus.OK);
		}catch(UserNotFoundException e) {
			logger.error("No User Found: "+userName);
			return new ResponseEntity<String>("No User Found", HttpStatus.NOT_FOUND);

		}
	}
	
	@RequestMapping(value="/getRole", method = RequestMethod.GET)
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<String> getRole(@RequestParam String username) {
		return new ResponseEntity<>(userInterfaceImpl.getRole(username),HttpStatus.OK);
	}
	
	@RequestMapping(value="/studentRegister", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({StudentNotRegisteredException.class})
	public ResponseEntity<String> studentRegister(@RequestBody Student student) {
		int studentId;
		try
		{
			studentId = studentInterfaceImpl.register(student);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<>("Something went wrong! Please try again.", HttpStatus.NOT_FOUND); 
		}
				
		return new ResponseEntity<>("Registration Successful for "+studentId,HttpStatus.OK); 
	}


}
