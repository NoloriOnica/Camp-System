package Staff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import CampFilter.ByDate;
import CampFilter.ByLocation;
import CampFilter.CampFilter;

public class StaffViewsCamps implements Serializable{
    private CampFilter campFilter;

    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        int choice = 0, index = 0, maxTries = 3;
        Scanner sc = new Scanner(System.in);
        int tries;
        for (tries = 0; tries < maxTries; tries++) {
            try {
                System.out.println("Do you want to filter to view Camps?");
                System.out.println("1) Yes\n2) No");
                index = sc.nextInt();

                if (index == 2) {
                    choice = 3;
                    break; // Exit the loop if the input is valid
                } else if (index == 1) {
                    break; // Exit the loop if the input is valid
                } else {
                    System.out.println("Invalid Choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Consume the invalid input
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
            // Handle accordingly, for example, return null or perform some other action.
        }

        sc.nextLine();// consume next line
        if (index != 2) {
            for (tries = 0; tries < maxTries; tries++) {
                try {
                    System.out.println("Choose the filter types?");
                    System.out.println("1) By Date Before Registration Closed Date\n2) By Location");
                    choice = sc.nextInt();

                    if (choice == 2 || choice == 1) {
                        break; // Exit the loop if the input is valid
                    } else {
                        System.out.println("Invalid Choice. Try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.nextLine(); // Consume the invalid input
                }
            }

            if (tries == maxTries) {
                System.out.println("You've reached the maximum number of tries. Please try again later.");
                return null;
                // Handle accordingly, for example, return null or perform some other action.
            }
        }

        switch (choice) {
            case 1:
                this.campFilter = new ByDate();
                filteredCamps = this.campFilter.applyCampFilter(campList);
                break;
            case 2:
                this.campFilter = new ByLocation();
                filteredCamps = this.campFilter.applyCampFilter(campList);
                break;
            case 3:
                filteredCamps = CampFilter.sortByAlphabet(campList);
                break;
            default:
                System.out.println("Invalid Choice");
                return null;
        }
        return filteredCamps;
    }

    public void viewCamps(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()) {
            System.out.println("NOT FOUND");
            return;
        }
        int i = 1;
        for (Camp camp : filteredSortedCamps) {
            System.out.println((i++) + ") Camp: " + camp.getCampInfo().getCampName() + ", Start Date: "
                    + camp.getCampInfo().getStartDate()
                    + ", Location: " + camp.getCampInfo().getLocation() + ", Created by Staff "
                    + camp.getCampInfo().getStaffInChargeID());
        }
    }

    public ArrayList<Camp> viewOwnCamps(ArrayList<Camp> campList) { //return a list of sortedfiltercamp
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if (filteredSortedCamps == null || filteredSortedCamps.isEmpty()) {
            System.out.println("NOT FOUND");
            return null;
        }
        int i = 1;
        for (Camp camp : filteredSortedCamps) {
            System.out.println((i++) + ") Camp: " + camp.getCampInfo().getCampName() + ", Start Date: "
                    + camp.getCampInfo().getStartDate()
                    + ", Location: " + camp.getCampInfo().getLocation() + ", Created by Staff "
                    + camp.getCampInfo().getStaffInChargeID());
        }

        return filteredSortedCamps;
    }
}