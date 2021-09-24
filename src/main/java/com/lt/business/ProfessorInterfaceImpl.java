package com.lt.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.bean.RegisteredCourses;
import com.lt.bean.Student;
import com.lt.dao.AdminDaoImpl;
import com.lt.dao.ProfessorDaoImpl;
import com.lt.exceptions.GradeNotAddedException;
import com.lt.exceptions.ProfessorNotAddedException;
@Service
@Scope("singleton")
public class ProfessorInterfaceImpl implements ProfessorInterface{
	private static Logger logger = Logger.getLogger(ProfessorInterfaceImpl.class);
	
	@Autowired
	private ProfessorDaoImpl professorDaoImpl;
	
	public boolean addGrade(int studentId,String courseCode,String grade) throws GradeNotAddedException {
		try
		{
			professorDaoImpl.addGrade(studentId, courseCode, grade);
		}
		catch(Exception ex)
		{
			throw new GradeNotAddedException(studentId);
		}
		return true;
	}
	
	
 
	public List<RegisteredCourses> viewEnrolledStudents(String profId){
		List<RegisteredCourses> enrolledStudents=new ArrayList<RegisteredCourses>();
		try
		{
			enrolledStudents=professorDaoImpl.getEnrolledStudents(profId);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return enrolledStudents;
	}

	
	 
	public List<Course> getCourses(String profId) {
		//call the DAO class
		//get the courses for the professor
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered=professorDaoImpl.getCoursesByProfessor(profId);
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return coursesOffered;
	}
	
	 
	public String getProfessorById(String profId) throws ProfessorNotAddedException
	{
		try {
			return professorDaoImpl.getProfessorById(profId);
			
		} catch (ProfessorNotAddedException e) {
			logger.error(e);
			throw new ProfessorNotAddedException(profId);
		}
	}


}
