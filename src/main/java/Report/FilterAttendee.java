package Report;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

import Camp.Camp;
import Student.Student;


/**
 * Implementation of the ReportFilter interface for filtering camps by attendee's name.
 */
public class FilterAttendee implements ReportFilter,Serializable{
	
	
	/**
     * Filters the provided list of camps by attendee's name.
     *
     * @param campList The list of camps to be filtered.
     * @return An ArrayList of camps filtered by the desired attendee's name.
     */
	
    public ArrayList<Camp> applyFilter(ArrayList<Camp> campList){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter attendee's name:");
        String desiredAttendeeName = sc.nextLine();

        ArrayList<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : campList){
            ArrayList<Student> students = camp.getRegisteredStudents();
            for (Student student : students) {
                if (student.getName().equals(desiredAttendeeName) && 
                    (student.getCommitteeForCamp() == null || !student.getCommitteeForCamp().equals(camp))) {
                    filteredCamps.add(camp);
                }
            }
        }
        return filteredCamps;
    }
}
