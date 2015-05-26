/*
 * Class is responsible for assigning a winner and calculating the best average in a course.
 */

package ca.uwo.rbloomf;

import java.util.LinkedList;

/**
 *extends scholarship
 * @author Riley
 */
public class rbloomf_lab06_BestStudentAward extends rbloomf_lab06_Scholarship {

   
    double getWinner(LinkedList<rbloomf_lab06_Student> students, rbloomf_lab06_Course course) { //assigns a winner to the award
        //holders for the numerical values used for returning and computing best average
        double sum =0;
        double average = 0;
        double highestAverage = -1;
        
        //run through all the students and get their average for each course.
        for (int i =0; i<students.size(); i++) {
            if (students.get(i).reportCard.coursesInReport.contains(course)) {
                for (int j=0; j<students.get(i).reportCard.numMarksCourse; j++) {
                    //add up all a students marks for a course
                    sum += students.get(i).reportCard.marks[students.get(i).reportCard.coursesInReport.indexOf(course)][j];     
                }
                average = sum/students.get(i).reportCard.numMarksCourse;
                if (average>highestAverage) {
                    highestAverage = average;
                    this.setWinner(students.get(i).name);
                }
            }
            //reset sum to give correct result for each student
            sum=0;
        }
        return highestAverage;
    }
    
}
