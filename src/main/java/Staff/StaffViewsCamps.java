package Staff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import CampFilter.ByDate;
import CampFilter.ByLocation;
import CampFilter.CampFilter;
import Student.Student;


/**
 * Represents staff viewing functionality for camps.
 */
public class StaffViewsCamps implements Serializable{
    /**
     * Filters the camp list based on user input.
     *
     * @param campList The list of camps to filter.
     * @return The filtered list of camps or null if not found.
     */
    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        CampFilter campFilter;
        int choice = 0, index = 0, maxTries = 3;
        Scanner sc = new Scanner(System.in);
        int tries;
        for (tries = 0; tries < maxTries; tries++) {
            try {
                System.out.println("Do you want to filter to view Camps?");
                System.out.println("1) Yes\n2) No");
                index = sc.nextInt();

                if (index == 2) {
                    choice = 3;
                    break; // Exit the loop if the input is valid
                } else if (index == 1) {
                    break; // Exit the loop if the input is valid
                } else {
                    System.out.println("Invalid Choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Consume the invalid input
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        sc.nextLine();// consume next line
        if (index != 2) {
            for (tries = 0; tries < maxTries; tries++) {
                try {
                    System.out.println("Choose the filter types?");
                    System.out.println("1) Display the registration date of the camps prior to the entered date.\n2) By Location");
                    choice = sc.nextInt();

                    if (choice == 2 || choice == 1) {
                        break; // Exit the loop if the input is valid
                    } else {
                        System.out.println("Invalid Choice. Try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.nextLine(); // Consume the invalid input
                }
            }

            if (tries == maxTries) {
                System.out.println("You've reached the maximum number of tries. Please try again later.");
                return null;
            }
        }

        switch (choice) {
            case 1:
                campFilter = new ByDate();
                filteredCamps = campFilter.applyCampFilter(campList);
                break;
            case 2:
                campFilter = new ByLocation();
                filteredCamps = campFilter.applyCampFilter(campList);
                break;
            case 3:
                filteredCamps = CampFilter.sortByAlphabet(campList);
                break;
            default:
                System.out.println("Invalid Choice");
                return null;
        }
        return filteredCamps;
    }

    /**
     * Displays details of filtered/sorted camps based on staff choice.
     *
     * @param campList The list of camps to display.
     */
    
    public void viewCamps(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()) {
            System.out.println("NOT FOUND");
            return;
        }
        int i = 1;
        for (Camp camp : filteredSortedCamps) {
            System.out.println((i++) + ") Camp: " + camp.getCampInfo().getCampName()
            		+ ", Camp User Group: " + camp.getCampInfo().getCampUserGroup()
            		+ ", Start Date: " + camp.getCampInfo().getStartDate()
                    + ", Location: " + camp.getCampInfo().getLocation() 
                    + ", Created by Staff: " + camp.getCampInfo().getStaffInChargeID()
                    + ", Visibility: " + camp.getCampInfo().getCampVisibility()
                    );
        }
    }

    /**
     * Displays and returns a list of filtered/sorted camps owned by the staff.
     *
     * @param campList The list of camps to display.
     * @param showParticipant Whether to show the attendee and Camp committee of the camp
     * @return The list of filtered/sorted camps or null if not found.
     */
    public ArrayList<Camp> viewOwnCamps(ArrayList<Camp> campList, boolean showParticipant) { //return a list of sortedfiltercamp
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()) {
            System.out.println("NOT FOUND");
            return null;
        }
        int i = 1;
        for (Camp camp : filteredSortedCamps) {
            System.out.println("(" + (i++) + ") Camp: " + camp.getCampInfo().getCampName() + ", Start Date: "
                    + camp.getCampInfo().getStartDate()
                    + ", Location: " + camp.getCampInfo().getLocation() + ", Created by Staff "
                    + camp.getCampInfo().getStaffInChargeID());
            ArrayList <Student> studentList = camp.getRegisteredStudents();

            if(!showParticipant) continue;

            if(studentList == null || studentList.isEmpty()){
                System.out.println("No student register for this camp.\n");
                continue;
            }
            int j = 1;
            System.out.println("Registered Student(s): ");
            for(Student student: studentList){
                if(student.getCommitteeForCamp()!=null && student.getCommitteeForCamp().equals(camp)){
                    System.out.println(
                        (j++) + ". " + student.getName() + ", Faculty: " + student.getFaculty().toString() + ", Role: Camp Committee\n");
                }else{
                    System.out.println(
                        (j++) + ". " + student.getName() + ", Faculty: " + student.getFaculty().toString() + ", Role: Attendee\n");
                }
            }
            
        }
        System.out.println("");

        return filteredSortedCamps;
    }
}