package com.cst438.domain;

import java.util.ArrayList;

/*
 * a transfer object that is a list of assignment details
 */
// to use the inner class, we need to use the outer class to refer to it
// ex.) AssignmentListDTO.AssignmentDTO
public class AssignmentListDTO {
	
	// inner class is one assignment
	// instance of assignmentDTO(an array of assignments)
	// could have been defined as a separate class, but since this class is small, we are putting it inside this outer class
	public static class AssignmentDTO {
		public int assignmentId;
		public String assignmentName;
		public String dueDate;
		public String courseTitle;
		public int courseId;
		
		public AssignmentDTO(int assignmentId, int courseId, String assignmentName, String dueDate,
				String courseTitle) {
			this.assignmentId = assignmentId;
			this.courseId = courseId;
			this.assignmentName = assignmentName;
			this.dueDate = dueDate;
			this.courseTitle = courseTitle;
		}
		
		public AssignmentDTO() {

		}

		@Override
		public String toString() {
			return "[assignmentId=" + assignmentId + ", assignmentName=" + assignmentName + ", dueDate="
					+ dueDate + ", courseTitle=" + courseTitle + ", courseId=" + courseId + "]";
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AssignmentDTO other = (AssignmentDTO) obj;
			if (assignmentId != other.assignmentId)
				return false;
			if (assignmentName == null) {
				if (other.assignmentName != null)
					return false;
			} else if (!assignmentName.equals(other.assignmentName))
				return false;
			if (courseId != other.courseId)
				return false;
			if (courseTitle == null) {
				if (other.courseTitle != null)
					return false;
			} else if (!courseTitle.equals(other.courseTitle))
				return false;
			if (dueDate == null) {
				if (other.dueDate != null)
					return false;
			} else if (!dueDate.equals(other.dueDate))
				return false;
			return true;
		}	
		
	}
	
	// holds the arraylist of AssignmentDTO
	public ArrayList<AssignmentDTO> assignments = new ArrayList<>();

	@Override
	public String toString() {
		return "AssignmentListDTO " + assignments ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentListDTO other = (AssignmentListDTO) obj;
		if (assignments == null) {
			if (other.assignments != null)
				return false;
		} else if (!assignments.equals(other.assignments))
			return false;
		return true;
	}
	
}
