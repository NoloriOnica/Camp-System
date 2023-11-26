package Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.CampVisibility;
import CampFilter.ByDate;
import CampFilter.ByLocation;
import CampFilter.CampFilter;
import Camp.Camp;


/**
 * Handles operations related to viewing camps for a student.
 */
public class StudentViewsCamps implements Serializable{
    
    /**
     * Checks if a camp is visible to a student based on its visibility and user group.
     *
     * @param camp    The camp to check visibility for.
     * @param student The student for whom visibility is checked.
     * @return True if the camp is visible to the student, otherwise false.
     */
    
    private boolean isCampVisibleToStudent(Camp camp, Student student) {
        boolean isVisible = camp.getCampInfo().getCampVisibility() == CampVisibility.ON;
        boolean isUserGroupAllowed = camp.getCampInfo().getCampUserGroup().equals(student.getFaculty())
                                     || camp.getCampInfo().getCampUserGroup().equals("Whole NTU");
        return isVisible && isUserGroupAllowed;
    }
    
    /**
     * Filters the selection of camps based on user input.
     *
     * @param campList The list of camps to filter.
     * @return The filtered list of camps based on user selection.
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
            // Handle accordingly, for example, return null or perform some other action.
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
                // Handle accordingly, for example, return null or perform some other action.
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
     * Displays registered camps for a student.
     *
     * @param student The student for whom registered camps are viewed.
     * @return The list of registered camps for the student.
     */
    
    public ArrayList<Camp> viewRegisteredCamps(Student student){ 
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
        ArrayList<Camp> filteredSortedCamps = filterSelection(registeredCamps);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()){
            System.out.println("NOT FOUND");
            return null;
        }
        int i = 0;
        for(Camp camp:filteredSortedCamps){

            if(camp.equals(student.getCommitteeForCamp()))
                System.out.println((++i) + ") Registered Camp Name: " + camp.getCampInfo().getCampName() + ", Role: Camp Committee,"+ " Start Date: "+ camp.getCampInfo().getStartDate()
                + ", Location: "+camp.getCampInfo().getLocation());
            else
                System.out.println((++i) + ") Registered Camp Name: " + camp.getCampInfo().getCampName() + ", Role : Attendee," + " Start Date: "+ camp.getCampInfo().getStartDate()
                + ", Location: "+camp.getCampInfo().getLocation());
        }
        return filteredSortedCamps;
    }
    
    /**
     * Displays available camps for a student.
     *
     * @param allCamps The list of all available camps.
     * @param student  The student for whom available camps are viewed.
     * @return The list of available camps visible to the student.
     */
    
    public ArrayList<Camp> viewCamps(ArrayList<Camp> allCamps, Student student){
        int i = 0;
        ArrayList<Camp> campHolder = new ArrayList<>();
        ArrayList<Camp> filteredSortedCamps = filterSelection(allCamps);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()){
            System.out.println("NOT FOUND");
            return null;
        }
        for (Camp camp : filteredSortedCamps) {
            if (isCampVisibleToStudent(camp, student)) {
                System.out.println((++i) + ") Camp Name: " + camp.getCampInfo().getCampName() + ", Registration Close Date: "+camp.getCampInfo().getRegClosingDate()
                +", Start Date: "+ camp.getCampInfo().getStartDate()+ ", Location: "+camp.getCampInfo().getLocation());
                campHolder.add(camp);
            }
        }
        if(campHolder.isEmpty()){
            System.out.println("No available camps that are visible for you.");
        }
        return campHolder;
    }

    
    /**
     * Displays available camp slots for a student.
     *
     * @param allCamps The list of all camps to check slots for.
     * @param student  The student for whom camp slots are viewed.
     */
    public void viewCampSlots(ArrayList<Camp> allCamps, Student student){
        ArrayList<Camp> filteredSortedCamps = filterSelection(allCamps);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()){
            System.out.println("NOT FOUND");
            return;
        }
        for (Camp camp : filteredSortedCamps) {
            if (isCampVisibleToStudent(camp, student)) { //Only can view certian camps
                int remainingSlots = camp.getCampInfo().getTotalSlots();//gets number of remaining slots
                System.out.println("Camp Name: " + camp.getCampInfo().getCampName() + " - Remaining Slots: " + remainingSlots);
            }
        }
    }
}