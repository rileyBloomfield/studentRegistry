package ca.uwo.rbloomf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.*;

public class rbloomf_lab06_Shuffle extends JFrame {

    //the thext input and display areas
    private final JTextField courseName = new JTextField("all"); //set default text in text box to "all"
    private final JTextArea textArea = new JTextArea();
    
    //control buttons for functionality
    private final JButton sortButton;
    private final JButton shuffleButton;
    private final JButton reverseButton;
    
    //lists that will be filled according to specified course enrollment
    LinkedList<rbloomf_lab06_Student> courseStudents = new LinkedList<>();
    LinkedList<rbloomf_lab06_Student> students = new LinkedList<>();
    final LinkedList<rbloomf_lab06_Student> filteredStudents = new LinkedList<>();
    
    //iterator that will be used when printing the lists
    ListIterator<rbloomf_lab06_Student> iterator;
    
    //will hold the string taken from the input box
    String textBox;

    public rbloomf_lab06_Shuffle(LinkedList<rbloomf_lab06_Student> student) {

        //set the box wide enough to input course names... and then some
        courseName.setColumns(15);
        
        //take the parameter and assign it to a final list
        students = student;

        //create button for sort and add functionality with action listener
        sortButton = new JButton("Sort"); //add text to the button
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterList(students); //filter the list to be displayed based on input text
                Collections.sort(filteredStudents); //sort the list using student's compareTo()
                iterator = filteredStudents.listIterator(); //print all of the names to the textfield using iterator
                while (iterator.hasNext()) {
                    textArea.append(iterator.next() + "\n");
                }
                textArea.append("\n\n");
            }
        });

        //create button for shuffle and add functionality with action listener
        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterList(students);
                Collections.shuffle(filteredStudents);
                iterator = filteredStudents.listIterator();
                while (iterator.hasNext()) {
                    textArea.append(iterator.next() + "\n");
                }
                textArea.append("\n\n");
            }
        });

        //create button for reverse and add functionality with action listener
        reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Collections.reverse(filteredStudents);
                iterator = filteredStudents.listIterator();
                while (iterator.hasNext()) {
                    textArea.append(iterator.next() + "\n");
                }
                textArea.append("\n\n");
            }
        });

        //create list holder for students that are in the desired course. This will be instantiated if text in textbox is changed
        LinkedList<rbloomf_lab06_Student> studentsInCourse;

        //set window to overlap previous GUI
        setSize(600, 400);
        setLocationRelativeTo(null);

        //panel to hold buttons and textfields
        JPanel panel1 = new JPanel();

        //label that describes course text field
        panel1.add(new JLabel("Enter a course name:"));

        //add label to tell user to input a course
        panel1.add(courseName);

        //add scrollable text area
        JScrollPane jsp = new JScrollPane(textArea);

        JPanel panel2 = new JPanel();
        panel2.add(sortButton);
        panel2.add(shuffleButton);
        panel2.add(reverseButton);

        this.add(panel1, BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);

    }

    //filter list function checks the input text to see if it has changed from all and adds students to the filteredStudents list to be sorted and shuffled 
    //if they have the course input in their report cards
    private void filterList(LinkedList<rbloomf_lab06_Student> student) {
        filteredStudents.clear();
        if ((courseName.getText().equals("all"))) {
            for (int i = 0; i < student.size(); i++) {
                filteredStudents.add(student.get(i));
            }
        } 
            textBox = courseName.getText().toUpperCase();
        
        System.out.println(textBox+ " students search.");
        if (!(courseName.getText().equals("all"))) {
            for (int i = 0; i < student.size(); i++) {
                for (int j = 0; j < student.get(i).reportCard.coursesInReport.size(); j++) {
                    if (student.get(i).reportCard.coursesInReport.get(j).name.equals(textBox)) {
                        filteredStudents.add(student.get(i));
                    }
                }
            }
        }
        if (filteredStudents.isEmpty()) 
            textArea.append("There are no students in that course.\n\n");
        
    }
}
