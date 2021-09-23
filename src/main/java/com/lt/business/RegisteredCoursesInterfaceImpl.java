package com.lt.business;

import java.sql.SQLException;
import java.util.List;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.dao.RegisteredCoursesDaoImpl;
import com.lt.exceptions.*;
import com.lt.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegisteredCoursesInterfaceImpl implements RegisteredCoursesInterface{


	private static volatile RegisteredCoursesInterfaceImpl instance = null;

	private RegisteredCoursesInterfaceImpl() {
	}

	public static RegisteredCoursesInterfaceImpl getInstance() {
		if (instance == null) {
			synchronized (RegisteredCoursesInterfaceImpl.class) {
				instance = new RegisteredCoursesInterfaceImpl();
			}
		}
		return instance;
	}


	RegisteredCoursesDaoImpl registeredCoursesDaoImpl =  RegisteredCoursesDaoImpl.getInstance();

	public boolean addCourse(String courseCode, int studentId,List<Course> availableCourseList) throws CourseNotFoundException, CourseLimitExceedException, SeatNotAvailableException, SQLException 
	{
		return registeredCoursesDaoImpl.addCourse(courseCode, studentId);
	}

	
	public boolean dropCourse(String courseCode, int studentId,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
		return registeredCoursesDaoImpl.dropCourse(courseCode, studentId);
	}

	
	public double calculateFee(int studentId) throws SQLException {
		return registeredCoursesDaoImpl.calculateFee(studentId);
	}



	public List<Grade> viewGradeCard(int studentId) throws SQLException {
		return registeredCoursesDaoImpl.viewGradeCard(studentId);
	}

	public List<Course> viewCourses(int studentId) throws SQLException {
		return registeredCoursesDaoImpl.viewCourses(studentId);
	}


	public List<Course> viewRegisteredCourses(int studentId) throws SQLException {
		return registeredCoursesDaoImpl.viewRegisteredCourses(studentId);
	}
    

	public boolean getRegistrationStatus(int studentId) throws SQLException {
		return registeredCoursesDaoImpl.getRegistrationStatus(studentId);
	}
	

	public void setRegistrationStatus(int studentId) throws SQLException {
		registeredCoursesDaoImpl.setRegistrationStatus(studentId);
	}


	public boolean addCourse(String courseCode, int studentId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dropCourse(String courseCode, int studentId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}




	public boolean seatAvailable(String courseCode) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int numOfRegisteredCourses(int studentId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isRegistered(String courseCode, int studentId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


}
