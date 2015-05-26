/**
 * SE2205 Lab04 Riley Bloomfield 250670211 Winter 2014
 */
package ca.uwo.rbloomf;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;

public class rbloomf_lab06_GUIManager extends JFrame {

    //These fields are holders for 1st and 2ndhorizontal panels to be redefined as options are selected from main menu and main window is refreshed
    private static final JPanel mainMenu = new JPanel(new GridBagLayout()); //set grid layout for main menu buttons
    private static boolean generatedGradesAlready = false;

    //Create Department field to hold all data
    static rbloomf_lab06_Department engineering = new rbloomf_lab06_Department();

    //Create letterGrade field to create a new GUI when selected
    rbloomf_lab06_LetterGrade letterGrade;
    
    //Create shuffle students field to create a new GUI when selected
    rbloomf_lab06_Shuffle shuffleStudents;

    //Create text area for displaying system information and printed data
    static JTextArea textField;

    //Default Constructor creates menu screen and leaves 2/3 of window blank for future implementations of member JPanels
    rbloomf_lab06_GUIManager() {

        //Set operations for created JFrame
        setTitle("Lab06 - Riley Bloomfield"); //set top titlebar
        setSize(600, 400); //set frame size
        setResizable(false); //not allow user to resize window
        setLocationRelativeTo(null); //start window in the center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE); //program will exit if close is clicked on window

        //Create string array of main menu options
        String[] menu = new String[12];
        menu[0] = "Print List of All Students";
        menu[1] = "Print List of All Courses";
        menu[2] = "Add a Student to a Course";
        menu[3] = "Drop a Student from a Course";
        menu[4] = "Print a List of Students in a Course";
        menu[5] = "Award Scholarship";
        menu[6] = "Generate Report Cards";
        menu[7] = "Show Waiting List";
        menu[8] = "Exit";
        menu[9] = "Validate File";
        menu[10] = "Shuffle Students";
        menu[11] = "Letter Grade";

        //Set layout for two JPanels on JFrame (center them)
        setLayout(new GridLayout(1, 2));

        //Initiate Menu of buttons (left half of screen) for main menu panel
        GridBagConstraints menuConstraint = new GridBagConstraints();
        menuConstraint.fill = GridBagConstraints.HORIZONTAL; //set buttons to be wide ad short
        JButton button;
        for (int i = 0; i < menu.length; i++) {
            button = new JButton(menu[i]); //add text to button
            menuConstraint.gridy = i; //apply above formatting to button
            button.addMouseListener(new Action(i, menu)); //add a listener for mouse click on every button
            mainMenu.add(button, menuConstraint); //add button to main menu panel of window and format
        }

        //Set display box that will appear on right side of JFrame
        textField = new JTextArea();
        //change font type and size
        Font font = new Font("Verdana", Font.PLAIN, 14);
        textField.setFont(font);
        textField.setDisabledTextColor(Color.WHITE); //change text color
        textField.setEnabled(false); //make read only text displayed
        textField.setBackground(Color.BLACK);

        //encapsulate text box in scrollable box
        JScrollPane insideText = new JScrollPane(textField);

        //set background colour of panels
        insideText.setBackground(Color.BLACK);
        mainMenu.setBackground(Color.BLACK);

        //Add all panels to the JFrame
        add(mainMenu);
        add(insideText);
    }

    /**
     * Main method. Method creates a frame from GUIManager and sets it visible
     *
     * @param args
     */
    public static void main(String[] args) {

        rbloomf_lab06_GUIManager frame = new rbloomf_lab06_GUIManager(); //create frame
        frame.setVisible(true); //make frame visible to user
    }

    /**
     * printListNames prints all students registered to passed department
     * parameter
     */
    private static void printListNames(rbloomf_lab06_Department engineering) {
        textField.append("     List of All Students:\n");
        for (int i = 0; i < engineering.students.size(); i++) {
            textField.append(engineering.students.get(i).toString() + "\n");
        }
        textField.append("\n");
    }

    /**
     * printListCourses prints all courses in a passed department parameter
     */
    private static void printListCourses(rbloomf_lab06_Department engineering) {
        textField.append("     List of All Courses:\n");
        for (int i = 0; i < engineering.courses.length; i++) {
            textField.append(engineering.courses[i].toString() + "\n");
        }
        textField.append("\n");
    }

    /**
     * printListEnrolled searches department for passed string course name and
     * if found prints all students enrolled in the course.(converts course
     * string to uppercase before check) If course is empty prints class is
     * empty.
     */
    private static void printListEnrolled(rbloomf_lab06_Department engineering) throws Exception {
        boolean valid = false;
        while (!valid) {
            String course = null;
            try {
                course = JOptionPane.showInputDialog("Enter a course name: ");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(textField, "Invalid input.");
            }
            for (int i = 0; i < engineering.courses.length; i++) { //search all courses in department
                if (engineering.courses[i].name.equals(course.toUpperCase())) { //if course matches course.name
                    valid = true;
                    if (engineering.courses[i].isEmpty()) {
                        textField.append("Class is empty!\n");
                        return;
                    } else {
                        textField.append("Enrolled Students --------\n");
                        for (int j = 0; j < engineering.courses[i].size; j++) {
                            if (engineering.courses[i].students[j] != null) {
                                textField.append(engineering.courses[i].students[j].toString() + "\n");
                            }
                        }
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(textField, "Invalid input.");
        }
        throw new rbloomf_lab06_CourseNotFoundException();
    }

    /**
     * addSudentCourse searches passed department course list for passed course
     * name. If course name is matched, user is prompted to input a student id
     * to add to a course. (int number is validated) Checks to see if student id
     * is already in the course (will not allow duplicate students) If student
     * is not currently registered, calls addStudent function. Function asks
     * user to repeat an incorrect id or name entry.
     */
    private static void addStudentCourse(rbloomf_lab06_Department engineering, String course) throws Exception {
        boolean courseFound = false;
        for (int i = 0; i < engineering.courses.length; i++) {

            if (engineering.courses[i].name.equals(course.toUpperCase())) {
                courseFound = true;
                int id = 0;
                String input = JOptionPane.showInputDialog("Enter Student ID: ");
                if (input == null) {
                    return;
                }
                try {
                    id = Integer.parseInt(input);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mainMenu, "Invalid input.");
                }

                //Check for id duplications
                for (int j = 0; j < engineering.students.size(); j++) {
                    if (id == engineering.students.get(j).id) {
                        if (isDuplicate(engineering, i, id)) {
                            JOptionPane.showMessageDialog(mainMenu, "Student is already registered. Student not added.");
                            return;
                        }
                        addStudent(engineering, i, engineering.students.get(j).id);
                        return;
                    }
                }
                boolean tryAgain = true;
                while (tryAgain) {
                    int in = JOptionPane.showConfirmDialog(textField, "ID not found. Would you like to try another?");
                    if (in == 0) {
                        addStudentCourse(engineering, course);
                    }
                    tryAgain = false;
                    if (in == 1 || in == -1) {
                        tryAgain = false;
                        throw new rbloomf_lab06_StudentNotFoundException();
                    }
                }
            }
        }
        if (!courseFound) {
            boolean tryAgain = true;
            while (tryAgain) {
                int in = JOptionPane.showConfirmDialog(textField, "Course not found. Would you like to try another?");
                if (in == 0) {
                    callCourseNameAdd(engineering);
                }
                tryAgain = false;
                if (in == -1 || in == 1) {
                    tryAgain = false;
                    throw new rbloomf_lab06_CourseNotFoundException();
                }
            }
        }
    }

    /**
     * addStudent checks to see if course is full, if course.isFull() returns
     * true, continues. function finds first null location in the department
     * array students to add. Function then searches the department array
     * students to check if an id is a valid student. If a valid student is
     * determined, this student is added to the null location found.
     */
    private static void addStudent(rbloomf_lab06_Department engineering, int courseIndex, int id) throws Exception {
        if (engineering.courses[courseIndex].isFull()) { //if course is full, add student to waiting list for that course

            for (int j = 0; j < engineering.students.size(); j++) {
                //check if student ID exists, if it does not, StudentNotFoundException is thrown
                if (id == engineering.students.get(j).id) {
                    if (engineering.courses[courseIndex].waitingList.contains(engineering.students.get(j))) {
                        textField.append("Student is already on the waiting list.");
                        return;
                    }
                    textField.append("Course is full. Student has been added \nto the waiting list.");
                    engineering.courses[courseIndex].waitingList.add(engineering.students.get(j));
                }

            }
        }

        if (!engineering.courses[courseIndex].isFull()) {
            //look for available free spot to add a student (first null location found)
            for (int i = 0; i < engineering.courses[courseIndex].students.length; i++) {
                if (engineering.courses[courseIndex].students[i] == null) {

                    for (int j = 0; j < engineering.students.size(); j++) {
                        //check if student ID exists, if it does not, StudentNotFoundException is thrown
                        if (id == engineering.students.get(j).id) {

                            //add student to volatile array
                            engineering.courses[courseIndex].students[i] = new rbloomf_lab06_Student(engineering.students.get(j).name, engineering.students.get(j).id);
                            generatedGradesAlready = false; //make sure student gets reportcard before sorting or shuffling
                            
                            //add course to students report card and change number of courses on report
                            System.out.println("added: " + engineering.students.get(j).reportCard.coursesInReport.add(engineering.courses[courseIndex]));
                            engineering.students.get(j).reportCard.numCoursesInReport++;
                            System.out.println("number of courses: " + engineering.students.get(j).reportCard.numCoursesInReport);

                            //save student to non-volatile files to be read next startup
                            File file = new File(engineering.courses[courseIndex].name.toUpperCase() + ".txt");
                            if (file.exists()) {
                                PrintWriter output = new PrintWriter(new FileWriter(file, true));
                                output.println(engineering.courses[courseIndex].students[i]);
                                output.close();
                            }
                            //Display added message in GUI
                            textField.append(engineering.students.get(j).name + " was added to " + engineering.courses[courseIndex].name + "\n");
                            return;
                        }
                    }
                    throw new rbloomf_lab06_StudentNotFoundException();
                }
            }
        }
    }

    /**
     * function removes a valid student from a passed course of a passed
     * department. Function checks if course name is valid. Continues if valid
     * course is found. If course is empty, returns course is empty. User inputs
     * student id, if valid function checks if the id was found in the desired
     * course. If id is found dropStudent is called, if not returns no student
     * found. Function asks user to repeat an incorrect id or name entry.
     */
    private static void dropStudentCourse(rbloomf_lab06_Department engineering, String course) throws Exception {
        boolean courseFound = false;
        for (int i = 0; i < engineering.courses.length; i++) {

            if (engineering.courses[i].name.equals(course.toUpperCase())) {
                courseFound = true;
                if (engineering.courses[i].isEmpty()) {
                    textField.append("Course is empty.\n");
                    return;
                }
                int id = 0;
                try {
                    //System.out.println("Enter Student ID: ");
                    //Scanner scan = new Scanner(System.in);
                    String input = JOptionPane.showInputDialog("Enter Student ID:");
                    id = Integer.parseInt(input);
                } catch (Exception e) {
                    //System.out.println("Invalid input.\n");
                    JOptionPane.showMessageDialog(textField, "Invalid input.");
                    return;
                }
                for (int j = 0; j < engineering.students.size(); j++) {

                    if (id == engineering.students.get(j).id) {
                        dropStudent(engineering, i, engineering.students.get(j).id);
                        return;
                    }
                }
                boolean tryAgain = true;
                while (tryAgain) {
                    int in = JOptionPane.showConfirmDialog(textField, "ID not found. Would you like to try another?");
                    if (in == 0) {
                        dropStudentCourse(engineering, course);
                    }
                    tryAgain = false;
                    if (in == 1 || in == -1) {
                        tryAgain = false;
                        throw new rbloomf_lab06_StudentNotFoundException();
                    }
                }
            }
        }
        if (!courseFound) {
            boolean tryAgain = true;
            while (tryAgain) {
                int in = JOptionPane.showConfirmDialog(textField, "Course not found. Would you like to try another?");
                //char select = another.nextLine().charAt(0);
                if (in == 0) {
                    callCourseNameDrop(engineering);
                }
                tryAgain = false;
                if (in == -1 || in == 1) {
                    tryAgain = false;
                    throw new rbloomf_lab06_CourseNotFoundException();
                }
            }
        }
    }

    /**
     * dropstudent takes an id, course index and department and checks if
     * student id is present in the desired course of desired department. If the
     * id is valid, the location matching the id is assigned null, meaning it
     * can be ovverrriden and will not be printed
     */
    private static void dropStudent(rbloomf_lab06_Department engineering, int courseIndex, int id) throws Exception {
        boolean validId = false;

        for (int i = 0; i < engineering.students.size(); i++) {
            if (id == engineering.students.get(i).id) {
                validId = true;
            }
        }

        if (!validId) {
            throw new rbloomf_lab06_StudentNotFoundException();
        }

        for (int i = 0; i < engineering.courses[courseIndex].students.length; i++) {
            if (engineering.courses[courseIndex].students[i] != null) {
                if (id == engineering.courses[courseIndex].students[i].id) {
                    textField.append(engineering.courses[courseIndex].students[i].name + " was removed.\n");

                    //Search all students in department and find student matching this id. Remove the course form their report card.
                    for (int n = 0; n < engineering.students.size(); n++) {
                        if (id == engineering.students.get(n).id) {
                            System.out.println("contains: " + engineering.students.get(n).reportCard.coursesInReport.contains(engineering.courses[courseIndex]));
                            System.out.println("removed: " + engineering.students.get(n).reportCard.coursesInReport.remove(engineering.courses[courseIndex]));
                            engineering.students.get(n).reportCard.numCoursesInReport--;
                        }
                    }

                    //Remove student from volatile array
                    engineering.courses[courseIndex].students[i] = null;

                    //When student is removed, check waiting to see if there is a student to add
                    if (engineering.courses[courseIndex].waitingList.size() != 0) {
                        textField.append("The next student in the \nwaiting list has been added.");
                        generatedGradesAlready = false; //make sure grades are generated before entering shuffle or sort
                        
                        //add the course to the student's report card and increment their number of courses
                        for (int n = 0; n < engineering.students.size(); n++) {
                            if (engineering.courses[courseIndex].waitingList.peek().id == engineering.students.get(n).id) {
                                engineering.students.get(n).reportCard.coursesInReport.add(engineering.courses[courseIndex]);
                                engineering.students.get(n).reportCard.numCoursesInReport++;
                            }
                        }

                        //remove student from the waiting list and add to course list
                        engineering.courses[courseIndex].students[i] = engineering.courses[courseIndex].waitingList.remove();
                    }

                    //Rewrite file for course using volatile array without recently deleted entity
                    File file = new File(engineering.courses[courseIndex].name.toUpperCase() + ".txt");
                    PrintWriter output = null;

                    //delete old file
                    file.delete();

                    output = new PrintWriter(file);

                    for (int k = 0; k < engineering.courses[courseIndex].students.length; k++) {
                        if (engineering.courses[courseIndex].students[k] != null) {
                            output.println(engineering.courses[courseIndex].students[k]);
                        }
                    }
                    output.close();
                    return;
                }
            }
        }
        textField.append("Student was not registered in this course.\n");
    }

    /**
     * function runs through all students of a passed department, passed course
     * index to find a passed id. If current id is found, it is identified as a
     * duplicate and returns true.
     */
    private static boolean isDuplicate(rbloomf_lab06_Department engineering, int courseIndex, int id) {
        for (int i = 0; i < engineering.courses[courseIndex].students.length; i++) {
            if (engineering.courses[courseIndex].students[i] != null) {
                if (id == engineering.courses[courseIndex].students[i].id) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method asks user to input a course name and relays to dropStudentCourse()
     *
     * @param engineering
     */
    private static void callCourseNameDrop(rbloomf_lab06_Department engineering) {
        String course = JOptionPane.showInputDialog("Enter Course Name:");
        if (course == null) {
            return;
        }
        try {
            dropStudentCourse(engineering, course);
        } catch (Exception ex) {
        }
    }

    /**
     * Method asks user to input a course name and relays to addStudentCourse()
     *
     * @param engineering
     */
    private static void callCourseNameAdd(rbloomf_lab06_Department engineering) {
        String course = JOptionPane.showInputDialog("Enter Course Name:");
        if (course == null) {
            return;
        }
        try {
            addStudentCourse(engineering, course);
        } catch (Exception ex) {
        }
    }

    /**
     * Method is responsible for assigning the desired student marks and then
     * displaying the marks for all of the courses the student is enrolled in.
     */
    private static void generateReportCard() {
        //seach all students to see if id was valid
        for (int i = 0; i < engineering.students.size(); i++) {
            //check if student is enrolled in any courses, if they are, will display banner for them, otherwise skip
            if (!engineering.students.get(i).reportCard.coursesInReport.isEmpty()) {
                textField.append("\n" + engineering.students.get(i).name + "'s report card:\n");
            }

            //generate marks for that student
            engineering.students.get(i).reportCard.generateRandomMarks();

            //allow scholarship to be generated now that grades have been generated
            generatedGradesAlready = true;

            //print out all of the marks for courses student is registered in
            for (int j = 0; j < engineering.students.get(i).reportCard.numCoursesInReport; j++) { //go through all courses
                textField.append("[" + engineering.students.get(i).reportCard.coursesInReport.get(j).name + "] ["); //add the course name to the textField
                for (int k = 0; k < engineering.students.get(i).reportCard.numMarksCourse; k++) {
                    textField.append(Integer.toString(engineering.students.get(i).reportCard.marks[j][k]) + " ");
                }
                textField.append("]\n");
            }
        }
    }

    private void validateFile() {
        TreeMap<String, Integer> words = new TreeMap<>();
        String line = null;
        String[] parsedLine = null;
        JFileChooser fileChooser = new JFileChooser("/Users/Riley/NetBeansProjects/SE2205_lab06");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Scanner input = null;
            // Get the selected file
            java.io.File file = fileChooser.getSelectedFile();
            // Create a Scanner for the file
            try {
                input = new Scanner(file);
            } catch (Exception ex) {

            }
            
            //list of exception combinations that are not considered words, exclude form word count
            LinkedList<String> exceptions = new LinkedList<>();
            exceptions.add("*");
            exceptions.add("+");
            exceptions.add("=");
            exceptions.add("+=");
            exceptions.add("<=");
            exceptions.add(">=");
            exceptions.add("/");
            exceptions.add("*/");
            exceptions.add("/**");
            exceptions.add("&&");
            exceptions.add("||");
            
            
            // Read text from the file
            while (input.hasNext()) {
                //textField.append(input.nextLine()+"\n");
                line = input.nextLine();
                parsedLine = line.split("[\\[\\] \n\t\r.,;:!?(){}-]");
                for (int i = 0; i < parsedLine.length; i++) {
                    String key = parsedLine[i].toLowerCase();
                    if (exceptions.contains(key))
                        continue;
                    if (parsedLine[i].length() >= 1) {
                        if (words.get(key) == null) {
                            words.put(key, 1);
                        } else {
                            int value = words.get(key);
                            value++;
                            words.put(key, value);
                        }
                    }
                }
            }
            textField.append("File has been read successfully.\n\nDocument Contains:\n");
            Set<String> set = words.keySet();
            Iterator<String> iterator = set.iterator();
            boolean valid = true;
            while(iterator.hasNext()) {
                String next = iterator.next();
                if (words.get(next)>1 && (!next.equals("*"))&&(!next.equals("+="))&&(!next.equals("+"))&&(!next.equals("-"))) {
                    valid = false;
                    textField.append(next+"     "+words.get(next)+"    <- Duplicate Exists\n");
                }
                else
                    textField.append(next+"     "+words.get(next)+"\n");
            }
            if (!valid) {
                textField.append("\nFile is not valid, duplicates exist.");
            }
        }
    }

    /**
     * Method used to control inputs as menu options are selected. Index 0-9 are
     * for left panel buttons. Index 10-19 are for middle panel buttons. Any
     * above indexes are used for right panel operations.
     */
    class Action implements MouseListener {

        //
        int index;
        String[] menu;

        Action(int index, String[] menu) {
            this.index = index;
            this.menu = menu;
        }

        public void mouseClicked(MouseEvent e) {

            //remove all printed elements from the list to save memory
            textField.removeAll();
            //clear text area from previous prints
            textField.setText(null);
            if (index == 0) {
                printListNames(engineering); //prints names of students
            }
            if (index == 1) {
                printListCourses(engineering); //prints names of courses
            }
            if (index == 2) {
                callCourseNameAdd(engineering); //add a student to a course
            }
            if (index == 3) {
                callCourseNameDrop(engineering); //drop a student from a course
            }
            if (index == 4) {

                try {
                    printListEnrolled(engineering); //print a list of the students enrolled in a desired course
                } catch (Exception ex) {
                }
            }
            if (index == 5) {
              //AWARD SCHOLARSHIP

                //if grades have not been generated, instruct user to generate grades and return.
                if (!generatedGradesAlready) {
                    JOptionPane.showMessageDialog(mainMenu, "Grades have not been generated. Select Generate Report Card \nbefore selecting Awards.");
                    return;
                }

                //allow user to input a course name to specify which awards they would like
                String input = JOptionPane.showInputDialog("Enter a course name: ");
                if (input == null) {
                    return;
                }
                input = input.toUpperCase();

                //search all courses in the department for the entered course
                for (int i = 0; i < engineering.courses.length; i++) {
                    if (input.equals(engineering.courses[i].name)) {

                        //if course is empty
                        if (engineering.courses[i].students.length == 0) {
                            textField.append("Course is empty.");
                            return;
                        }
                        //create a new highestMark object
                        rbloomf_lab06_HighestMarkAward highestMark = new rbloomf_lab06_HighestMarkAward();

                        //call the getWinner function and assign the highest mark to a holder
                        double mark1 = highestMark.getWinner(engineering.students, engineering.courses[i]);
                        if (mark1 < 0) {
                            textField.append("There are no students in this course.");
                            return;
                        }
                        //tell user who won the highestmark award
                        textField.append("The winner of the highest mark award\nis " + highestMark.getWinner() + " with a mark of " + mark1 + "\n\n");

                        //create new bestStudet award
                        rbloomf_lab06_BestStudentAward bestStudent = new rbloomf_lab06_BestStudentAward();

                        //call the getWinner function and assign the best student, assign the winning average to a holder.
                        double mark2 = bestStudent.getWinner(engineering.students, engineering.courses[i]);
                        if (mark2 < 0) {
                            textField.append("There are no students in this course.");
                            return;
                        }

                        //tell user who won the highestmark award
                        textField.append("The winner of the best student award\nis " + bestStudent.getWinner() + " with an average of " + mark2);

                        //finished
                        return;
                    }
                }
                try {
                    throw new rbloomf_lab06_CourseNotFoundException();
                } catch (rbloomf_lab06_CourseNotFoundException ex) {
                }
            }

            if (index == 6) {
                generateReportCard(); //gives all students in courses grades and prints reports
            }

            if (index == 7) {//show a waiting list for a desired course
                //this case searches for the desired course and prints the queue for waiting list if it is found and not empty
                String input = JOptionPane.showInputDialog("Enter a course name: ");
                input = input.toUpperCase();
                for (int i = 0; i < engineering.courses.length; i++) {
                    if (input.equals(engineering.courses[i].name)) {
                        if (engineering.courses[i].waitingList.size() == 0) {
                            textField.append("The waiting list is empty");
                            return;
                        }
                        textField.append(engineering.courses[i].waitingList.toString());
                    }
                }
            }

            if (index == 8) {
                System.exit(0);
            }

            if (index == 9) {
                validateFile();
            }
            if (index==10) {
                if(generatedGradesAlready) {
                shuffleStudents(engineering.students);
                }
                else
                    textField.append("Generate report card first");
            }
            if (index == 11) {
                LetterGrade();
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        private void LetterGrade() {
            textField.append("Opened in new GUI");
            letterGrade = new rbloomf_lab06_LetterGrade();
            letterGrade.setVisible(true);
        }

        private void shuffleStudents(LinkedList<rbloomf_lab06_Student> students) {
            textField.append("Opened in a new GUI");
            shuffleStudents = new rbloomf_lab06_Shuffle(engineering.students);
            shuffleStudents.setVisible(true);
        }
    }
}
