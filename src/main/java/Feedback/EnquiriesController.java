package Feedback;
import java.util.*;

import Camp.Camp;

public class EnquiriesController {

    public void viewEnquiries(ArrayList<Camp> camps) {
        for (Camp camp : camps) {
            ArrayList<Enquiries> enquiries = camp.getEnquiriesList();
            System.out.println("\n" + camp.getCampInfo().getCampName());
            for (Enquiries enquiry : enquiries) {
                System.out.println(enquiry.toString());
            }
        }
    }

    public boolean replyEnquiries(ArrayList<Camp> camps) {
        int maxTries = 3;
        int tries = 0;

        while (tries < maxTries) {
            System.out.println("Which camp do you want to reply to?");

            try {
                Scanner sc = new Scanner(System.in);

                // Display camps and their indices
                for (int i = 0; i < camps.size(); i++) {
                    System.out.println((i + 1) + ". " + camps.get(i).getCampInfo().getCampName());
                }

                int campChoice = sc.nextInt() - 1;

                if (campChoice >= 0 && campChoice < camps.size()) {
                    Camp selectedCamp = camps.get(campChoice);

                    ArrayList<Enquiries> enquiries = selectedCamp.getEnquiriesList();

                    if (enquiries.isEmpty()) {
                        System.out.println(
                                "No enquiries found for " + selectedCamp.getCampInfo().getCampName() + ". Please select another camp.");
                        continue; // Restart the loop to allow the user to choose another camp
                    }
                    System.out.println("Choose an enquiry to reply (Enter alphabet):");
                    for (int i = 0; i < enquiries.size(); i++) {
                        System.out.println((char) ('A' + i) + ". " + enquiries.get(i).toString());
                    }

                    char enquiryChoice = sc.next().toUpperCase().charAt(0);

                    // Validate the input
                    if (enquiryChoice >= 'A' && enquiryChoice < 'A' + enquiries.size()) {
                        int index = enquiryChoice - 'A';

                        if (enquiries.get(index).getProcessState()) {
                            System.out.println("That enquiry sent by " + enquiries.get(index).getSenderName()
                                    + " is ALREADY replied.");
                        } else {
                            System.out.println("Enter the reply:");
                            String reply = sc.nextLine();
                            enquiries.get(index).setReply(reply);
                            enquiries.get(index).setProcessState(true);
                            System.out.println(
                                    "That enquiry sent by " + enquiries.get(index).getSenderName() + " is replied.");
                        }
                        return true; // End the function after successful reply
                    } else {
                        System.out.println("Invalid choice. Please enter a valid alphabet within the range.");
                    }
                } else {
                    System.out.println("Invalid camp choice. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }

            tries++;

            if (tries < maxTries) {
                System.out.println("Try again.");
            } else {
                System.out.println("Try again later.");
            }
        }
        return false;
    }

}
