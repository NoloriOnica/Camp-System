package Staff;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Report.CampsFilter;
import Report.CampsFilterImplementation;
import Camp.Camp;

public class StaffViewsCamps implements Serializable{
    private CampsFilter campsFilter;

    public StaffViewsCamps() {
        campsFilter = new CampsFilterImplementation();
    }

    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        int choice = 0, index, maxTries = 3;
        Scanner sc = new Scanner(System.in);
        for (int tries = 0; tries < maxTries; tries++) {
            System.out.println("Do you want to filter to view Camps?");
            System.out.println("1) Yes\n2) No");
            index = sc.nextInt();

            if (index == 2) {
                choice = 3;
                break; // Exit the loop if the input is valid
            } else if (index == 1) {
                System.out.println("Choose the filter types?");
                System.out.println("1) By Date Before\n2) By Location");
                choice = sc.nextInt();
                break; // Exit the loop if the input is valid
            } else {
                System.out.println("Invalid Choice. Try again.");
            }
        }

        if (choice == 0) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
            // Handle accordingly, for example, return null or perform some other action.
        }

        sc.nextLine();// consume next line
        switch (choice) {
            case 1:
                int tries = 0;
                LocalDate desiredDate = null;

                while (tries < maxTries) {
                    try {
                        System.out.println("Enter Date (YYYY-MM-DD):");
                        desiredDate = LocalDate.parse(sc.nextLine());
                        // Additional checks if needed
                        break; // If the input is valid, break out of the loop
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                        tries++;
                    }
                }
                if (tries == maxTries) {
                    System.out.println("You've reached the maximum number of tries. Please try again later.");
                    return null;
                }
                filteredCamps = this.campsFilter.byDate(campList, desiredDate);
                break;
            case 2:
                System.out.println("Enter desired Location");
                String desiredLocation = sc.nextLine();
                filteredCamps = this.campsFilter.byLocation(campList, desiredLocation);
                break;
            case 3:
                filteredCamps = this.campsFilter.sortByAlphabet(campList);
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

    public void viewOwnCamps(ArrayList<Camp> campList) {
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
}