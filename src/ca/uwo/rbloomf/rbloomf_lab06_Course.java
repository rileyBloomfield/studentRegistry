
package ca.uwo.rbloomf;

import java.util.LinkedList;
import java.util.Queue;


/*
Class is used to hold an array of students registered in each course
*/
public class rbloomf_lab06_Course {

    public String name; //course name
    public int size = 5; //default course limit is 5 students, can be adjusted by user
    public rbloomf_lab06_Student[] students = new rbloomf_lab06_Student[size]; //create original array to be resized as neccessary in isFull() method
    Queue<rbloomf_lab06_Student> waitingList = new LinkedList<>(); //queue to hold list of students that will be attmitted to a course once there is space

    rbloomf_lab06_Course(String name) {
        this.name = name; //set name from parameter
    }

    /*returns true if course holds max number of students students
    function resizes array if it is full to a new size twice as large.
    (input validation)*/
    public boolean isFull() {
        if (this.students[students.length-1] == null) { //if last entry becomes full change size
            return false;
           }
        return true; //always shows there is more room now that array changes size
    }

    /*
    function checks if all students are null (no registered students)
    */
    public boolean isEmpty() {
        for (int i = 0; i < students.length; i++) { //return first entry that is not null, if all null then it is empty
            if (students[i] != null) {
                return false;
            }
        }
        return true;
    }

    //returns formatted course name
    public String toString() {
        return "      " + name;
    }

    //returns how many non-null entries are in the students array
    public int getSize() {
        int count = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null) {
                count++;
            }
        }
        return count;
    }
}
