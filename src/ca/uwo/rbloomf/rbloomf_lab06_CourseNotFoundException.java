/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.uwo.rbloomf;


/**
 *class extends Exception. This is thrown when a course name is not found in the GUIManager
 * @author Riley
 */
public class rbloomf_lab06_CourseNotFoundException extends Exception{

    //default constructor displays an error message in the public textField member of GUIManager
    rbloomf_lab06_CourseNotFoundException() {
        rbloomf_lab06_GUIManager.textField.append("Course Name Not Found.\n"); //if no string is matched to a course.name
    }
    
}
