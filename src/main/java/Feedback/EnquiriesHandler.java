package Feedback;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

import Camp.Camp;
import Student.Student;

public class EnquiriesHandler implements Serializable{
    
    public void makeEnquiries(ArrayList<Camp> allCamps, Student student) {
        ArrayList<Camp> availableCamps = student.viewCamps(allCamps);
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        int tries = 0;
        while (tries < maxTries) {
            System.out.println("Choose a camp to make enquiries:");

            try {
                int campIndex = sc.nextInt();

                // Validate the camp index
                if (campIndex >= 1 && campIndex <= availableCamps.size()) {
                    campIndex--; // Adjust the index to match the list index

                    sc.nextLine(); // Consume the newline character left by nextInt()
                    System.out.println("Type the enquiry:");
                    String enquiryLine = sc.nextLine();

                    // Make enquiries and break out of the loop on success
                    Enquiries enquiry = new Enquiries(student.getName());
                    enquiry.setEnquiry(enquiryLine);
                    student.getEnquiriesList().add(enquiry); // Store the enquiry inside the correspinding student
                    availableCamps.get(campIndex).addEnquiriesList(enquiry); // Store the enquiry inside the
                                                                             // corresponding camp
                    return;
                } else {
                    System.out.println(
                            "Invalid camp choice. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the camp index.");
                if (tries < maxTries) {
                    System.out.println("Please try again.");
                } else {
                    System.out.println("Max tries reached. Exiting...");
                }
            }
        }
    }

    public ArrayList<Enquiries> viewEnquiries(Student student) {
        ArrayList<Enquiries> enquiriesHolder = new ArrayList<>();
        int i = 0;
        ArrayList<Enquiries> enquiriesList = student.getEnquiriesList();
        if(enquiriesList == null) return null;
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
        if(availableEnquiries == null){
            System.out.println("You have not submitted any enquiry!");
            return;
        }
        System.out.println("Choose which enquiry to edit");
        int index = -1; // Initialize index with an invalid value
        boolean validIndexEntered = false;

        while (!validIndexEntered) {
            System.out.println("Which suggestion do you want to edit?");
            if (sc.hasNextInt()) {
                index = sc.nextInt(); // Get the index from the user
                sc.nextLine(); // Consume the newline character
                if (index - 1 >= 0 && index - 1 < availableEnquiries.size()) {
                    validIndexEntered = true; // Valid index, break the loop
                } else {
                    System.out.println("Invalid index. Please try again with a valid index.");
                }
            } else {
                System.out.println("That's not a number. Please enter a number corresponding to the suggestion index.");
                sc.nextLine(); // Consume the non-integer input
            }
        }
        System.out.println("What would you like to change it to? (type 'END' to finish)");
        StringBuilder enquiry = new StringBuilder();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if ("END".equals(line)) {
                break;
            }
            enquiry.append(line).append("\n");
        }
        Enquiries selectedEnquiry = availableEnquiries.get(index - 1);
        availableEnquiries.get(index-1).setEnquiry(enquiry.toString());
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
