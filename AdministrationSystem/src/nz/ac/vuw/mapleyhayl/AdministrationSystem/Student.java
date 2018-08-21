package nz.ac.vuw.mapleyhayl.AdministrationSystem;

import java.util.*;

public class Student {
	
	private int ID;
	private String name;
	private Map<Course, String> courses = new HashMap<>(); 
	
	public Student(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}
	
	public String toString() {
		return ID + " " + name;
	}
	
	public void addCourseGrade(Course course, String grade) {
		courses.put(course, grade);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}

	//ACCESSORS
 	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void printCourseGrades() {
		for (Course s : courses.keySet()) {
			System.out.println(s.getName() + ": " + courses.get(s));
		}
	}
}
