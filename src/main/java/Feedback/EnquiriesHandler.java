package Feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Student.Student;

/**
 * Handles the creation, viewing, editing, and deletion of enquiries related to camps by students.
 */
public class EnquiriesHandler implements Serializable {

    /**
     * Allows a student to make an enquiry for a selected camp from the available camps list.
     *
     * @param allCamps The list of all available camps.
     * @param student  The student who is making the enquiry.
     */
    public void makeEnquiries(ArrayList<Camp> allCamps, Student student) {
        ArrayList<Camp> availableCamps = student.viewCamps(allCamps);
        if (availableCamps == null || availableCamps.isEmpty()) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        int campIndex = -1;
        int tries = 0;
        while (tries < maxTries) {
            System.out.println("Choose a camp to make enquiries:");
            try {
                campIndex = sc.nextInt();
                // Validate the camp index
                if (campIndex >= 1 && campIndex <= availableCamps.size()) {
                    break;
                } else {
                    System.out.println(
                            "Invalid camp index. Please enter a valid index within the range.");
                    tries++;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return;
        }
        sc.nextLine(); // Consume the newline character

        //Select the target Camp
        Camp selectedCamp = availableCamps.get(campIndex - 1);
        if (student.getCommitteeForCamp() != null && student.getCommitteeForCamp().getCampInfo().getCampName().equals(selectedCamp.getCampInfo().getCampName())) {
            System.out.println("You are the Camp Committee of the selected camp thus can't make enquiries.");
            return;
        }

        System.out.println("Type the enquiry:");
        String enquiryLine = sc.nextLine();
        Enquiries enquiry = new Enquiries(student.getUserID(), student.getName(), selectedCamp);
        tries = 0;
        while (tries < maxTries) {
            if (enquiryLine.trim().isEmpty()) {
                System.out.println("Enquiry cannot be blank. Please enter a valid enquiry:");
                enquiryLine = sc.nextLine();
                tries++;
            } else {
                break;
            }
        }
        if (tries >= maxTries) {
            System.out.println("Please try again later.");
            return; // Return if the user exceeds the maximum number of tries
        }

        enquiry.setEnquiryString(enquiryLine);
        student.getEnquiriesList().add(enquiry); // Store the enquiry inside the corresponding student
        selectedCamp.addEnquiriesList(enquiry); // Store the enquiry inside the corresponding camp
        System.out.println("You have successfully made an enquiry to camp " + selectedCamp.getCampInfo().getCampName());
        return;
    }

    /**
     * Displays the enquiries made by a student.
     *
     * @param student The student whose enquiries need to be viewed.
     * @return The list of enquiries made by the student.
     */
    public ArrayList<Enquiries> viewEnquiries(Student student) {
        ArrayList<Enquiries> enquiriesHolder = new ArrayList<>();
        int i = 0;
        ArrayList<Enquiries> enquiriesList = student.getEnquiriesList();
        if (enquiriesList == null || enquiriesList.isEmpty()) {
            System.out.println("NOT FOUND!");
            return null;
        }
        for (Enquiries enquiry : enquiriesList) {
            enquiriesHolder.add(enquiry);
            System.out.println((++i) + ") " + enquiry.toString());
        }
        return enquiriesHolder;
    }

    /**
     * Allows students to edit their previously submitted and unanswered enquiries.
     *
     * @param student  The student who is editing their enquiries.
     * @param allCamps The list of all available camps.
     */
    public void editEnquiries(Student student, ArrayList<Camp> allCamps) {
        // edit an enquiry
        Scanner sc = new Scanner(System.in);
        ArrayList<Enquiries> availableEnquiries;
        availableEnquiries = student.viewEnquiries();
        if(availableEnquiries == null || availableEnquiries.isEmpty()){
            System.out.println("You have not submitted any enquiry!");
            return;
        }
        int tries = 0, maxTries = 3;
        int index = -1; // Initialize index with an invalid value
        while(tries < maxTries){
            try{
                System.out.println("Choose which enquiry to edit");
                index = sc.nextInt(); // Get the index from the user
                 if (index - 1 >= 0 && index - 1 < availableEnquiries.size()){
                    break;
                 }else{
                    System.out.println("Invalid index. Please enter a valid index.");
                    tries++;
                 }

            }catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return;
        }

        sc.nextLine(); // Consume the newline character
        //Select the target enquiry
        Enquiries seletedEnquiry = availableEnquiries.get(index-1);

         if(seletedEnquiry.getProcessState()){
            System.out.println("Cannot edit the enquiry as it is replied");
            return;
        }
        //Remove the unchanged enquiry in all camps
        Camp targetCampInAllCamps = null;
        for(Camp camp  : allCamps){
            if(camp.getCampInfo().getCampName().equals(seletedEnquiry.getCamp().getCampInfo().getCampName())){
                targetCampInAllCamps = camp;
                break;
            }
        }
        ArrayList<Enquiries> enquiriesList = targetCampInAllCamps.getEnquiriesList();
        for(Enquiries enquiry : enquiriesList){
            if(enquiry.getEnquiryString().equals(seletedEnquiry.getEnquiryString()) && enquiry.getSenderId().equals(student.getUserID())){
                targetCampInAllCamps.getEnquiriesList().remove(enquiry);
                break;
            }
        }
        
        System.out.println("What would you like to change it to?");
        String enquiryString = sc.nextLine();
        tries = 0;
        while (tries < maxTries) {
            if (enquiryString.trim().isEmpty()) {
                System.out.println("Enquiry cannot be blank. Please enter a valid enquiry:");
                enquiryString = sc.nextLine();
                tries++;
            }
            else{
                break;
            }
        }

        if (tries >= maxTries) {
            System.out.println("Please try again later.");
            return; // Return if the user exceeds the maximum number of tries
        }

        seletedEnquiry.setEnquiryString(enquiryString.toString());
        //Update on "allCamps"
        targetCampInAllCamps.getEnquiriesList().add(seletedEnquiry);
        System.out.println("Enquiry updated!");
    }

    /**
     * Allows a student to delete their previously submitted and unanswered enquiries.
     *
     * @param student  The student who is deleting their enquiries.
     * @param allCamps The list of all available camps.
     */
    
    public void deleteEnquiry(Student student,ArrayList<Camp> allCamps) {
        // Delete an enquiry
        Scanner sc = new Scanner(System.in);
        ArrayList<Enquiries> availableEnquiries;
        availableEnquiries = student.viewEnquiries();
        if(availableEnquiries == null || availableEnquiries.isEmpty()){
            System.out.println("You have not submitted any enquiry!");
            return;
        }

        int tries = 0, maxTries = 3;
        int index = -1; // Initialize index with an invalid value
        while(tries < maxTries){
            try{
                System.out.println("Choose which enquiry to delete");
                index = sc.nextInt(); // Get the index from the user
                 if (index - 1 >= 0 && index - 1 < availableEnquiries.size()){
                    break;
                 }else{
                    System.out.println("Invalid index. Please enter a valid index.");
                    tries++;
                 }

            }catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return;
        }

        sc.nextLine(); // Consume the newline character
        //Select the target enquiry
        Enquiries selectedEnquiry = availableEnquiries.get(index-1);
        if(selectedEnquiry.getProcessState()){
            System.out.println("Cannot delete the enquiry as it is replied");
            return;
        }

        // Remove the enquiry on Student's end
        student.getEnquiriesList().remove(index - 1); 
        
        // Remove the enquiry on Camp's end
        Camp targetCampInAllCamps = null;
        for(Camp camp  : allCamps){
            if(camp.getCampInfo().getCampName().equals(selectedEnquiry.getCamp().getCampInfo().getCampName())){
                targetCampInAllCamps = camp;
                break;
            }
        }
        ArrayList<Enquiries> enquiriesList = targetCampInAllCamps.getEnquiriesList();
        for (Enquiries enquiry : enquiriesList) {
            if (enquiry.getEnquiryString().equals(selectedEnquiry.getEnquiryString()) &&
                    enquiry.getSenderId().equals(student.getUserID())) {
                targetCampInAllCamps.getEnquiriesList().remove(enquiry);
                break;
            }
        }
        System.out.println("Enquiry: " + selectedEnquiry.toString() + " is deleted");
    }
}
