package Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import Report.CampsFilter;
import Report.CampsFilterImplementation;
import Camp.CampVisibility;
import Camp.Camp;
import Camp.CampUserGroup;


public class StudentViewsCamps{

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
    
    private ArrayList<Camp> filterSelection( ArrayList<Camp> campList){
        ArrayList<Camp> filteredCamps;
        Scanner sc = new Scanner(System.in);
        int choice , index;
        System.out.println("Do you want to filter to view Camps?");
        System.out.println("1)Yes\n2)No");
        index = sc.nextInt();
        if(index == 2){
            choice = 3;
        }else if(index == 1){
            System.out.println("Choose the filter types?");
            System.out.println("1)By Date Before\n2)By Location");
            choice = sc.nextInt();
        }else{
            System.out.println("Invalid Choice");
            return null;
        }
        switch(choice){
            case 1:
                System.out.println("Enter Start Date (YYYY-MM-DD):");
                LocalDate desiredDate = LocalDate.parse(sc.nextLine());
                filteredCamps = this.campsFilter.byDate(campList,desiredDate);
                break;
            case 2:
                System.out.println("Enter desired Location");
                String desiredLocation = sc.nextLine();
                filteredCamps = this.campsFilter.byLocation(campList,desiredLocation);
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
    
    public void viewRegisteredCamps(Student student){
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
        ArrayList<Camp> filterSortedCamps = filterSelection(registeredCamps);
        if(filterSortedCamps.isEmpty() || filterSortedCamps ==null){
            System.out.println("NOT FOUND");
            return;
        }
        int i = 0;
        for(Camp camp:filterSortedCamps){
            if(camp.equals(student.getCommitteeForCamp()))
                System.out.println((++i) + ") Registered Camp Name: " + camp.getCampInfo().getCampName() + "role: Camp Committee"+ " Start Date: "+ camp.getCampInfo().getStartDate()
                + " Location: "+camp.getCampInfo().getLocation());
            else
                System.out.println((++i) + ") Registered Camp Name: " + camp.getCampInfo().getCampName() + "role : Attendee" + " Start Date: "+ camp.getCampInfo().getStartDate()
                + " Location: "+camp.getCampInfo().getLocation());
        }
    }

    public ArrayList<Camp> viewCamps(ArrayList<Camp> allCamps, Student student){
        int i = 0;
        ArrayList<Camp> campHolder = new ArrayList<>();
        ArrayList<Camp> filterSortedCamps = filterSelection(allCamps);
        if(filterSortedCamps.isEmpty() || filterSortedCamps ==null){
            System.out.println("NOT FOUND");
            return null;
        }
        for (Camp camp : filterSortedCamps) {
            if (isCampVisibleToStudent(camp, student)) {
                System.out.println((++i) + ") Camp Name: " + camp.getCampInfo().getCampName() + " Start Date: "+ camp.getCampInfo().getStartDate()
                + " Location: "+camp.getCampInfo().getLocation());
                campHolder.add(camp);
            }
        }
        return campHolder;
    }

    public void viewCampSlots(ArrayList<Camp> allCamps, Student student){
        ArrayList<Camp> filterSortedCamps = filterSelection(allCamps);
        if(filterSortedCamps.isEmpty() || filterSortedCamps ==null){
            System.out.println("NOT FOUND");
            return;
        }
        for (Camp camp : filterSortedCamps) {
            if (isCampVisibleToStudent(camp, student)) { //Only can view certian camps
                int remainingSlots = camp.getCampInfo().getTotalSlots();//gets number of remaining slots
                System.out.println("Camp Name: " + camp.getCampInfo().getCampName() + " - Remaining Slots: " + remainingSlots);
            }
        }
    }
}