package Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Login.User;
import Camp.Camp;
import CampCommittee.CampCommittee;
import Feedback.Enquiries;
import Feedback.EnquiriesHandler;


/**
 * Represents a student with registration and camp-related functionalities.
 */
public class Student extends User implements Serializable {
    private boolean isCampCommittee;
    private Camp committeeForCamp; // field to store the camp for which the student is a committee
    private ArrayList<Camp> registeredCamps;
    private ArrayList<Enquiries> enquiriesList; // Different from camp committee's suggestion, a student can send enquiries to multiple camps
    private StudentViewsCamps studentViewsCamps;
    private EnquiriesHandler enquiriesHandler;
    private final static int MAX_TRIES = 3;

    public Student(String userID, String name, String email, String faculty, String userType) {
        super(userID, name, email, faculty, userType);
        this.registeredCamps = new ArrayList<>();
        this.enquiriesList = new ArrayList<>();
        this.isCampCommittee = false;
        studentViewsCamps = new StudentViewsCamps();
        enquiriesHandler = new EnquiriesHandler();
    }

    /**
     * Checks for date clashes between the new camp and already registered camps.
     *
     * @param newCamp The camp to check for date clashes.
     * @return True if there are no date clashes, otherwise False.
     */
    
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
    
    /**
     * Displays the camps registered by the student.
     *
     * @return A list of registered camps by the student.
     */
    
    public ArrayList<Camp> viewRegisteredCamps() {
        return studentViewsCamps.viewRegisteredCamps(this);
    }

    /**
     * Views the camps available for the student to register.
     *
     * @param allCamps The list of all available camps.
     * @return The list of camps available for the student to view.
     */
    
    public ArrayList<Camp> viewCamps(ArrayList<Camp> allCamps) { // Return a list a camp's object that can be viewed by
                                                                 // this student
        return studentViewsCamps.viewCamps(allCamps, this);
    }

    /**
     * Views the available camp slots for the student.
     *
     * @param allCamps The list of all available camps.
     */
    
    public void viewCampSlots(ArrayList<Camp> allCamps) {
        studentViewsCamps.viewCampSlots(allCamps, this);
    }

    /**
     * Registers the student for a camp.
     *
     * @param allCamps The list of all available camps.
     */
    public void registerForCamp(ArrayList<Camp> allCamps) {
        // View available camps
        ArrayList<Camp> availableCamps = viewCamps(allCamps);
        if(availableCamps == null || availableCamps.isEmpty()){
            System.out.println("There are no available camps.");
            return;
        }
            
        Scanner sc = new Scanner(System.in);
        int tries;
        int campIndex = 0;
        // Ask user to select a camp
        tries = 0;
        while (tries < MAX_TRIES) {
            try {
                System.out.println("Enter the index of the camp you want to register for:");
                campIndex = sc.nextInt() - 1;
                sc.nextLine(); // Consume the newline character

                if (campIndex >= 0 && campIndex < availableCamps.size()) {
                    break; // Valid index, break the loop
                } else {
                    System.out.println("Invalid camp index. Please enter a valid index.");
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
            return;
        }

        Camp selectedCamp = availableCamps.get(campIndex);

        // check if the student is already registered for the selected camp
        for (Camp registerCamp : this.registeredCamps) {
            if (registerCamp.getCampInfo().getCampName().equals(selectedCamp.getCampInfo().getCampName())) {
                System.out.println("You already registered for this camp!");
                return;
            }
        }

        // Check for date deadline
        LocalDate currentDate = LocalDate.now();
        if (!currentDate.isBefore(selectedCamp.getCampInfo().getRegClosingDate())) {
            System.out.println("Cannot register for this camp as you have passed the deadline.");
            return;
        }

        // check for date clash
        if (!checkForDateClashes(selectedCamp)) {
            System.out.println("Cannot register for this camp as there are date clash with your registered camp.");
            return;
        }

        // check if the student got withdraw from this camp anot
        ArrayList<Student> bannedStudentList = selectedCamp.getBannedStudents();
        for(Student student : bannedStudentList){
            if(student.getUserID().equals(super.getUserID())){
                System.out.println("Cannot register for this camp as you withdrawn from this camp before.");
                selectedCamp.getBannedStudents().remove(student);
                selectedCamp.getBannedStudents().add(this);
                return;
            }
        }

        if (selectedCamp.getCampInfo().getCampCommitteeSlot() > 0 || selectedCamp.getRemainingAttendeeSlot() > 0) {
            int choice = 0;
            tries = 0;

            System.out.println("What do you want to register as?");
            System.out.println("1) Attendee\n2) Camp Committee");
            while (tries < MAX_TRIES) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume the newline character

                    if (choice == 1 || choice == 2) {
                        // Valid choice, break the loop
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                    sc.next(); // Consume the invalid input to prevent an infinite loop
                }

                tries++;
            }

            if (tries == MAX_TRIES) {
                System.out.println("You've reached the maximum number of tries. Please try again later.");
                // Handle the case where the user exceeds the maximum number of tries
            }

            boolean result = false;

            if (choice == 1) {// Register as Attendee
                if (selectedCamp.getRemainingAttendeeSlot() > 0) { // Check got slot anot
                    selectedCamp.addRegisteredStudents(this); // Update camp's Registered Student list
                    selectedCamp.setRemainingAttendeeSlot(selectedCamp.getRemainingAttendeeSlot() - 1);// Update Camp's remaining Attendee slot
                    this.registeredCamps.add(selectedCamp); // Update Student's resgistered Camp list
                    result = true;
                } else {
                    System.out.println("No more attendee slots!");
                }
            } else { // Register as committee
                if (selectedCamp.getCampInfo().getCampCommitteeSlot() > 0) {// Check got slot anot
                    if (this.isCampCommittee) {// student is already a committee for other camp
                        System.out.println(
                                "Cannot register as camp committee as you are a camp committee for other camp");
                        return;
                    }
                    CampCommittee campCommittee = new CampCommittee(super.getUserID(),super.getName(),super.getEmail(),super.getFaculty(),super.getUserType(), selectedCamp); //first time correct
                    selectedCamp.addRegisteredStudents(this); // Update camp's Registered Student list
                    selectedCamp.addRegisteredCampCommittee(campCommittee); // Update camp's Registered camp committee
                                                                            // list
                    selectedCamp.getCampInfo()
                            .setCampCommitteeSlot(selectedCamp.getCampInfo().getCampCommitteeSlot() - 1);// Update Camp's remaining camp committee slot
                    this.registeredCamps.add(selectedCamp);// Update Student's resgistered Camp list
                    this.isCampCommittee = true;
                    this.committeeForCamp = selectedCamp;
                    //the camp which is added into this.registered, and then  saved later, the camp committee is not
                    //saved
                    result = true;
                } else {
                    System.out.println("No more camp committee slots!");
                }
            }

            if (result) {
                selectedCamp.getCampInfo().setTotalSlots(
                        selectedCamp.getCampInfo().getCampCommitteeSlot() + selectedCamp.getRemainingAttendeeSlot()); // Update
                                                                                                                      // total
                if(choice == 1)                                                                                           // Slot
                    System.out.println("Successfully registered for camp: " + selectedCamp.getCampInfo().getCampName() + " as a attendee!");
                else
                    System.out.println("Successfully registered for camp: " + selectedCamp.getCampInfo().getCampName() + " as a camp committee!");
            }
        } else {
            // Handle the error
            System.out.println(
                    "Cannot register for camp as there is no more slot" + selectedCamp.getCampInfo().getCampName());
        }
    }

    /**
     * Withdraws the student from a registered camp.
     *
     * @param allCamps The list of all available camps.
     */
    
    public void withdrawFromCamp(ArrayList<Camp> allCamps) {
    	Scanner sc = new Scanner(System.in);
    	int tries;             
    	int campIndex = 0;
       
        // View registered camps
        if(this.registeredCamps==null || this.registeredCamps.isEmpty()){
            System.out.println("You have not registered for any camp!");
            return;
        }

        ArrayList<Camp> filteredSortedCmaps = this.viewRegisteredCamps();
        tries = 0;
        while (tries < MAX_TRIES) {
            try {        
		        // Ask the user to select a camp to withdraw from
		        System.out.println("Enter the index of the camp you want to withdraw from:");
                campIndex = sc.nextInt() - 1;
                sc.nextLine(); // Consume the newline character
                
                if (campIndex >= 0 && campIndex < filteredSortedCmaps.size()) {
                    break; // Valid index, break the loop
                } 
                else {
                    System.out.println("Invalid camp index. Please enter a valid index.");
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
            return;
        }
        Camp selectedCamp = filteredSortedCmaps.get(campIndex);

        System.out.println("Are you sure you want to withdraw from the camp? (1 for Yes, 2 for No)");

        int choice = 0;
        while (tries < MAX_TRIES) {
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                if (choice == 1 || choice == 2) {
                    break; // Valid choice, break the loop
                } 
                else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    tries++;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        
        if (choice == 1) {
            // Withdraw the student from the camp
            if (selectedCamp == this.committeeForCamp) { // Check if the student is the committee in the selected camp
                System.out.println(
                        "You are currently a camp committee for this camp, thus you cannot be withdrawn from this camp!");
                return;
            } else {
                System.out.println(
                        "You are currently an attendee for this camp, we will now proceed to withdraw you from this camp");
                // Update on student's end
                this.registeredCamps.remove(selectedCamp);
                // update on Camp's end
                ArrayList<Student> studentList = selectedCamp.getRegisteredStudents();
                for(Student student : studentList){
                    if(student.getUserID().equals(super.getUserID())){
                        selectedCamp.getRegisteredStudents().remove(student);
                        break;
                    }
                }
                selectedCamp.setRemainingAttendeeSlot(selectedCamp.getRemainingAttendeeSlot() + 1);
                selectedCamp.addBannedStudents(this);
                selectedCamp.getCampInfo().setTotalSlots(
                selectedCamp.getCampInfo().getCampCommitteeSlot() + selectedCamp.getRemainingAttendeeSlot()); // Update total slot
                // Update on "allCamp's" end
                for (Camp camp : allCamps) {
                    if (camp.getCampInfo().getCampName().equals(selectedCamp.getCampInfo().getCampName())) {
                        allCamps.remove(camp);
                        allCamps.add(selectedCamp);
                        break;
                    }
                }
                System.out.println("Successfully withdrawn from camp: " + selectedCamp.getCampInfo().getCampName());
            }
        } else {
            System.out.println("Withdrawal canceled.");
        }
        
    }
    
    /**
     * Views the camp committee camp the student is a part of.
     */
    
    public void viewCampCommitteeCamp() {
    	if (!this.isCampCommittee) {
    		System.out.println("You are not a camp committee member in any camps!!");
    	}
    	else {
    		 System.out.println("Registered Camp Name: " + this.getCommitteeForCamp().getCampInfo().getCampName() + ", Role: Camp Committee,"+ " Start Date: "+ this.getCommitteeForCamp().getCampInfo().getStartDate() + ", Location: "+ this.getCommitteeForCamp().getCampInfo().getLocation());
    	}
    }

    /**
     * Allows the student to make enquiries about camps.
     *
     * @param allCamps The list of all available camps.
     */
    
    public void makeEnquiries(ArrayList<Camp> allCamps) {
        enquiriesHandler.makeEnquiries(allCamps, this);
    }

    /**
     * Views the enquiries made by the student.
     *
     * @return The list of enquiries made by the student.
     */
    
    public ArrayList<Enquiries> viewEnquiries() { // Return the available Enquiries that can be view by student
        return enquiriesHandler.viewEnquiries(this);
    }

    /**
     * Edits an existing enquiry made by the student.
     *
     * @param allCamps The list of all available camps.
     */
    
    public void editEnquiry(ArrayList<Camp> allCamps) {
        // edit an enquiry
        enquiriesHandler.editEnquiries(this,allCamps);
    }

    /**
     * Deletes an enquiry made by the student.
     *
     * @param allCamps The list of all available camps.
     */
    
    public void deleteEnquiry(ArrayList<Camp> allCamps) {
        enquiriesHandler.deleteEnquiry(this,allCamps);
    }

    // Below are Getters and setters

    
    /**
     * Checks whether the student is part of a camp committee.
     *
     * @return True if the student is part of a camp committee, otherwise False.
     */
    public boolean isCampCommittee() {
        return isCampCommittee;
    }

    
    /**
     * Sets whether the student is part of a camp committee.
     *
     * @param campCommittee True if the student is part of a camp committee, otherwise False.
     */
    public void setCampCommittee(boolean campCommittee) {
        isCampCommittee = campCommittee;
    }

    
    /**
     * Retrieves a list of camps in which the student is registered.
     *
     * @return The list of camps in which the student is registered.
     */
    public ArrayList<Camp> getRegisteredCamps() {
        return this.registeredCamps;
    }

    
    /**
     * Retrieves the camp for which the student is a committee member.
     *
     * @return The camp for which the student is a committee member.
     */
    public Camp getCommitteeForCamp() {
        return this.committeeForCamp; //not null upon restarting
    }

    
    /**
     * Sets the camp for which the student is a committee member.
     *
     * @param committeeForCamp The camp for which the student is a committee member.
     */
    public void setCommitteeForCamp(Camp committeeForCamp) {
        this.committeeForCamp = committeeForCamp;
    }
    
    /**
     * Retrieves a list of enquiries made by the student.
     *
     * @return The list of enquiries made by the student.
     */

    public ArrayList<Enquiries> getEnquiriesList() {
        return this.enquiriesList;
    }
}
