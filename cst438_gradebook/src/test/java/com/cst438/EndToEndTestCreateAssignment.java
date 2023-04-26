package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *  In SpringBootTest environment, the test program may use Spring repositories to 
 *  setup the database for the test and to verify the result.
 */

@SpringBootTest
public class EndToEndTestCreateAssignment {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/Users/broth/Desktop/CST438/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int SLEEP_DURATION = 3000; // 3 second.
	
	public static final String TEST_ASSIGNMENT_NAME = "Test Assignment";
	public static final String TEST_COURSE_TITLE = "Test Course";
	public static final String TEST_STUDENT_NAME = "Test";
	public static final String TEST_DUE_DATE = "2023-03-01";
	public static final int TEST_COURSE_ID = 123456;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;

	@Autowired
	AssignmentRepository assignmentRepository;

	@Test
	public void addCourseTest() throws Exception {

		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on
		
		/*
		 * initialize the WebDriver and get the home page. 
		 */
		Assignment x = null;
		do {			
            for (Assignment a : assignmentRepository.findAll()) {
            	System.out.println(a.getName()); // for debug
            	if (a.getName().equals(TEST_ASSIGNMENT_NAME)) {
        			x = a;
        			break;
        		}
            }
            if (x != null)
                assignmentRepository.delete(x);
        } while (x != null);

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		
		WebDriver driver = new ChromeDriver(ops);
		//WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		

		try {
			driver.findElement(By.id("AddAssignment")).click();
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.xpath("//input[@name='name']")).sendKeys(TEST_ASSIGNMENT_NAME);
			driver.findElement(By.xpath("//input[@name='dueDate']")).sendKeys(TEST_DUE_DATE);
			driver.findElement(By.xpath("//input[@name='courseId']")).sendKeys(Integer.toString(TEST_COURSE_ID));

			driver.findElement(By.xpath("//button[@id='Add']")).click();
			Thread.sleep(SLEEP_DURATION);

			boolean found = false;
			for (Assignment a : assignmentRepository.findAll()) {
				System.out.println(a.getName()); // for debug
            	if (a.getName().equals(TEST_ASSIGNMENT_NAME)) {
            		found=true;
        			break;
        		}
            }
			assertTrue( found, "Assignment was not added.");
			
			
		} catch (Exception ex) {
			throw ex;
		} finally {

			/*
			 *  clean up database so the test is repeatable.
			 */
			
			Assignment y = null;
			for (Assignment a : assignmentRepository.findAll()) {
				System.out.println(a.getName()); // for debug
            	if (a.getName().equals(TEST_ASSIGNMENT_NAME)) {
            		y = a;
        			break;
        		}
            }
			if (y != null) assignmentRepository.delete(y);
			driver.quit();
		}

	}
}