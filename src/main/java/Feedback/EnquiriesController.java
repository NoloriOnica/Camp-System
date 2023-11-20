package Feedback;
import java.io.Serializable;
import java.util.*;

import Camp.Camp;

public class EnquiriesController implements Serializable {
    int count;

    public void viewEnquiries(ArrayList<Camp> camps) {
        if (camps.isEmpty()) {
            System.out.println();
            System.out.println("No Enquiries made to any camp as you have not created any camp");
            System.out.println("Returning to main menu ...");
            return;
        }
        for (Camp camp : camps) {
            ArrayList<Enquiries> enquiries = camp.getEnquiriesList();
            System.out.println("\n" + camp.getCampInfo().getCampName());
            if (enquiries.isEmpty()) {
                System.out.println("No Enquiries made for " + camp.getCampInfo().getCampName());
            }
            for (Enquiries enquiry : enquiries) {
                System.out.println(enquiry.toString());
            }
        }
    }

    public boolean replyEnquiries(ArrayList<Camp> camps, String replierName) {
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        
        for (int tries = 0; tries < maxTries; tries++) {
            if(camps.isEmpty()) {
                System.out.println();//Message for staff
                System.out.println("No enquiries made to any camp as you have not created any camp, please check again later");
                System.out.println("Exiting to main menu.....");
                return false;
            }
            System.out.println("Which camp do you want to reply to? (Enter number or 0 to exit):");
            
            // Display camps and their indices
            for (int i = 0; i < camps.size(); i++) {
                System.out.println((i + 1) + ". " + camps.get(i).getCampInfo().getCampName());
            }
            System.out.println("0. Exit to main menu");

            try {
                int campChoice = sc.nextInt() - 1;
                
                if (campChoice == -1) {
                    System.out.println("Exiting to main menu...");
                    return false;
                }
                
                if (campChoice >= 0 && campChoice < camps.size()) {
                    Camp selectedCamp = camps.get(campChoice);
                    ArrayList<Enquiries> enquiries = selectedCamp.getEnquiriesList();
                    
                    if (enquiries.isEmpty()) {
                        System.out.println("No enquiries found for " + selectedCamp.getCampInfo().getCampName() + ". Please select another camp.");
                        continue;
                    }

                    System.out.println("Choose an enquiry to reply (Enter alphabet):");
                    for (int i = 0; i < enquiries.size(); i++) {
                        System.out.println((char) ('A' + i) + ". " + enquiries.get(i).toString());
                    }

                    char enquiryChoice = sc.next().toUpperCase().charAt(0);

                    if (enquiryChoice >= 'A' && enquiryChoice < 'A' + enquiries.size()) {
                        int index = enquiryChoice - 'A';

                        if (enquiries.get(index).getProcessState()) {
                            System.out.println("That enquiry sent by " + enquiries.get(index).getSenderName() + " is ALREADY replied.");
                            continue;
                        }
                        
                        sc.nextLine(); // Consume the rest of the line
                        System.out.println("Enter the reply:");
                        String reply = sc.nextLine();
                        
                        while(tries < maxTries) {
                            if (reply.trim().isEmpty()) {
                                System.out.println("Reply cannot be blank: ");
                                reply = sc.nextLine();
                                tries++;
                            }else{
                                break;
                            }
                          }
                          if (tries >= maxTries) {
                              System.out.println("Please try again later.");
                              return false; // Return false if the user exceeds the maximum number of tries
                          }
                        Enquiries enquiry = enquiries.get(index);
                        selectedCamp.getEnquiriesList().remove(index);
                        enquiry.setReply(reply);
                        enquiry.setProcessState(true);
                        enquiry.setReplierName(replierName);
                        selectedCamp.getEnquiriesList().add(enquiry);
                        System.out.println("Enquiry replied to successfully.");
                        return true;
                    } else {
                        System.out.println("Invalid choice. Please enter a valid alphabet within the range.");
                    }
                } else {
                    System.out.println("Invalid camp choice. Please enter a valid index within the range.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Clear the buffer
            }

            count = maxTries - tries - 1;
            if (count > 0) {
                System.out.println("Try again. " + count + " tries remaining.");
            }
        }
        System.out.println("Exiting to main menu...");
        return false;
    }
}
