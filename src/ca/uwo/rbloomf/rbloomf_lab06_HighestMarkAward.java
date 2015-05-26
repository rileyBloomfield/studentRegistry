/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.uwo.rbloomf;

import java.util.LinkedList;

/**
 *
 * @author Riley
 */
public class rbloomf_lab06_HighestMarkAward extends rbloomf_lab06_Scholarship {

    
    double getWinner(LinkedList<rbloomf_lab06_Student> students, rbloomf_lab06_Course course) { //finds a winner for highest mark
        
        double highestMark = -1;
        
        //run through all students
        for (int i =0; i<students.size(); i++) {
            if (students.get(i).reportCard.coursesInReport.contains(course)) {
                for (int j=0; j<students.get(i).reportCard.numMarksCourse; j++) {
                    //go through all of the students marks for that course, if a new high is found, set winner and new high
                    if(students.get(i).reportCard.marks[students.get(i).reportCard.coursesInReport.indexOf(course)][j]>highestMark) {
                        this.setWinner(students.get(i).name); //set winner
                        highestMark = students.get(i).reportCard.marks[students.get(i).reportCard.coursesInReport.indexOf(course)][j];
                    }
                }
            }
        }
        //return highest mark in a course
        return highestMark;
    }
    
    
}
