package com.lt.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.Customer;
import com.lt.business.AdminInterfaceImpl;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/details")
		@ResponseBody
	 public List<Course> details(){
		
		AdminInterfaceImpl adminInterfaceImpl = AdminInterfaceImpl.getInstance();
			
		
		return adminInterfaceImpl.showCourses();	
	
	}
}
