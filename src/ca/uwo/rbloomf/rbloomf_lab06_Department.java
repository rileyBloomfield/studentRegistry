package ca.uwo.rbloomf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 Class is used to hold an array of students in the department as well as an array
 of courses offered by the department
 */
public class rbloomf_lab06_Department {

    static int NUM_STUDENTS; //will hold number of students from read file
    static int NUM_COURSES; //will hold number of courses form read file
    static String COURSE_PATH = "Courses.txt"; //holds path to course obtaining file
    static String STUDENTS_PATH = "Students.txt"; //holds path to total students obtaining file
    
    //public rbloomf_lab06_Student[] students; //array will hold all students in a department
    public LinkedList<rbloomf_lab06_Student> students = new LinkedList<>();
    public rbloomf_lab06_Course[] courses;  //array will hold all courses in a department

    //default constructor instantiates a department object by calling loadFile methods
    rbloomf_lab06_Department() {
        loadCourseNames(COURSE_PATH); //will instatiate courses array
        loadStudentNames(STUDENTS_PATH);  //will instatiate students array
        loadCourseEnrollment(); //loads all existing course enrollment files from previous sessions
    }

    /**
     * Method takes a file path and loads courses to initialize course variables.
     * Method finds number of courses, assigns course list size and creates array.
     * @param path 
     */
    private void loadCourseNames(String path) {
        File course = new File(path); //holds file path
        Scanner courseScan = null; //scanner will count lines to find array size and add all elements to array after
        int index = 0;
        //Read file to count lines for number of students
        int count = 0; //hold line number
        try {
            courseScan = new Scanner(course);
            while (courseScan.hasNext()){ //if there is a next line in the file
                count++;
                courseScan.nextLine(); //move to next line
            }
            courseScan.close(); //close scanner after counting lines
        } catch (Exception ex) {
            System.err.println(path + " was not found.");
        }
        
        //Set size of courses array and create array
        System.err.println(count+" courses total."); //print total number of courses from counter
        NUM_COURSES = count; //set array size
        courses = new rbloomf_lab06_Course[NUM_COURSES]; //instantiate courses array
        
        //Populate array with list of course names
        try {
            courseScan = new Scanner(course);
        } catch (FileNotFoundException ex) {
            System.err.println(path + " was not found.");
        }
        while (courseScan.hasNext()) {
            courses[index] = new rbloomf_lab06_Course(courseScan.nextLine()); //add each line to the array courses
            index++;
        }
        courseScan.close();
    }

    /**
     * Method takes a file path and loads students to initialize student variables.
     * Method finds number of students, assigns student list size and creates array.
     * @param path 
     */
    private void loadStudentNames(String path) {
        File student = new File(path);
        Scanner studentScan = null;
        int index = 0;
        //Read file to count lines for number of students
        int count = 0;
        try {
            studentScan = new Scanner(student);
            while (studentScan.hasNext()){
                count++;
                studentScan.nextLine();
            }
            studentScan.close();
        } catch (FileNotFoundException ex) {
            System.err.println(path + " was not found.");
        }
        
        //Set size of courses array and create array
        System.err.println(count+" students total.");
        NUM_STUDENTS = count;
        //students = new rbloomf_lab06_Student[NUM_STUDENTS];
        
        //Populate array with list of Student names
        try {
            studentScan = new Scanner(student);
        } catch (FileNotFoundException ex) {
            System.out.println(path + " was not found.");
        }
        while (studentScan.hasNext()) {
            students.add(new rbloomf_lab06_Student(studentScan.next(), studentScan.nextInt()));
            index++;
        }
        studentScan.close();
    }

    /**
     * Method loads all course files from previous session. If no course files are found for any
     * courses added to the courses array, a new blank file is created to be written to.
     */
    private void loadCourseEnrollment() {
        File course;
        PrintWriter output;
        Scanner scan;
        for (int i = 0; i<NUM_COURSES; i++) { //for all courses in courses array field
            int j = 0;
            course = new File(courses[i].name.toUpperCase() + ".txt"); //create path with course name all caps and .txt filetype
            if (!course.exists()) { //if file name does not exist, create a blank one
                try {
                    output = new PrintWriter(course);
                    output.close();
                } catch (Exception ex) {
                }
            }
            else //file already exists, so load it
                try {
                    scan = new Scanner(course);
                    //checks if there is a next line in the file
                    while (scan.hasNext()) {
                        //isFull() makes sure aray will be resized if it becomes full
                        //this.courses[i].isFull(); //redundant if fixed array size, also, cannot be added to queue because never more than full in a save file when loaded
                        this.courses[i].students[j] = new rbloomf_lab06_Student(scan.next(), scan.nextInt()); //add each line from file to the students array field
                        
                        //add the course to the students report card and increment their number of courses in the report card
                        for (int n=0; n<students.size(); n++) {
                            if (courses[i].students[j].id == students.get(n).id) {
                                students.get(n).reportCard.coursesInReport.add(courses[i]); //add course
                                students.get(n).reportCard.numCoursesInReport++; //increment
                            }
                        }
                        
                        
                        j++; //move to next array index
                }
            } catch (Exception ex) { //if file is not in ideal format, print this error. Previous session results will not be included if this error is obtained
                System.err.println("Students not read from file. Class list is inaccurate. Student list must be in form: NAME ####");
            }    
        }
    }
}
