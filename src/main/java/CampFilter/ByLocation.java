package CampFilter;

import java.util.ArrayList;
import java.util.Scanner;
import Camp.Camp;


public class ByLocation extends CampFilter{

    public ArrayList<Camp> applyCampFilter(ArrayList<Camp> campList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter desired location");
        String desiredLocation = sc.nextLine();
        int tries = 0, maxTries = 3;
        while (tries < maxTries) {
            if (desiredLocation.trim().isEmpty()) {
                System.out.println("Desired location cannot be blank. Please enter a valid desired location:");
                desiredLocation = sc.nextLine();
                tries++;
            } else {
                break;
            }
        }
        if (tries == maxTries) {
            System.out.println("Please try again later.");
            return null; // Return false if the user exceeds the maximum number of tries
        }

        ArrayList<Camp> filteredCamp = new ArrayList<>();

        // Case-insensitive comparison
        String desiredLocationLowerCase = desiredLocation.toLowerCase();
        for (Camp camp : campList) {
            if (camp.getCampInfo().getLocation().toLowerCase().equals(desiredLocationLowerCase)) {
                filteredCamp.add(camp);
            }
        }
        // Return the sorted copy
        ArrayList<Camp> filteredSortedCamps = super.sortByAlphabet(filteredCamp);
        return filteredSortedCamps;

    }
}
