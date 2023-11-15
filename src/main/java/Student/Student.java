package Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

import Login.User;
import Camp.Camp;
import Camp.CampUserGroup;
import Camp.CampCommittee;
import Feedback.Enquiries;
import Feedback.EnquiriesHandler;
import Student.StudentViewsCamps;

public class Student extends User implements Serializable {
    private boolean isCampCommittee;
    private Camp committeeForCamp; // field to store the camp for which the student is a committee
    private ArrayList<Camp> registeredCamps;
    private ArrayList<Camp> bannedCamps; //Student are not allow to register for the camp that he withdrawed before
    private ArrayList<Enquiries> enquiriesList; //Different from camp committee's suggestion, a student can send
                                            //enquiries to multiple camps
    private StudentViewsCamps studentViewsCamps;
    private EnquiriesHandler enquiriesHandler;
    
    public Student(String userID, String name, String email, String faculty, String userType) {
        super(userID, name, email,faculty,userType);
        this.registeredCamps = new ArrayList<>();
        this.enquiriesList = new ArrayList<>();
        this.bannedCamps = new ArrayList<>();
        this.isCampCommittee = false;
        this.committeeForCamp = null;

        studentViewsCamps = new StudentViewsCamps();
        enquiriesHandler = new EnquiriesHandler();
    }

    private boolean checkForDateClashes(Camp newCamp) {
        LocalDate newStartDate = newCamp.getCampInfo().getStartDate();
        LocalDate newEndDate = newCamp.getCampInfo().getEndDate();

        for (Camp registeredCamp : registeredCamps) {
            LocalDate registeredStartDate = registeredCamp.getCampInfo().getStartDate();
            LocalDate registeredEndDate = registeredCamp.getCampInfo().getEndDate();

            // Check for date clashes
            if (!(newEndDate.isBefore(registeredStartDate) || newStartDate.isAfter(registeredEndDate))) {
                // Date clash detected
                return false;
            }
        }

    // No date clashes found
    return true;
}
    
    public void viewRegisteredCamps(){
        studentViewsCamps.viewRegisteredCamps(this);
    }
    
    public ArrayList<Camp> viewCamps(ArrayList<Camp> allCamps) { //Return a list a camp's object that can be viewed by this student
        return studentViewsCamps.viewCamps(allCamps, this);
    }
    
    public void viewCampSlots(ArrayList<Camp> allCamps) { 
        studentViewsCamps.viewCampSlots(allCamps, this);
    }
    
    public void registerForCamp(ArrayList<Camp> allCamps) {
        // View available camps
        ArrayList<Camp> availableCamps = viewCamps(allCamps);
    
        // Ask the user to select a camp
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the index of the camp you want to register for:");
    
        int campIndex;
        while (true) {
            try {
                campIndex = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                if (campIndex >= 0 && campIndex < availableCamps.size()) {
                    break; // Valid index, break the loop
                } else {
                    System.out.println("Invalid camp index. Please enter a valid index.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
    
        Camp selectedCamp = availableCamps.get(campIndex);

        //Check for date deadline
        LocalDate currentDate = LocalDate.now();
        if(!currentDate.isBefore(selectedCamp.getCampInfo().getRegClosingDate())){
            System.out.println("Cannot register for this camp as you have passed the deadline.");
            return;
        }

        //check for date clash
        if (!checkForDateClashes(selectedCamp)){ 
            System.out.println("Cannot register for this camp as there are date clash with your registered camp.");
            return;
        }

        //check if the student got withdraw from this camp anot
        if(bannedCamps.contains(selectedCamp)){
            System.out.println("Cannot register for this camp as you have withdrawed from this camp before.");
            return;
        }

        if (selectedCamp.getCampInfo().getCampCommitteeSlot() > 0 || selectedCamp.getRemainingAttendeeSlot() > 0) {
            System.out.println("What do you want to register as?");
            System.out.println("1) Attendee\n2) Camp Committee");
    
            int choice;
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume the newline character
                    if (choice == 1 || choice == 2) {
                        break; // Valid choice, break the loop
                    } else {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                    sc.next(); // Consume the invalid input to prevent an infinite loop
                }
            }
    
            boolean result = false;
    
            if (choice == 1) {//Register as Attendee
                if (selectedCamp.getRemainingAttendeeSlot() > 0) { //Check got slot anot
                    selectedCamp.addRegisteredStudents(this); //Update camp's Registered Student list
                    selectedCamp.setRemainingAttendeeSlot(selectedCamp.getRemainingAttendeeSlot() - 1);//Update Camp's remaining Attendee slot
                    this.registeredCamps.add(selectedCamp); //Update Student's resgistered Camp list
                    result = true;
                } else {
                    System.out.println("No more attendee slots!");
                }
            } else { //Register as committee
                if (selectedCamp.getCampInfo().getCampCommitteeSlot() > 0) {//Check got slot anot
                    if (this.isCampCommittee) {//student is already a committee for other camp
                        System.out.println("Cannot register as camp committee");
                        return;
                    }
                    CampCommittee campCommittee = new CampCommittee(super.getName(), selectedCamp);
                    selectedCamp.addRegisteredStudents(this); //Update camp's Registered Student list
                    selectedCamp.addRegisteredCampCommittee(campCommittee); //Update camp's Registered camp committee list
                    selectedCamp.getCampInfo().setCampCommitteeSlot(selectedCamp.getCampInfo().getCampCommitteeSlot() - 1);//Update Camp's remaining camp committee slot
                    this.registeredCamps.add(selectedCamp);//Update Student's resgistered Camp list
                    this.isCampCommittee = true;
                    this.committeeForCamp = selectedCamp;
                    result = true;
                } else {
                    System.out.println("No more camp committee slots!");
                }
            }
    
            if (result) {
                selectedCamp.getCampInfo().setTotalSlots(selectedCamp.getCampInfo().getCampCommitteeSlot() + selectedCamp.getRemainingAttendeeSlot()); //Update total Slot
                System.out.println("Successfully registered for camp: " + selectedCamp.getCampInfo().getCampName());
            }
        } else {
            // Handle the error
            System.out.println("Cannot register for camp as there is no more slot" + selectedCamp.getCampInfo().getCampName());
        }
    }
    
    public void withdrawFromCamp(ArrayList<Camp> allCamps) {
        // View registered camps
        this.viewRegisteredCamps();
    
        // Ask the user to select a camp to withdraw from
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the index of the camp you want to withdraw from:");
    
        int campIndex;
        while (true) {
            try {
                campIndex = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                if (campIndex >= 0 && campIndex < this.registeredCamps.size()) {
                    break; // Valid index, break the loop
                } else {
                    System.out.println("Invalid camp index. Please enter a valid index.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
    
        Camp selectedCamp = this.registeredCamps.get(campIndex);
    
        System.out.println("Are you sure you want to withdraw from the camp? (1 for Yes, 2 for No)");
    
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                if (choice == 1 || choice == 2) {
                    break; // Valid choice, break the loop
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
    
        if (choice == 1) {
            // Withdraw the student from the camp
            if (selectedCamp == this.committeeForCamp) { // Check if the student is the committee in the selected camp
                System.out.println("You are currently a camp committee for this camp, thus you cannot be withdrawn from this camp!");
                return;
            } else {
                System.out.println("You are currently an attendee for this camp, we will now proceed to withdraw you from this camp");
                this.registeredCamps.remove(selectedCamp);
                selectedCamp.getRegisteredStudents().remove(this);
                selectedCamp.setRemainingAttendeeSlot(selectedCamp.getRemainingAttendeeSlot() + 1);
            }
            this.bannedCamps.add(selectedCamp);
            selectedCamp.getCampInfo().setTotalSlots(selectedCamp.getCampInfo().getCampCommitteeSlot() + selectedCamp.getRemainingAttendeeSlot()); //Update total Slot
            System.out.println("Successfully withdrawn from camp: " + selectedCamp.getCampInfo().getCampName());
        } else {
            System.out.println("Withdrawal canceled.");
        }
    }
    
    public void makeEnquiries(ArrayList<Camp> allCamps) {
        enquiriesHandler.makeEnquiries(allCamps, this);
    }
    
    public ArrayList<Enquiries> viewEnquiries(){ //Return the available Enquiries that can be view by student
        return enquiriesHandler.viewEnquiries(this);
    }
    
    public void editEnquiry() {
        // edit an enquiry
        enquiriesHandler.editEnquiries(this);
    }
    
    public void deleteEnquiry() {
        enquiriesHandler.deleteEnquiry(this);
    }

    // Below are Getters and setters

    
    
    public boolean isCampCommittee() {
        return isCampCommittee;
    }

    public void setCampCommittee(boolean campCommittee) {
        isCampCommittee = campCommittee;
    }

    public ArrayList<Camp> getRegisteredCamps(){
        return this.registeredCamps;
    }

    public Camp getCommitteeForCamp() {
        return this.committeeForCamp;
    }

    public void setCommitteeForCamp(Camp committeeForCamp) {
        this.committeeForCamp = committeeForCamp;
    }

    public ArrayList<Enquiries> getEnquiriesList(){
        return this.enquiriesList;
    }
}
