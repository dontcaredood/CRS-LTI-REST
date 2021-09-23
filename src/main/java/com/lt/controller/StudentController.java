package com.lt.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.business.NotificationInterfaceImpl;
import com.lt.business.RegisteredCoursesInterfaceImpl;
import com.lt.business.StudentInterfaceImpl;
import com.lt.constants.ModeOfPayment;
import com.lt.constants.NotificationType;
import com.lt.exceptions.CourseLimitExceedException;
import com.lt.exceptions.CourseNotFoundException;
import com.lt.exceptions.SeatNotAvailableException;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

	StudentInterfaceImpl studentDaoImpl = StudentInterfaceImpl.getInstance();
	RegisteredCoursesInterfaceImpl registeredCourses = RegisteredCoursesInterfaceImpl.getInstance();

	NotificationInterfaceImpl notificationInterfaceImpl = NotificationInterfaceImpl.getInstance();

	private static Logger logger = Logger.getLogger(StudentController.class);

	@RequestMapping(value = "/registerCourses", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> registerCourses(@RequestBody List<String> courseList,
			@QueryParam("studentId") int studentId) throws ValidationException, SQLException {
		try {
			List<Course> availableCourseList = registeredCourses.viewCourses(studentId);
			Set<String> hash_set = new HashSet<String>();

			for (String courseCode : courseList) {
				registeredCourses.addCourse(courseCode, studentId, availableCourseList);

				if (!hash_set.add(courseCode))
					return new ResponseEntity<>("Duplicate value  : " + courseCode, HttpStatus.OK);
			}

			for (String courseCode : courseList)
				registeredCourses.addCourse(courseCode, studentId);

			registeredCourses.setRegistrationStatus(studentId);
		} catch (CourseLimitExceedException | SeatNotAvailableException | CourseNotFoundException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
	}

	@RequestMapping(value = "/viewAvailableCourses", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Course>> viewCourse(@QueryParam("studentId") int studentId)
			throws ValidationException, SQLException {
		try {
			return new ResponseEntity<>(registeredCourses.viewCourses(studentId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/dropCourses", method = RequestMethod.DELETE)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<String> dropCourse(@QueryParam("courseCode") String courseCode,
			@QueryParam("studentId") int studentId) throws ValidationException, SQLException {

		try {

			List<Course> registeredCourseList = registeredCourses.viewRegisteredCourses(studentId);
			registeredCourses.dropCourse(courseCode, studentId, registeredCourseList);
			return new ResponseEntity<>("You have successfully dropped Course : " + courseCode, HttpStatus.OK);
		} catch (CourseNotFoundException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>("You have not registered for course : " + e.getCourseCode(), HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/addCourse", method = RequestMethod.PUT)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<String> addCourse(@QueryParam("courseCode") String courseCode,
			@QueryParam("studentId") int studentId)
			throws ValidationException, SQLException, CourseLimitExceedException, SeatNotAvailableException {

		try {

			List<Course> availCourseList = registeredCourses.viewCourses(studentId);
			registeredCourses.addCourse(courseCode, studentId, availCourseList);
			// registeredCourses.addCourse(courseCode, studentId);

			return new ResponseEntity<>("You have successfully added Course : " + courseCode, HttpStatus.OK);

		} catch (CourseNotFoundException e) {
			logger.info(e.getMessage());

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/viewRegisteredCourse", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Course>> viewRegisteredCourse(@QueryParam("studentId") int studentId)
			throws ValidationException, SQLException {

		return new ResponseEntity<>(registeredCourses.viewRegisteredCourses(studentId), HttpStatus.OK);
	}

	@RequestMapping(value = "/viewGradeCard", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Grade>> viewGradeCard(@QueryParam("studentId") int studentId)
			throws ValidationException, SQLException {

		List<Grade> grade_card = registeredCourses.viewGradeCard(studentId);

		return new ResponseEntity<>(grade_card, HttpStatus.OK);

	}

	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<String> makePayment(@QueryParam("paymentMode") int paymentMode,
			@QueryParam("studentId") int studentId) throws SQLException {

		double fee = registeredCourses.calculateFee(studentId);

		fee = registeredCourses.calculateFee(studentId);
		logger.info("Your total fee  = " + fee);
		ModeOfPayment mode = ModeOfPayment.getModeofPayment(paymentMode);

		int notify = notificationInterfaceImpl.sendNotification(NotificationType.PAYMENT, studentId, mode, "ONLINE",
				fee);

		logger.info("Your Payment is successful");
		return new ResponseEntity<>("Your total fee  = " + fee + "\n" + "Your Payment is successful\n", HttpStatus.OK);
	}
}
