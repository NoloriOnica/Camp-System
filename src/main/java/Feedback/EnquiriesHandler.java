package Feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Student.Student;

public class EnquiriesHandler implements Serializable{
    
    public void makeEnquiries(ArrayList<Camp> allCamps, Student student) {
        ArrayList<Camp> availableCamps = student.viewCamps(allCamps);
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
        Camp selectedCamp = availableCamps.get(campIndex-1);
        System.out.println("Type the enquiry:");
        String enquiryLine = sc.nextLine();
        Enquiries enquiry = new Enquiries(student.getName(), selectedCamp);
        enquiry.setEnquiryString(enquiryLine);
        student.getEnquiriesList().add(enquiry); // Store the enquiry inside the correspinding student
        selectedCamp.addEnquiriesList(enquiry); // Store the enquiry inside the corresponding camp
        System.out.println("You have sucessfully made an enquiry to camp " + selectedCamp.getCampInfo().getCampName());
        return;
    }

    public ArrayList<Enquiries> viewEnquiries(Student student) { //Return the enquiries List that can be seen by student
        ArrayList<Enquiries> enquiriesHolder = new ArrayList<>();
        int i = 0;
        ArrayList<Enquiries> enquiriesList = student.getEnquiriesList();
        if(enquiriesList == null || enquiriesList.isEmpty()){
            System.out.println("NOT FOUND!");
            return null;
        }
        for (Enquiries enquiry : enquiriesList) {
            enquiriesHolder.add(enquiry);
            System.out.println((++i) + ") " + enquiry.toString());
        }
        return enquiriesHolder;
    }

    public void editEnquiries(Student student) {
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
        Enquiries enquiry = availableEnquiries.get(index-1);
        if(enquiry.getProcessState()){
            System.out.println("Cannot edit the enquiry as it is replied");
            return;
        }
        
        System.out.println("What would you like to change it to?");
        String enquiryString = sc.nextLine();
        enquiry.setEnquiryString(enquiryString.toString());
        System.out.println("Enquiry updated!");
    }

    public void deleteEnquiry(Student student) {
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
        Camp camp = selectedEnquiry.getCamp();
        ArrayList<Enquiries> enquiriesList = camp.getEnquiriesList();
        for (Enquiries enquiry : enquiriesList) {
            if (enquiry.getEnquireString().equals(selectedEnquiry.getEnquireString()) &&
                    enquiry.getSenderName().equals(student.getName())) {
                camp.getEnquiriesList().remove(enquiry);
                break;
            }
        }
        System.out.println("Enquiry: " + selectedEnquiry.toString() + " is deleted");
    }
}
