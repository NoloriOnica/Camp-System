package Staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Login.User;
import Camp.Camp;
import Camp.CampUserGroup;
import Camp.CampVisibility;
import Feedback.EnquiriesController;
import Feedback.SuggestionController;
import Report.CampReportGenerator;
import Report.PerformanceReportGenerator;

public class Staff extends User {

    private ArrayList<Camp> createdCamps;
    private EnquiriesController enquiriesController;
    private StaffViewsCamps staffViewsCamps;
    private SuggestionController suggestionController;
    private CampReportGenerator campReportGenerator;
    private PerformanceReportGenerator performanceReportGenerator;
    
    public Staff(String userID, String name, String email, String faculty, String userType) {
        super(userID, name,email,faculty,userType);
        this.createdCamps = new ArrayList<>();
        this.staffViewsCamps = new StaffViewsCamps();
        this.enquiriesController = new EnquiriesController();
        this.suggestionController = new SuggestionController();
        this.campReportGenerator = new CampReportGenerator();
        this.performanceReportGenerator = new PerformanceReportGenerator();
    }
    

    public Camp createCamp(ArrayList<Camp> allCamps) { //Pass in ALL CAMPS as parameter //Return Null if the camp is not created
        // Implementation for creating a camp
        //Declaration for the attribute needed for a Camp
        String campName; LocalDate startDate; LocalDate endDate; LocalDate regClosingDate;
        CampUserGroup campUserGroup = CampUserGroup.WHOLE_NTU; 
        CampVisibility campVisibility = CampVisibility.ON; 
        String location; int totalSlots;
        int campCommitteeSlot; String description ;
        String staffInChargeID = super.getName();

        int tries = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Camp Name:");
        campName = sc.nextLine();
        // Check if the camp name already exists
        boolean campExists = false;
        for (Camp existingCamp : allCamps) {
            if (existingCamp.getCampInfo().getCampName().equalsIgnoreCase(campName)) {
                campExists = true;
                break;
            }
        }
        if (campExists) {
            System.out.println(
                    "Camp with the name '" + campName + "' already exists. Choose a different name.");
            return null;
        }

        System.out.println("Enter Start Date (YYYY-MM-DD):");
        startDate = LocalDate.parse(sc.nextLine());

        System.out.println("Enter End Date (YYYY-MM-DD):");
        endDate = LocalDate.parse(sc.nextLine());

        System.out.println("Enter Registration Closing Date (YYYY-MM-DD):");
        regClosingDate = LocalDate.parse(sc.nextLine());

        System.out.println("Select Camp User Group");
        System.out.println("1) Whole NTU\n2)SCSE\n3)NBS\n4)SPMS");
        tries = 0; int index = sc.nextInt();
        while (tries < 3) {
            switch (index) {
                case 1:
                    campUserGroup = CampUserGroup.WHOLE_NTU;
                    break;
                case 2:
                    campUserGroup = CampUserGroup.SCSE;
                    break;
                case 3:
                    campUserGroup = CampUserGroup.NBS;
                    break;
                case 4:
                    campUserGroup = CampUserGroup.SPMS;
                    break;
                default:
                    if(tries < 3)
                        System.out.println("Invalid input, please enter again.");
                    tries++;
                    break;
            }
        }
        if (tries >= 3) {
            System.out.println("Please try again later.");
            return null;
        }
        
        
        System.out.println("Select Camp's Visibility");
        System.out.println("1)ON\n2)OFF");
        tries = 0; index = sc.nextInt();
        while (tries < 3) {
            switch (index) {
                case 1:
                    campVisibility = CampVisibility.ON;
                    break;
                case 2:
                    campVisibility = CampVisibility.OFF;
                    break;
                default:
                    if(tries < 3)
                        System.out.println("Invalid input, please enter again.");
                    tries++;
                    break;
            }
        }
        if (tries >= 3) {
            System.out.println("Please try again later.");
            return null;
        }
        sc.nextLine();//Consume next line

        System.out.println("Enter Camp's Location");
        location = sc.nextLine();

        System.out.println("Enter Camp's Total Slots");
        totalSlots = sc.nextInt();

        System.out.println("Enter Camp's Camp Committee Slots (Max = 10)");
        campCommitteeSlot = sc.nextInt();
        if(campCommitteeSlot > 10)
            campCommitteeSlot = 10;
        
        System.out.println("Enter Camp's Description");
        description = sc.nextLine();


        Camp camp = new Camp(campName, startDate, endDate, regClosingDate, campUserGroup, campVisibility, 
        location, totalSlots, campCommitteeSlot, description, staffInChargeID);
        this.createdCamps.add(camp);
        System.out.println("Camp "+camp.getCampInfo().getCampName()+" created successfully.");
        return camp;
    }

    public boolean editCamp(ArrayList<Camp> allCamps) {
        // Implementation for editing a camp
        // Check if the camp object is in the createdCamps (Created by this staff)

        Scanner sc = new Scanner(System.in);
        int index;
        this.viewOwnCamps();

        // Validate user input
        while (true) {
            System.out.println("Select which camp you want to edit:");
            try {
                index = sc.nextInt();

                if (index >= 1 && index <= this.createdCamps.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
        Camp camp = this.createdCamps.get(index-1); //Get the camp that the staff wanna edit

        int tries = 0;
        System.out.println("Select what information you want to edit:");
        System.out.println(
                "1) Camp Name\n2) Start Date\n3) End Date\n4) Registration Closing Date\n5) User Group\n6) Camp Visibility\n7) Location\n8) Total Slots\n9) Camp Committee Slot\n10) Description");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                System.out.println("Enter the new Camp Name:");
                String newCampName = sc.nextLine();
                boolean campExists = false;
                for (Camp existingCamp : allCamps) {
                    if (existingCamp.getCampInfo().getCampName().equalsIgnoreCase(newCampName)) {
                        campExists = true;
                        break;
                    }
                }
                if (campExists) {
                    System.out.println(
                            "Camp with the name '" + newCampName + "' already exists. Choose a different name.");
                    return false;
                }
                System.out.println("The camp name has changed from "+camp.getCampInfo().getCampName()+" to "+newCampName);
                camp.getCampInfo().setCampName(newCampName);
                return true;
            case 2:
                System.out.println("Enter the new Start Date (YYYY-MM-DD):");
                LocalDate newStartDate = LocalDate.parse(sc.nextLine());
                camp.getCampInfo().setStartDate(newStartDate);
                System.out.println("The start date has changed to " + newStartDate);
                return true;
            case 3:
                System.out.println("Enter the new End Date (YYYY-MM-DD):");
                LocalDate newEndDate = LocalDate.parse(sc.nextLine());
                camp.getCampInfo().setEndDate(newEndDate);
                System.out.println("The end date has changed to " + newEndDate);
                return true;
            case 4:
                System.out.println("Enter the new Registration Closing Date (YYYY-MM-DD):");
                LocalDate newRegClosingDate = LocalDate.parse(sc.nextLine());
                camp.getCampInfo().setRegClosingDate(newRegClosingDate);
                System.out.println("The Registration Closing date has changed to " + newRegClosingDate);
                return true;
            case 5:
                System.out.println("Select the new Camp User Group:");
                System.out.println("1) Whole NTU\n2)SCSE\n3)NBS\n4)SPMS");
                tries = 0;
                int index1 = sc.nextInt();
                while (tries < 3) {
                    switch (index1) {
                        case 1:
                            camp.getCampInfo().setCampUserGroup(CampUserGroup.WHOLE_NTU);
                            break;
                        case 2:
                            camp.getCampInfo().setCampUserGroup(CampUserGroup.SCSE);
                            break;
                        case 3:
                            camp.getCampInfo().setCampUserGroup(CampUserGroup.NBS);
                            break;
                        case 4:
                            camp.getCampInfo().setCampUserGroup(CampUserGroup.SPMS);
                            break;
                        default:
                            if (tries < 3)
                                System.out.println("Invalid input, Please enter again.");
                            tries++;
                    }
                }
                if (tries >= 3) {
                    System.out.println("Please try again later.");
                    return false;
                }else{
                    System.out.println("The Camp User Group has changed to "+ camp.getCampInfo().getCampUserGroup().toString());
                    return true;
                }
            case 6:
                System.out.println("Enter the new Camp Visibility (ON or OFF):");
                System.out.println("1)ON\n2)OFF");
                tries = 0;
                index = sc.nextInt();
                while (tries < 3) {
                    switch (index) {
                        case 1:
                            camp.getCampInfo().setCampVisibility(CampVisibility.ON);
                            break;
                        case 2:
                            camp.getCampInfo().setCampVisibility(CampVisibility.OFF);
                            break;
                        default:
                            if (tries < 3)
                                System.out.println("Invalid input, please enter again.");
                            tries++;
                            break;
                    }
                }
                if (tries >= 3) {
                    System.out.println("Please try again later.");
                    return false;
                } else {
                    System.out.println("The Camp User Group has changed to " + camp.getCampInfo().getCampVisibility().toString());
                }
                return true;
            case 7:
                System.out.println("Enter the new Location:");
                String newLocation = sc.nextLine();
                camp.getCampInfo().setLocation(newLocation);
                System.out.println("The Location has changed to " + newLocation);
                return true;
            case 8:
                System.out.println("Enter the new Total Slots:");
                int newTotalSlots = sc.nextInt();
                camp.getCampInfo().setTotalSlots(newTotalSlots);
                System.out.println("The Total Slots has changed to " + newTotalSlots);
                return true;
            case 9:
                System.out.println("Enter the new Camp Committee Slot (Max = 10):");
                int newCampCommitteeSlot = sc.nextInt();
                if(newCampCommitteeSlot > 10){
                    newCampCommitteeSlot = 10;
                    System.out.println("The Camp Committee slot has changed to 10");
                }
                else{
                    camp.getCampInfo().setCampCommitteeSlot(newCampCommitteeSlot);
                    System.out.println("The Camp Committee slot has changed to " + newCampCommitteeSlot);
                }
                return true;
            case 10:
                System.out.println("Enter the new Description:");
                String newDescription = sc.nextLine();
                camp.getCampInfo().setDescription(newDescription);
                System.out.println("The Description has changed to " + newDescription);
                return true;
            default:
                System.out.println("Invalid choice");
                return false;
        }
    }

    public boolean deleteCamp(ArrayList<Camp> allCamps) {
        // Implementation for deleting a camp
        Scanner sc = new Scanner(System.in);
        int index;
        this.viewOwnCamps();

        // Validate user input
        while (true) {
            System.out.println("Select which camp you want to delete:");
            try {
                index = sc.nextInt();

                if (index >= 1 && index <= this.createdCamps.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
        Camp camp = this.createdCamps.get(index-1); //Get the camp that the staff wanna delete
        if(camp.getRegisteredStudents().isEmpty()){ //Only can delete the empty camp
            allCamps.remove(camp); //Remove the Camp on global
            this.createdCamps.remove(camp); //Remove the Camp on Staff's end
            return true;
        }else{
            System.out.println("Unable to delete a non empty camp!");
            return false;
        }
    }

    public void toggleCampVisibility() {
        // Implementation for toggling the CampVisibility
        Scanner sc = new Scanner(System.in);
        int index;
        this.viewOwnCamps();

        // Validate user input
        while (true) {
            System.out.println("Select which camp you want to toggle visibility:");
            try {
                index = sc.nextInt();

                if (index >= 1 && index <= this.createdCamps.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }

        Camp camp = this.createdCamps.get(index-1); //Get the camp that the staff wanna edit
        if (camp.getCampInfo().getCampVisibility()== CampVisibility.OFF) {
            camp.getCampInfo().setCampVisibility(CampVisibility.ON);;
            System.out.println("The camp" + camp.getCampInfo().getCampName() + "now can be viewed by others.");
        } else {
            camp.getCampInfo().setCampVisibility(CampVisibility.OFF);
            System.out.println("The camp" + camp.getCampInfo().getCampName() + "now cannot be viewed by others.");
        }
    }

    public void viewCamps(ArrayList<Camp> allCamps){ //Pass in a global variable that store all camps
        staffViewsCamps.viewCamps(allCamps); 
    }

    public void viewOwnCamps(){ 
        staffViewsCamps.viewOwnCamps(this.createdCamps); //Pass in Staff's attribute as parameter
    }

    public void viewEnquiries(){
        enquiriesController.viewEnquiries(this.createdCamps);//Pass in Staff's attribute as parameter
    }

    public void replyEnquiries(){
        enquiriesController.replyEnquiries(this.createdCamps);//Pass in Staff's attribute as parameter
    }

    public void viewSuggestion(){
        suggestionController.viewSuggestion(this.createdCamps);//Pass in Staff's attribute as parameter
    }

    public void approveSuggestion(){
        suggestionController.approveSuggestion(this.createdCamps);//Pass in Staff's attribute as parameter
    }

    public void generateCampReport(){
        this.campReportGenerator.generateCampReport(this.createdCamps);
    }
    
    public void generatePerformanceReport(){
        performanceReportGenerator.generatePerformanceReport(this.createdCamps);
    }
}
    