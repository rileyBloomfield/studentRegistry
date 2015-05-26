/**
 * Class to be extended by two independent award classes bestStudentAward and highestMarkAward
 */


package ca.uwo.rbloomf;

import java.util.LinkedList;

/**
 *
 * @author Riley
 */
public abstract class rbloomf_lab06_Scholarship {
    
    private String name; //can hold the name of a scholarship
    private double value; //can hold a monetary value of the scholarship
    private String winner; //will hold the name of the winner of the scholarship
    
    //default constructor will assign non-important values 
    rbloomf_lab06_Scholarship() {
        this.name = "DefaultName";
        this.value = 0;
        this.winner = "NoWinner";
    }
    //constructor with parameters will take in a name and a value when scholarship is created 
     rbloomf_lab06_Scholarship(String name, double value) {
        this.name = name;
        this.value = value;
        this.winner = "NoWinner";
    }
    
     //set function for the private name field
    void setName(String name) {
        this.name = name;
    }
    
    //set function for the private value field
    void setValue(double value) {
        this.value = value;
    }
    
    //set function for the private winner field
    void setWinner(String winner) {
        this.winner = winner;
    }
    
    //get function for the private name field
    String getName() {
        return this.name;
    }
    
    //get function for the private value field
    double getValue() {
        return this.value;
    }
    
    //get function for the private winner field
    String getWinner() {
        return this.winner;
    }
    
    //get winner function to be redefined based on future scholarship criteria
    abstract double getWinner(LinkedList<rbloomf_lab06_Student> students, rbloomf_lab06_Course course);
    
    //overloaded toString function used to print all of the important scholarship information on one line.
    public String toString() {
        return "Scholarship: "+name+"\nValue: "+value+"\nWinner:"+winner;
    }
}
