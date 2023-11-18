package Staff;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Login.User;
import Camp.Camp;
import Camp.CampVisibility;
import Feedback.EnquiriesController;
import Feedback.SuggestionController;
import Report.CampReportGenerator;
import Report.PerformanceReportGenerator;

public class Staff extends User implements Serializable{

    private ArrayList<Camp> createdCamps;
    private EnquiriesController enquiriesController;
    private StaffViewsCamps staffViewsCamps;
    private SuggestionController suggestionController;
    private CampReportGenerator campReportGenerator;
    private PerformanceReportGenerator performanceReportGenerator;
    private static final int MAX_TRIES = 3;

    public Staff(String userID, String name, String email, String faculty, String userType) {
        super(userID, name, email, faculty, userType);
        this.createdCamps = new ArrayList<>();
        this.staffViewsCamps = new StaffViewsCamps();
        this.enquiriesController = new EnquiriesController();
        this.suggestionController = new SuggestionController();
        this.campReportGenerator = new CampReportGenerator();
        this.performanceReportGenerator = new PerformanceReportGenerator();
    }

    public Camp createCamp(ArrayList<Camp> allCamps) { // Pass in ALL CAMPS as parameter //Return Null if the camp is
                                                       // not created
        // Implementation for creating a camp
        // Declaration for the attribute needed for a Camp
        String campName = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDate regClosingDate = null;
        String campUserGroup = null;
        CampVisibility campVisibility = CampVisibility.ON;
        String location;
        int totalSlots = 0;
        int campCommitteeSlot = 0;
        String description;
        String staffInChargeID = super.getName();

        int tries = 0;
        Scanner sc = new Scanner(System.in);

        // Create Camp Name
        while (tries < MAX_TRIES) {
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
                System.out.println("Camp with the name '" + campName + "' already exists. Choose a different name.");
                tries++;
            } else {
                break; // Exit the loop if the camp name is unique
            }
        }
        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        // Create Start Date
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Enter Start Date (YYYY-MM-DD):");
            try {
                startDate = LocalDate.parse(sc.nextLine());

                // Perform additional error checking if needed

                // If the input is valid, break out of the loop
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                tries++;
            }
        }

        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null; // Or handle it as needed in your code
        }

        // Create End Date
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Enter End Date (YYYY-MM-DD):");
            try {
                endDate = LocalDate.parse(sc.nextLine());

                // Perform additional error checking
                if (endDate.isBefore(startDate)) {
                    System.out.println("Error: End Date must be later than the Start Date.");
                    tries++;
                } else {
                    // If the input is valid, break out of the loop
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                tries++;
            }
        }
        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null; // Or handle it as needed in your code
        }

        // Create registration closing Date
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Enter Registration Closing Date (YYYY-MM-DD):");
            try {
                regClosingDate = LocalDate.parse(sc.nextLine());

                // Perform additional error checking
                if (!regClosingDate.isBefore(startDate)) {
                    System.out.println("Error: Registration Closing Date must be before the Start Date.");
                    tries++;
                } else {
                    // If the input is valid, break out of the loop
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                tries++;
            }
        }

        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null; // Or handle it as needed in your code
        }

        // Select Camp User Group
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select Camp User Group");
            System.out.println("1) Whole NTU\n2) SCSE\n3) NBS\n4) SPMS");
            try {
                int index = sc.nextInt();
                sc.nextLine(); // Consume the newline character after reading the integer

                switch (index) {
                    case 1:
                        campUserGroup = "Whole NTU";
                        break;
                    case 2:
                        campUserGroup = "SCSE";
                        break;
                    case 3:
                        campUserGroup = "NBS";
                        break;
                    case 4:
                        campUserGroup = "SPMS";
                        break;
                    default:
                        System.out.println("Invalid input, please enter a number between 1 and 4.");
                        tries++;
                        continue; // Skip the rest of the loop and start over
                }
                // If the switch was successful, break out of the loop
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number within the range.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }
        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        // Select Camp Visibility
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select Camp's Visibility");
            System.out.println("1) ON\n2) OFF");

            try {
                int index = sc.nextInt();
                sc.nextLine(); // Consume the newline character after reading the integer

                switch (index) {
                    case 1:
                        campVisibility = CampVisibility.ON;
                        break;
                    case 2:
                        campVisibility = CampVisibility.OFF;
                        break;
                    default:
                        System.out.println("Invalid input, please enter 1 for ON or 2 for OFF.");
                        tries++;
                        continue; // Skip the rest of the loop and start over
                }

                // If the switch was successful, break out of the loop
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter either 1 for ON or 2 for OFF.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        // Create Camp Location
        System.out.println("Enter Camp's Location");
        location = sc.nextLine();
        while (tries < MAX_TRIES) {
            if (location.trim().isEmpty()) {
                System.out.println("Location cannot be blank. Please enter a valid location:");
                location = sc.nextLine();
                tries++;
            }
        }

        if (tries >= MAX_TRIES) {
            System.out.println("Please try again later.");
            return null; // Return false if the user exceeds the maximum number of tries
        }
            

        // Enter Camp's Total Slots
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Enter Camp's Total Slots:");
            try {
                totalSlots = sc.nextInt();

                // Perform additional error checking if needed

                // If the input is valid, break out of the loop
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for total slots.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null; // Or handle it as needed in your code
        }

        // Enter Camp's Camp Committee Slots
        tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Enter Camp's Camp Committee Slots (Max = 10):");
            try {
                campCommitteeSlot = sc.nextInt();

                // Perform additional error checking if needed

                // If the input is valid, break out of the loop
                if (campCommitteeSlot <= 10) {
                    break;
                } else {
                    System.out.println("Error: Camp Committee Slots cannot exceed the maximum limit of 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for Camp Committee Slots.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null; // Or handle it as needed in your code
        }

        sc.nextLine();// Consume nextline

        // Create Camp Description
        System.out.println("Enter Camp's Description");
        description = sc.nextLine();

        Camp camp = new Camp(campName, startDate, endDate, regClosingDate, campUserGroup, campVisibility,
                location, totalSlots, campCommitteeSlot, description, staffInChargeID);
        this.createdCamps.add(camp);
        System.out.println("Camp " + camp.getCampInfo().getCampName() + " created successfully.");
        return camp;
    }

    public boolean editCamp(ArrayList<Camp> allCamps) {
        // Implementation for editing a camp
        // Check if the camp object is in the createdCamps (Created by this staff)
        if(this.createdCamps == null || this.createdCamps.isEmpty()){
            System.out.println("You have not created any camps!");
            return false;
        }

        Scanner sc = new Scanner(System.in);
        int index = 0;
        ArrayList<Camp> filterSortedCreatedCamp = this.viewOwnCamps();
        int tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select which camp you want to edit:");
        
            try {
                index = sc.nextInt();
                index--; // Decrement the index to match the zero-based index of the list
        
                if (index >= 0 && index < filterSortedCreatedCamp.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                    tries++;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        
        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return false;
        }
        
        Camp selectedCamp = filterSortedCreatedCamp.get(index); // Get the camp that the staff wanna edit

        tries = 0;
        int choice = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select what information you want to edit:");
            System.out.println(
                    "1) Camp Name\n2) Start Date\n3) End Date\n4) Registration Closing Date\n5) User Group\n6) Camp Visibility\n7) Location\n8) Total Slots\n9) Camp Committee Slot\n10) Description");

            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= 10) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid number within the range.");
                    tries++;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        if (tries == MAX_TRIES) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return false;
        }

        switch (choice) {
            case 1:
                tries = 0;
                while (tries < MAX_TRIES) {
                    System.out.println("Enter the new Camp Name:");
                    String newCampName = sc.nextLine();
                    // Check if the new camp name already exists
                    boolean campExists = false;
                    for (Camp existingCamp : allCamps) {
                        if (existingCamp.getCampInfo().getCampName().equalsIgnoreCase(newCampName) &&
                                !existingCamp.equals(selectedCamp)) {
                            campExists = true;
                            break;
                        }
                    }
                    if (campExists) {
                        System.out.println(
                                "Camp with the name '" + newCampName + "' already exists. Choose a different name.");
                        tries++;
                    } else {
                        System.out.println(
                                "The camp name has changed from " + selectedCamp.getCampInfo().getCampName() + " to "
                                        + newCampName);
                        selectedCamp.getCampInfo().setCampName(newCampName);
                        return true; // Exit the loop and return true if the camp name is successfully changed
                    }
                }
                if (tries == MAX_TRIES) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    // Handle the case where the user exceeds the maximum number of tries
                }
                return false; // Return false if the user couldn't change the camp name within the allowed
                              // tries
            case 2:
                tries = 0;
                while (tries < MAX_TRIES) {
                    try {
                        System.out.println("Enter the new Start Date (YYYY-MM-DD):");
                        LocalDate newStartDate = LocalDate.parse(sc.nextLine());
                        // If the input is valid, set the new start date and exit the loop
                        selectedCamp.getCampInfo().setStartDate(newStartDate);
                        System.out.println("The start date has changed to " + newStartDate);
                        return true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                        tries++;
                    }
                }
                if (tries == MAX_TRIES) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    // Handle the case where the user exceeds the maximum number of tries
                }
                return false;

            case 3:
                tries = 0;
                while (tries < MAX_TRIES) {
                    try {
                        System.out.println("Enter the new End Date (YYYY-MM-DD):");
                        LocalDate newEndDate = LocalDate.parse(sc.nextLine());

                        // Check if the new end date is after the current start date
                        if (newEndDate.isAfter(selectedCamp.getCampInfo().getStartDate())) {
                            // If valid, set the new end date and exit the loop
                            selectedCamp.getCampInfo().setEndDate(newEndDate);
                            System.out.println("The end date has changed to " + newEndDate);
                            return true;
                        } else {
                            System.out.println("Error: The new end date must be after the current start date.");
                            tries++;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                        tries++;
                    }
                }
                if (tries == MAX_TRIES) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    // Handle the case where the user exceeds the maximum number of tries
                }
                return false;

            case 4:
                tries = 0;
                while (tries < MAX_TRIES) {
                    try {
                        System.out.println("Enter the new Registration Closing Date (YYYY-MM-DD):");
                        LocalDate newRegClosingDate = LocalDate.parse(sc.nextLine());

                        // Check if the new registration closing date is before the current start date
                        if (newRegClosingDate.isBefore(selectedCamp.getCampInfo().getStartDate())) {
                            // If valid, set the new registration closing date and exit the loop
                            selectedCamp.getCampInfo().setRegClosingDate(newRegClosingDate);
                            System.out.println("The Registration Closing date has changed to " + newRegClosingDate);
                            return true;
                        } else {
                            System.out.println(
                                    "Error: The new Registration Closing date must be before the current start date.");
                            tries++;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                        tries++;
                    }
                }
                if (tries == MAX_TRIES) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    // Handle the case where the user exceeds the maximum number of tries
                }
                return false;

            case 5:
                tries = 0;
                while (tries < MAX_TRIES) {
                    System.out.println("Select the new Camp User Group:");
                    System.out.println("1) Whole NTU\n2) SCSE\n3) NBS\n4) SPMS");

                    int index1 = sc.nextInt();

                    switch (index1) {
                        case 1:
                            selectedCamp.getCampInfo().setCampUserGroup("Whole NTU");
                            break;
                        case 2:
                            selectedCamp.getCampInfo().setCampUserGroup("SCSE");
                            break;
                        case 3:
                            selectedCamp.getCampInfo().setCampUserGroup("NBS");
                            break;
                        case 4:
                            selectedCamp.getCampInfo().setCampUserGroup("SPMS");
                            break;
                        default:
                            System.out.println("Invalid input. Please enter again.");
                            tries++;
                            continue; // Continue to the next iteration of the loop
                    }

                    // If the user input is valid, break out of the loop
                    break;
                }
                if (tries >= MAX_TRIES) {
                    System.out.println("Please try again later.");
                    return false; // Return false if the user exceeds the maximum number of tries
                } else {
                    System.out.println(
                            "The Camp User Group has changed to "
                                    + selectedCamp.getCampInfo().getCampUserGroup().toString());
                    return true;
                }

            case 6:
                tries = 0;

                while (tries < MAX_TRIES) {
                    System.out.println("Enter the new Camp Visibility (ON or OFF):");
                    System.out.println("1) ON\n2) OFF");

                    index = sc.nextInt();

                    switch (index) {
                        case 1:
                            selectedCamp.getCampInfo().setCampVisibility(CampVisibility.ON);
                            break;
                        case 2:
                            selectedCamp.getCampInfo().setCampVisibility(CampVisibility.OFF);
                            break;
                        default:
                            System.out.println("Invalid input. Please enter again.");
                            tries++;
                            continue; // Continue to the next iteration of the loop
                    }

                    // If the user input is valid, break out of the loop
                    break;
                }

                if (tries >= MAX_TRIES) {
                    System.out.println("Please try again later.");
                    return false; // Return false if the user exceeds the maximum number of tries
                } else {
                    System.out.println(
                            "The Camp Visibility has changed to "
                                    + selectedCamp.getCampInfo().getCampVisibility().toString());
                    return true;
                }

            case 7:
                tries = 0;
                System.out.println("Enter the new Location:");
                String newLocation = sc.nextLine();

                while(tries < MAX_TRIES) {
	                if (newLocation.trim().isEmpty()) {
	                    System.out.println("Location cannot be blank. Please enter a valid location:");
	                    newLocation = sc.nextLine();
	                    tries++;
	                }
                }
            
                if (tries >= MAX_TRIES) {
                    System.out.println("Please try again later.");
                    return false; // Return false if the user exceeds the maximum number of tries
                }
                selectedCamp.getCampInfo().setLocation(newLocation);
                System.out.println("The Location has changed to " + newLocation);
                return true;

            case 8:
                tries = 0;
                while (tries < MAX_TRIES) {
                    System.out.println("Enter the new Total Slots:");

                    if (sc.hasNextInt()) {
                        int newTotalSlots = sc.nextInt();

                        if (newTotalSlots >= 0) {
                            selectedCamp.getCampInfo().setTotalSlots(newTotalSlots);
                            selectedCamp.setRemainingAttendeeSlot(selectedCamp.getCampInfo().getTotalSlots()-selectedCamp.getCampInfo().getCampCommitteeSlot());
                            System.out.println("The Total Slots has changed to " + newTotalSlots);
                            return true; // Return true if the user input is valid
                        } else {
                            System.out.println("Invalid input. Please enter a non-negative integer.");
                            tries++;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next(); // Consume the invalid input to prevent an infinite loop
                        tries++;
                    }
                }
                // If the user exceeds the maximum number of tries, print a message and return
                // false
                System.out.println("Please try again later.");
                return false;

            case 9:
                tries = 0;

                while (tries < MAX_TRIES) {
                    System.out.println("Enter the new Camp Committee Slot (Max = 10):");

                    try {
                        int newCampCommitteeSlot = sc.nextInt();

                        if (newCampCommitteeSlot >= 0 && newCampCommitteeSlot <= 10) {
                            selectedCamp.getCampInfo().setCampCommitteeSlot(newCampCommitteeSlot);
                            System.out.println("The Camp Committee slot has changed to " + newCampCommitteeSlot);
                            return true; // Return true if the user input is valid
                        } else {
                            System.out.println("Invalid input. Please enter a value between 0 and 10.");
                            tries++;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next(); // Consume the invalid input to prevent an infinite loop
                        tries++;
                    }
                }
                System.out.println("Please try again later.");
                return false;

            case 10:
                System.out.println("Enter the new Description:");
                String newDescription = sc.nextLine();
                selectedCamp.getCampInfo().setDescription(newDescription);
                System.out.println("The Description has changed to " + newDescription);
                return true;

            default:
                System.out.println("Invalid choice");
                return false;
        }
    }

    public boolean deleteCamp(ArrayList<Camp> allCamps) {
        // Implementation for deleting a camp
        
        if(this.createdCamps == null || this.createdCamps.isEmpty()){
            System.out.println("You have not created any camps!");
            return false;
        }

        Scanner sc = new Scanner(System.in);
        int index = 0;
        ArrayList<Camp> filterSortedCreatedCamp = this.viewOwnCamps();

        // Validate user input
        int tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select which camp you want to delete:");

            try {
                index = sc.nextInt();

                if (index >= 1 && index <= filterSortedCreatedCamp.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }

            tries++;

            if (tries == MAX_TRIES) {
                System.out.println("You've reached the maximum number of tries. Exiting...");
                return false; // Or handle it as needed in your code
            }
        }
        Camp selectedCamp = filterSortedCreatedCamp.get(index - 1); // Get the camp that the staff wanna delete

        if (selectedCamp.getRegisteredStudents().isEmpty()) { // Only can delete the empty camp
            allCamps.remove(selectedCamp); // Remove the Camp on global
            this.createdCamps.remove(selectedCamp); // Remove the Camp on Staff's end
            System.out.println("Camp " + selectedCamp.getCampInfo().getCampName() + " has been deleted.");
            return true;
        } else {
            System.out.println("Unable to delete a non empty camp!");
            return false;
        }
    }

    public void toggleCampVisibility() {
        // Implementation for toggling the CampVisibility

        if(this.createdCamps == null || this.createdCamps.isEmpty()){
            System.out.println("You have not created any camps!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int index = 0;
        ArrayList<Camp> filterSortedCreatedCamp = this.viewOwnCamps();

        // Validate user input
        int tries = 0;
        while (tries < MAX_TRIES) {
            System.out.println("Select which camp you want to toggle visibility:");

            try {
                index = sc.nextInt();

                if (index >= 1 && index <= filterSortedCreatedCamp.size()) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }

            tries++;

            if (tries == MAX_TRIES) {
                System.out.println("You've reached the maximum number of tries. Exiting...");
                return; // Or handle it as needed in your code
            }
        }

        Camp selectedCamp = this.createdCamps.get(index - 1); // Get the camp that the staff wanna edit

        if (selectedCamp.getCampInfo().getCampVisibility() == CampVisibility.OFF) {
            selectedCamp.getCampInfo().setCampVisibility(CampVisibility.ON);
            System.out.println("The camp " + selectedCamp.getCampInfo().getCampName() + " now can be viewed by students.");
        } else {
            selectedCamp.getCampInfo().setCampVisibility(CampVisibility.OFF);
            System.out.println("The camp " + selectedCamp.getCampInfo().getCampName() + " now cannot be viewed by students.");
        }
    }

    public void viewCamps(ArrayList<Camp> allCamps) { // Pass in a global variable that store all camps
        staffViewsCamps.viewCamps(allCamps);
    }

    public ArrayList<Camp> viewOwnCamps() {
        return staffViewsCamps.viewOwnCamps(this.createdCamps); // Pass in Staff's attribute as parameter
    }

    public void viewEnquiries() {
        enquiriesController.viewEnquiries(this.createdCamps);// Pass in Staff's attribute as parameter
    }

    public void replyEnquiries() {
        enquiriesController.replyEnquiries(this.createdCamps);// Pass in Staff's attribute as parameter
    }

    public void viewSuggestion() {
        suggestionController.viewSuggestion(this.createdCamps);// Pass in Staff's attribute as parameter
    }

    public void approveSuggestion() {
        suggestionController.approveSuggestion(this.createdCamps);// Pass in Staff's attribute as parameter
    }

    public void generateCampReport() {
        this.campReportGenerator.generateCampReport(this.createdCamps);
    }

    public void generatePerformanceReport() {
        performanceReportGenerator.generatePerformanceReport(this.createdCamps);
    }

    //Below are getters and setters
    public  ArrayList<Camp> getCreatedCamp(){
        return this.createdCamps;
    }
}