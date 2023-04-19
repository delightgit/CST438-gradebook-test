package com.cst438.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.GradebookDTO;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class AssignmentController {
	
	@Autowired
	AssignmentRepository asRep;
	
	@Autowired
	CourseRepository cRep;
	
	@PostMapping("/assignment")
	@Transactional
	public AssignmentListDTO.AssignmentDTO newAssignment(@RequestBody AssignmentListDTO.AssignmentDTO assignment) {
//		Course c = cRep.findById(assignment.courseId).orElse(null); // does a look up and if found, returns the course table and if not returns a null value
//		if (c == null) { // error, exit the function
//			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Course not found. "+ assignment.courseId );
//		}
//		Assignment a = new Assignment(); // id(primary key) = int (default = 0), name, duedate, course = null
//		a.setName(assignment.assignmentName); // 
//		a.setDueDate(Date.valueOf(assignment.dueDate));
//		a.setCourse(c); // link assignment to course
//		a.setNeedsGrading(1);
//		Assignment b = asRep.save(a); // save will do insert to the DB, DB generates a primary key and updated assignment b
//		// Spring does not update entities. It creates a new entity with the primary key and returns that to the program
//		// save is used to create and update, but in this case we are using it to insert a new row
//		// could have been Assignment a
//		assignment.assignmentId = b.getId();
//		return assignment;
//		// create an instance of assignmentDTO
//		// copy data from  b into the assignmentDTO object
//		// return assignmentDTO object
//		// which will be converted into JSON format and returned to the client
		String userEmail = "dwisneski@csumb.edu";
		// validate course and that the course instructor is the user
		Course c = cRep.findById(assignment.courseId).orElse(null);
		if (c != null && c.getInstructor().equals(userEmail)) {
			// create and save new assignment
			// update and return dto with new assignment primary key
			Assignment a = new Assignment();
			a.setCourse(c);
			a.setName(assignment.assignmentName);
			a.setDueDate(Date.valueOf(assignment.dueDate));
			a.setNeedsGrading(1);
			a = asRep.save(a);
			assignment.assignmentId=a.getId();
			return assignment;
			
		} else {
			// invalid course
			throw new ResponseStatusException( 
                           HttpStatus.BAD_REQUEST, 
                          "Invalid course id.");
		}
	}
	// have request body with assignmetDTO
	// get the name and the duedate from the assignment DTO
	// return an assignment DTO, not an entity
	// save will return the assignment entity
	
	@DeleteMapping("/assignment/{assignment_id}")
	@Transactional
	public void deleteAssignment(@PathVariable int assignment_id) {

		Assignment a = asRep.findById(assignment_id).orElse(null);
		
		// verify that the assignment is not graded.
		if (a!=null && a.getNeedsGrading() == 0){
			// OK.  drop the course.
			asRep.delete(a);
		} else {
			// something is not right with the enrollment.  
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment_id invalid. "+assignment_id);
		}
	}
	
	@PostMapping("/assignment/{assignment_id}")
	@Transactional
	public void updateAssignmentName(@RequestBody AssignmentListDTO.AssignmentDTO assignment, @PathVariable int assignment_id) {
		Assignment a = asRep.findById(assignment_id).orElse(null);
		a.setName(assignment.assignmentName);
		
		
		// just updating the object won't update the DB so we need to save it
		asRep.save(a);
	}
}
