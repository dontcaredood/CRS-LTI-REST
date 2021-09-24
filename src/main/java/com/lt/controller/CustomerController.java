package com.lt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.Customer;
import com.lt.bean.Professor;
import com.lt.bean.Student;
import com.lt.business.AdminInterfaceImpl;
import com.lt.exceptions.CourseNotFoundException;

@RestController
@RequestMapping("/sandy")
public class CustomerController {
	AdminInterfaceImpl adminInterfaceImpl = AdminInterfaceImpl.getInstance();

	@RequestMapping(value="/test", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<Customer> test() {

		Customer s = new Customer();
		
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
}
