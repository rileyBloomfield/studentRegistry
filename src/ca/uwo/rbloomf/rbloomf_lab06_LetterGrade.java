/*
 * Riley Bloomfield lab06 Part 3
 */
package ca.uwo.rbloomf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class rbloomf_lab06_LetterGrade extends JFrame {

    //create all the labels to identify the boxes
    private JLabel ES1036 = new JLabel("          ES1036:");
    private JLabel CS1037 = new JLabel("          CS1037:");
    private JLabel SE2205 = new JLabel("          SE2205:");
    private JLabel SE2250 = new JLabel("          SE2250:");
    private JLabel space1 = new JLabel("          Total:");
    private JLabel space2 = new JLabel("          Average:");
    private JLabel space3 = new JLabel("          Letter Grade:");

    //create the textfields and give them a default value of 0
    private JTextField markProg = new JTextField("0");
    private JTextField markComp = new JTextField("0");
    private JTextField markJava = new JTextField("0");
    private JTextField markCons = new JTextField("0");
    private JTextField totalMarks = new JTextField();
    private JTextField averageMarks = new JTextField();
    private JTextField letterGrade = new JTextField();
    private JButton computeGrade = new JButton("Compute Grade");

    public rbloomf_lab06_LetterGrade() throws HeadlessException {

        setTitle("Lab06 - Riley Bloomfield - Letter Grade"); //set top titlebar
        setSize(600, 400); //set frame size
        setResizable(false); //not allow user to resize window
        setLocationRelativeTo(null); //start window in the center of screen
        //setDefaultCloseOperation(EXIT_ON_CLOSE); //program will exit if close is clicked on window
        //pack();

        //add all the buttons to the panel
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(ES1036);
        panel.add(markProg);
        panel.add(CS1037);
        panel.add(markComp);
        panel.add(SE2205);
        panel.add(markJava);
        panel.add(SE2250);
        panel.add(markCons);
        panel.add(space1);
        panel.add(totalMarks);
        panel.add(space2);
        panel.add(averageMarks);
        panel.add(space3);
        panel.add(letterGrade);

        //set instructional header
        panel.setBorder(new TitledBorder("Enter marks for four subjects"));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //set layout
        panel3.add(computeGrade); //add button to compute
        add(panel, BorderLayout.CENTER); //layout
        add(panel3, BorderLayout.SOUTH); //layout
        computeGrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //get values from text fields
                //assign each mark a default incase the user erases all values from fields
                double mark1 = 0;
                double mark2 = 0;
                double mark3 = 0;
                double mark4 = 0;
                //attempt to access data from fields, will fail and result in a 0 if there is no text in the box(or incorrect inputs)
                try {
                    mark1 = Math.abs(Double.parseDouble(markProg.getText()));
                    mark2 = Math.abs(Double.parseDouble(markComp.getText()));
                    mark3 = Math.abs(Double.parseDouble(markJava.getText()));
                    mark4 = Math.abs(Double.parseDouble(markCons.getText()));
                } catch (Exception ex) {
                    System.out.println("Invalid Inputs for one or more of the fields.");
                }

                //add all marks
                double total = mark1 + mark2 + mark3 + mark4;
                
                //compute average
                double average = total / 4.0;
                char grade = 'F';

                //decide what letter grade will be awarded based on average
                if (average > 85) {
                    grade = 'A';
                } else if (average > 70) {
                    grade = 'B';
                } else if (average > 60) {
                    grade = 'C';
                } else if (average > 50) {
                    grade = 'D';
                }

                //format output boxes and set them all to disabled for editing
                totalMarks.setText(String.format("%.1f", total));
                totalMarks.setEnabled(false);
                averageMarks.setText(String.format("%.1f", average));
                averageMarks.setEnabled(false);
                letterGrade.setText(String.format("%c", grade));
                letterGrade.setEnabled(false);

            }
        });
    }

}
