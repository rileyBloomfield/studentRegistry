package ca.uwo.rbloomf;

/*
 Class is used to store name and student id of students in the department
 */
public class rbloomf_lab06_Student implements Comparable {

    public String name; //holder for string name
    public int id; //holder for integer student id
    public rbloomf_lab06_ReportCard reportCard; //report card object to hold all of a students mark information
    public boolean allSort = true;

    //Constructor used to instantiate students already with name and id
    rbloomf_lab06_Student(String name, int id) {
        this.name = name; //set name form parameter
        this.id = id;  //set id from parameter
        this.reportCard = new rbloomf_lab06_ReportCard();
    }

    //default constructor explicitly defined
    rbloomf_lab06_Student() {
        this.reportCard = new rbloomf_lab06_ReportCard();
    }

    //Overloadded toString returns a formatted string showing name and id
    public String toString() {
        if (name.length() == 7) {
            return "     " + name + "   " + id;
        }
        if (name.length() == 6) {
            return "     " + name + "    " + id;
        }
        if (name.length() == 5) {
            return "     " + name + "     " + id;
        }
        if (name.length() == 4) {
            return "     " + name + "      " + id;
        } else {
            return "     " + name + "       " + id;
        }
    }

 //Compareto function is used to sort, shuffle and reverse a list of students in the Collections collection
    //students are sorrted based on averages, number of courses and highest mark in the event of ties
    public int compareTo(Object object) {
       
        //cast comparable object to a student
        rbloomf_lab06_Student compareStud = (rbloomf_lab06_Student)object;
        
        //mass of if statements used to compare the two students
        //first get if one student has no courses and one student does, the student with courses wins.
        if ((!reportCard.coursesInReport.isEmpty()) && (compareStud.reportCard.coursesInReport.isEmpty()))
            return -1;
        else if ((reportCard.coursesInReport.isEmpty()) && (!compareStud.reportCard.coursesInReport.isEmpty()))
            return 1;
        
        //if both students have no courses, they recieve a tie
        else if ((reportCard.coursesInReport.isEmpty()) && (compareStud.reportCard.coursesInReport.isEmpty()))
            return 0;
        
        //if both students have courses, marks need to be compared
        else if ((!reportCard.coursesInReport.isEmpty()) && (!compareStud.reportCard.coursesInReport.isEmpty())) {
            //first averages are compared
            if (getAverage(this)<getAverage(compareStud))
                return 1;
            else if (getAverage(this)>getAverage(compareStud))
                return -1;
            
            //if both students have the same average then further differentiation is needed
            else if (getAverage(this)==getAverage(compareStud)) {
                
                //number of courses enrolled is compared to find the victor
                if (reportCard.coursesInReport.size()<compareStud.reportCard.coursesInReport.size())
                    return 1;
                else if (reportCard.coursesInReport.size()>compareStud.reportCard.coursesInReport.size())
                    return -1;
                
                //if both students have the same number of courses and average and they both are enrolled in courses, highest marks are compared
                else if (reportCard.coursesInReport.size()==compareStud.reportCard.coursesInReport.size()) {
                    if (getHighestMark(this)<getHighestMark(compareStud))
                        return 1;
                    else if (getHighestMark(this)>getHighestMark(compareStud))
                        return -1;
                    
                    //if they also have the same highest mark then they both get gold... or no one does.
                    else 
                        return 0;
                }
            }
                  
        }
        return 1; //default return statement to stop netbeans from complaining... I don't think this can be reached... at least I hope not
    }   
    
    //get average function is used to return the average of a student. This is calculated based on all marks in a course for all courses enrolled in 
    private double getAverage(rbloomf_lab06_Student stud) {
        double average = 0;
        for (int i=0; i<stud.reportCard.coursesInReport.size(); i++) {
            for (int j=0; j<stud.reportCard.numMarksCourse; j++) {
                average += stud.reportCard.marks[i][j];
            }
        }
        average = average/(stud.reportCard.coursesInReport.size()*stud.reportCard.numMarksCourse);
        
        return average;
    }
    
    //get highest mark function goes through all of a students marks for all of their courses and returns the highest
    private double getHighestMark(rbloomf_lab06_Student stud) {
        double highestMark = 0;
        
        for (int i=0; i<stud.reportCard.coursesInReport.size(); i++) {
            for (int j=0; j<stud.reportCard.numMarksCourse; j++) {
                if (stud.reportCard.marks[i][j]>highestMark)
                    highestMark = stud.reportCard.marks[i][j];
            }
        }
        
        return highestMark;
    }
}
