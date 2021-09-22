package com.lt.business;

import java.util.*;

import org.apache.log4j.Logger;

import com.lt.bean.Course;
import com.lt.bean.GradeCard;
import com.lt.bean.Professor;
import com.lt.bean.Student;
import com.lt.bean.User;
import com.lt.dao.AdminDaoImpl;
import com.lt.exceptions.CourseFoundException;
import com.lt.exceptions.CourseNotDeletedException;
import com.lt.exceptions.CourseNotFoundException;
import com.lt.exceptions.ProfessorNotAddedException;
import com.lt.exceptions.StudentNotFoundForApprovalException;
import com.lt.exceptions.UserIdAlreadyInUseException;

public class AdminInterfaceImpl implements AdminInterface{
	
	private static AdminInterfaceImpl adminInterfaceImpl = null;
	
	AdminDaoImpl adminDaoImpl = AdminDaoImpl.getInstance();
	private static Logger logger = Logger.getLogger(AdminDaoImpl.class);
	
	//private Constructor for AdminInterfaceImpl
		private AdminInterfaceImpl(){
			logger.info("AdminInterfaceImpl Instance Created");
		}
		
		//Singleton Implementation Method
		public static AdminInterfaceImpl getInstance(){
			if(adminInterfaceImpl==null){
				synchronized (AdminInterfaceImpl.class){
					adminInterfaceImpl = new AdminInterfaceImpl();
				}
			}
			return adminInterfaceImpl;
		}
	

	public boolean addProfessor(Professor professor) {
		
		boolean isAdded = false;
		try {
			isAdded = adminDaoImpl.addProfessor(professor);
		} catch (UserIdAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProfessorNotAddedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isAdded){
			System.out.println("Professor Added Sucessfully");
		}else{
			System.out.println("Error Adding Professor");
		}
		return isAdded;
		
	}

	

	public boolean addCourses(Course course) {
		boolean isCourseAdded =false;
		try {
			isCourseAdded = adminDaoImpl.addCourse(course);
		} catch (CourseFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCourseAdded;
	}

	public boolean removeCourse(String courseCode) {
		boolean isCourseRemoved =false;
		try {
			isCourseRemoved = adminDaoImpl.deleteCourse(courseCode);
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotDeletedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCourseRemoved;
	}

	public List<GradeCard> generateReport() {
		//List<GradeCard> list = adminDaoImpl.getGradeCardDetails();
		//return list;
		return null;
		
	}
	
	public ArrayList<Course> showCourses(){
		ArrayList<Course> courseList = (ArrayList<Course>) adminDaoImpl.viewCourses();
		return courseList;
	}
	public ArrayList<Professor> showProfessors(){
		ArrayList<Professor> professorList = (ArrayList<Professor>) adminDaoImpl.viewProfessors();
		return professorList;
		}

	public boolean approveStudent(int studentId) {
		try {
			adminDaoImpl.approveStudent(studentId);
		} catch (StudentNotFoundForApprovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// TODO Auto-generated method stub
		return false;
	}



	public List<Student> showPendingForApprovalStudent() {
		return adminDaoImpl.viewPendingAdmissions();
	}
	
}
