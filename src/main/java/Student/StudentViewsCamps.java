package Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import Report.CampsFilter;
import Report.CampsFilterImplementation;
import Camp.CampVisibility;
import Camp.Camp;
import Camp.CampUserGroup;


public class StudentViewsCamps implements Serializable{

    private CampsFilter campsFilter;

    public StudentViewsCamps(){
        campsFilter = new CampsFilterImplementation();
    }

    private boolean isCampVisibleToStudent(Camp camp, Student student) {
        boolean isVisible = camp.getCampInfo().getCampVisibility() == CampVisibility.ON;
        boolean isUserGroupAllowed = camp.getCampInfo().getCampUserGroup().equals(student.getFaculty())
                                     || camp.getCampInfo().getCampUserGroup().equals("Whole NTU");
        return isVisible && isUserGroupAllowed;
    }
    
    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        int choice = 0, index, maxTries = 3;
        Scanner sc = new Scanner(System.in);
        for (int tries = 0; tries < maxTries; tries++) {
            System.out.println("Do you want to filter to view Camps?");
            System.out.println("1) Yes\n2) No");
            index = sc.nextInt();

            if (index == 2) {
                choice = 3;
                break; // Exit the loop if the input is valid
            } else if (index == 1) {
                System.out.println("Choose the filter types?");
                System.out.println("1) By Date Before\n2) By Location");
                choice = sc.nextInt();
                break; // Exit the loop if the input is valid
            } else {
                System.out.println("Invalid Choice. Try again.");
            }
        }

        if (choice == 0) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
            // Handle accordingly, for example, return null or perform some other action.
        }

        sc.nextLine();// consume next line
        switch (choice) {
            case 1:
                int tries = 0;
                LocalDate desiredDate = null;
                
                while (tries < maxTries) {
                    try {
                        System.out.println("Enter Date (YYYY-MM-DD):");
                        desiredDate = LocalDate.parse(sc.nextLine());
                        // Additional checks if needed
                        break; // If the input is valid, break out of the loop
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                        tries++;
                    }
                }
                if (tries == maxTries) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    return null;
                }
                filteredCamps = this.campsFilter.byDate(campList, desiredDate);
                break;
            case 2:
                System.out.println("Enter desired Location");
                String desiredLocation = sc.nextLine();
                filteredCamps = this.campsFilter.byLocation(campList, desiredLocation);
                break;
            case 3:
                filteredCamps = this.campsFilter.sortByAlphabet(campList);
                break;
            default:
                System.out.println("Invalid Choice");
                return null;
        }
        return filteredCamps;
    }
    
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
        return campHolder;
    }

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