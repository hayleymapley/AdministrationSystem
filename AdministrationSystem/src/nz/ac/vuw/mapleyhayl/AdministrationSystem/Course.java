package nz.ac.vuw.mapleyhayl.AdministrationSystem;

import java.util.*;

public class Course {

	private String name;
	private Map<Student, String> studentGrades = new HashMap<>();
	private ArrayList<Student> currentlyEnrolled = new ArrayList<>();

	public Course(String name) {
		this.name = name;
	}

	public void addGrades(Student student, String grade) {
		studentGrades.put(student, grade);
	}

	public void enroll(Student student) {
		currentlyEnrolled.add(student);
	}
	
	public void unenroll(Student student) {
		currentlyEnrolled.remove(student);
	}

	public void printStudentGrades() {
		for (Student s : studentGrades.keySet()) {
			if (!studentGrades.get(s).equals("0")) {
				System.out.println(s + ": " + studentGrades.get(s).toString());
			}
		}
	}

	public void printEnrolledStudents() {
		for (Student s : currentlyEnrolled) {
			System.out.println(s);
		}
	}

	public int size() {
		return currentlyEnrolled.size();
	}

	//ACCESSORS
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
