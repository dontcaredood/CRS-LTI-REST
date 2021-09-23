package com.lt.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.lt.bean.Course;
import com.lt.bean.GradeCard;
import com.lt.bean.Student;
import com.lt.constants.Gender;
import com.lt.dao.StudentDaoImpl;
import com.lt.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public class StudentInterfaceImpl implements StudentInterface{
	
	private static Logger logger = Logger.getLogger(StudentInterfaceImpl.class);
	StudentDaoImpl studentDaoImpl=  StudentDaoImpl.getInstance();
	private static volatile StudentInterfaceImpl instance=null;
	private StudentInterfaceImpl()
	{

	}
	
	public static StudentInterfaceImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(StudentInterfaceImpl.class){
				instance=new StudentInterfaceImpl();
			}
		}
		return instance;
	}

	public int register(Student student) throws StudentNotRegisteredException{
		int studentId;
		try
		{
			
			studentId=studentDaoImpl.addStudent(student);
			
		}
		catch(StudentNotRegisteredException ex)
		{
			throw ex;
		}
		return studentId;
	}
	
	
	public int getStudentId(String userId) {
		return studentDaoImpl.getStudentId(userId);
	
	}
	
	
	public boolean isApproved(int studentId) {
		return studentDaoImpl.isApproved(studentId);
	}



	
}