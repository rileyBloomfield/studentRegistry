/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.uwo.rbloomf;

/**
 *class extends exception and is thrown when a student is not found.
 * @author Riley
 */
public class rbloomf_lab06_StudentNotFoundException extends Exception {

    //default constructor adds output message to text display in frame
    rbloomf_lab06_StudentNotFoundException() {
        rbloomf_lab06_GUIManager.textField.append("No student added, ID Not Found.\n"); //add text to public member textFied
    }

  
}
