package nz.ac.vuw.mapleyhayl.AdministrationSystem;

import java.util.*;
import java.io.*;
import ecs100.*;

//combine student hashmaps

public class AdministrationSystem {

	private Map<Integer, Student> idHash = new HashMap<Integer, Student>();
	private Map<String, Student> nameHash = new HashMap<String, Student>();
	private ArrayList<Student> students = new ArrayList<>();
	private Map<String, Course> courses = new HashMap<>();

	Scanner in = new Scanner(System.in);

	public AdministrationSystem() {
		System.out.println("------- ADMINISTRATION SYSTEM -------");
		this.loadFile();
		this.menu();
	}

	public void menu() {
		System.out.println("");
		System.out.println("[1] Print everything");
		System.out.println("[2] Search student by name");
		System.out.println("[3] Search student by ID number");
		System.out.println("[4] Add course");
		System.out.println("[5] View past student grades by course");
		System.out.println("[6] View current students by course");
		System.out.println("[7] Enroll student");
		System.out.println("[8] Assign grade");
		System.out.println("[9] Unenroll student");
		int ans = in.nextInt();
		while (!(ans > 0 && ans < 10)) {
			System.out.println("Please choose a valid menu option: ");
			ans = in.nextInt();
		}
		switch (ans) {
		case 1 :
			this.print();
			this.printCourses();
			break;
		case 2 :
			this.searchName();
			break;
		case 3 : 
			this.searchID();
			break;
		case 4 :
			this.addCourse();
			break;
		case 5 :
			this.viewCourse();
			break;
		case 6 :
			this.viewEnrolled();
			break;
		case 7 :
			this.enroll();
			break;
		case 8 :
			this.assignGrade();
			break;
		case 9 :
			this.unenroll();
			break;
		}
		this.menu();
	}

	public void print() {
		for (Student s : students) {
			System.out.println(s.toString());
			s.printCourseGrades();
			System.out.println("");
		}
	}

	public void printCourses() {
		for (String c : courses.keySet()) {
			System.out.println(c);
		}
	}

	public void add() {
		System.out.println("Enter the ID of the student: ");
		int ID = in.nextInt();
		in.nextLine();
		System.out.println("Enter the name of the student: ");
		String name = in.nextLine();
		Student newStudent = new Student(ID, name);
		students.add(newStudent);
		idHash.put(ID, newStudent);
		nameHash.put(name, newStudent);
		System.out.println("Student added - currently not enrolled in any courses.");
	}
	
	public void loadFile() {
		try {
			File file = new File("testStudents.txt");
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				int ID = scan.nextInt();
				scan.nextLine();
				String name = scan.nextLine();
				String courseCode = scan.nextLine();
				Course newCourse = new Course(courseCode);
				if (!courses.containsKey(courseCode)) {
					courses.put(courseCode, newCourse);
				}
				String courseGrade = scan.nextLine();
				Student newStudent = new Student(ID, name);
				newStudent.addCourseGrade(newCourse, courseGrade);
				if (courseGrade.equals("0")) {
					courses.get(courseCode).enroll(newStudent);
				}
				courses.get(courseCode).addGrades(newStudent, courseGrade);
				//condense these below
				idHash.put(ID, newStudent);
				nameHash.put(name, newStudent);
				students.add(newStudent);
			}
			scan.close();
		} catch (Exception e) {}
	}

	public void searchName() {
		System.out.println("Please enter student's full name: ");
		in.nextLine();
		String ans = in.nextLine();
		if (nameHash.containsKey(ans)) {
			System.out.println("\nResult:");
			System.out.println(nameHash.get(ans));
		} else {
			System.out.println("Student not found.");
		}
	}

	public void searchID() {
		System.out.println("Please enter student's ID: ");
		in.nextLine();
		int ans = in.nextInt();
		if (idHash.containsKey(ans)) {
			System.out.println("Result: ");
			System.out.println(idHash.get(ans));
		} else {
			System.out.println("Student not found.");
		}
	}

	public void addCourse() {
		System.out.println("Enter the course code: ");
		String ans = in.next();
		Course newCourse = new Course(ans);
		if (!courses.containsKey(ans)) {
			courses.put(newCourse.getName(), newCourse);
		} else {
			System.out.println("Course already exists!");
		}
	}
	
	public void enroll() {
		System.out.println("Enter course to enroll in: ");
		String code = in.next();
		while (!courses.containsKey(code)) {
			System.out.println("Please enter valid course code: ");
			code = in.next();
		}
		System.out.println("Enter student ID to enroll: ");
		int id = in.nextInt();
		while (!idHash.containsKey(id)) {
			System.out.println("Please enter valid student ID: ");
			id = in.nextInt();
		}
		courses.get(code).enroll(idHash.get(id));
		idHash.get(id).addCourseGrade(courses.get(code), "0");
		System.out.println(idHash.get(id).getName() + " has been enrolled in " + code);
	}
	
	public void viewCourse() {
		System.out.println("Enter the course ID: ");
		String ans = in.next();
		Course course = null;
		if (courses.containsKey(ans)) {
			course = courses.get(ans);
			course.printStudentGrades();
		}
	}

	public void viewEnrolled() {
		System.out.println("Enter the course ID: ");
		String ans = in.next();
		Course course = null;
		if (courses.containsKey(ans)) {
			course = courses.get(ans);
			course.printEnrolledStudents();
		}
		System.out.println("Total students: " + course.size());
	}

	public void assignGrade() {
		System.out.println("Enter the course you wish to add a grade in: ");
		String code = in.next();
		while (!courses.containsKey(code)) {
			System.out.println("No such course - please enter course code");
			code = in.next();
		}
		courses.get(code).printEnrolledStudents();
		System.out.println("Enter ID of student to assign a grade: ");
		int id = in.nextInt();
		while (!idHash.containsKey(id)) {
			System.out.println("No such student - please enter student ID: ");
			id = in.nextInt();
		}
		System.out.println("Enter the letter grade to assign: ");
		String grade = in.next();
		courses.get(code).addGrades(idHash.get(id), grade);
		idHash.get(id).addCourseGrade(courses.get(code), grade);
	}
	
	public void unenroll() {
		System.out.println("Enter the course to unenroll from: ");
		String code = in.next();
		while (!courses.containsKey(code)) {
			System.out.println("No such course - please enter course code");
			code = in.next();
		}
		System.out.println("Enter ID of student to unenroll: ");
		int id = in.nextInt();
		while (!idHash.containsKey(id)) {
			System.out.println("No such student - please enter student ID: ");
			id = in.nextInt();
		}
		courses.get(code).unenroll(idHash.get(id));
		idHash.get(id).removeCourse(courses.get(code));
		System.out.println(idHash.get(id).getName() + " has been unenrolled from " + courses.get(code));
	}
	
	public static void main(String[] args) {
		//		Student s1 = new Student(100, "Hayley Mapley");
		//		Student s2 = new Student(500, "Oliver Pelham");
		//		Student s3 = new Student(800, "Anna Harris");
		//		Map<Integer, String> hash = new HashMap<Integer, String>();
		//		hash.put(s1.getID(), s1.getName());
		//		hash.put(s2.getID(), s2.getName());
		//		hash.put(s3.getID(), s3.getName());
		//		for (int c : hash.keySet()) {
		//			System.out.println("The name of student ID "+ c + " is "+ hash.get(c));
		//		}
		//		hash.remove(500);
		//		Map<Integer, String> tree = new TreeMap<Integer, String>(hash);
		//		for (int c : tree.keySet()) {
		//			System.out.println("The name of student ID "+ c + " is "+ hash.get(c));
		//		}
		new AdministrationSystem();
	}
}
