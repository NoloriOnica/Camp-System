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
                campIndex = sc.nextInt() - 1;
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
        Camp selectedCamp = availableCamps.get(campIndex);
        System.out.println("Type the enquiry:");
        String enquiryLine = sc.nextLine();
        Enquiries enquiry = new Enquiries(student.getName());
        enquiry.setEnquiry(enquiryLine);
        student.getEnquiriesList().add(enquiry); // Store the enquiry inside the correspinding student
        selectedCamp.addEnquiriesList(enquiry); // Store the enquiry inside the corresponding camp
        System.out.println("You have sucessfully made an enquiry to camp " + selectedCamp.getCampInfo().getCampName());
        return;
    }

    public ArrayList<Enquiries> viewEnquiries(Student student) { //Return the enquiries List that can be seen by student
        ArrayList<Enquiries> enquiriesHolder = new ArrayList<>();
        int i = 0;
        ArrayList<Enquiries> enquiriesList = student.getEnquiriesList();
        if(enquiriesList == null || enquiriesList.isEmpty()) return null;
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
        enquiry.setEnquiry(enquiryString.toString());
        System.out.println("Enquiry updated!");
    }

    public void deleteEnquiry(Student student) {
        // Delete an enquiry
        Scanner sc = new Scanner(System.in);
        ArrayList<Enquiries> availableEnquiries;
        availableEnquiries = student.viewEnquiries();
        if(availableEnquiries == null){
            System.out.println("You have not submitted any enquiry!");
            return;
        }
        System.out.println("Choose which enquiry to delete:");
        int index = -1; // Initialize index with an invalid value
        boolean validIndexEntered = false;

        while (!validIndexEntered) {
            System.out.println("Which enquiry do you want to delete?");
            if (sc.hasNextInt()) {
                index = sc.nextInt(); // Get the index from the user
                sc.nextLine(); // Consume the newline character
                if (index - 1 >= 0 && index - 1 < availableEnquiries.size()) {
                    validIndexEntered = true; // Valid index, break the loop
                } else {
                    System.out.println("Invalid index. Please try again with a valid index.");
                }
            } else {
                System.out.println("That's not a number. Please enter a number corresponding to the enquiry index.");
                sc.nextLine(); // Consume the non-integer input
            }
        }

        Enquiries deletedEnquiry = availableEnquiries.remove(index - 1);
        student.getEnquiriesList().remove(index - 1); // Remove the enquiry on Student's end
        // Remove the enquiry on Camp's end
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
        for (Camp camp : registeredCamps) {
            if (camp.getEnquiriesList().contains(deletedEnquiry)) {
                camp.getEnquiriesList().remove(deletedEnquiry);
                System.out.println("Enquiry: " + deletedEnquiry.toString() + " is deleted from camp: "
                        + camp.getCampInfo().getCampName());
                break; // Exit the loop after deleting the enquiry from the camp
            }
        }
        System.out.println("Enquiry: " + deletedEnquiry.toString() + " is deleted");
    }
}
