package Report;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;
import Login.User;

import Camp.Camp;

public class GenerateReport implements Serializable{
    // Function to generate any report based on the provided ReportGenerator

    public boolean reportSelection(ArrayList<Camp> campList, User user){
        ArrayList<Camp> filteredCamps = null;
        System.out.println("Enter what report you want to generate: ");
        System.out.println("1)Camp Report\n2)Enquiry Report");
        if(user.getUserType().equalsIgnoreCase("t")){
            System.out.println("3)Performance Report");
        }
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        int tries = 0;
        int choice = 0;
        while (tries < maxTries) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        filteredCamps = this.filterSelection(campList);
                        if(filteredCamps == null){
                            return false;
                        }
                        this.generate(new CampReport(), filteredCamps);
                        break;
                    case 2:
                        this.generate(new EnquiryReport(), campList);
                        break;
                    case 3:
                        if(user.getUserType().equalsIgnoreCase("t")){
                            filteredCamps = this.filterSelection(campList);
                            if(filteredCamps == null){
                                return false;
                            }
                            this.generate(new PerformanceReport(), filteredCamps);
                        }
                        else{
                            System.out.println("Invalid choice.");
                            tries ++;
                            continue;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        tries++;
                        break;
                }

                // Break out of the loop if the choice was valid
                if (choice >= 1 && choice <= 3) {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return false;
        }
        return true;
    }

    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        System.out.println("Enter what filter you want to apply for generating Camp Report: ");
        System.out.println("1)Attendee\n2)Camp Committee\n3)None");
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        int tries = 0;
        int choice = 0;
        while (tries < maxTries) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        filteredCamps = filter(new FilterAttendee(), campList);
                        break;
                    case 2:
                        filteredCamps = filter(new FilterCampCommittee(), campList);
                        break;
                    case 3:
                        filteredCamps = campList;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        tries++;
                        break;
                }

                // Break out of the loop if the choice was valid
                if (choice >= 1 && choice <= 3) {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        return filteredCamps;
    }
    
    private void generate(ReportGenerator reportGenerator, ArrayList<Camp> availableCamps) {
        reportGenerator.generateReport(availableCamps);
    }

    private ArrayList<Camp> filter(ReportFilter reportFilter, ArrayList<Camp> availableCamps){
        return reportFilter.applyFilter(availableCamps);
    }
}