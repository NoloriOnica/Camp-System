package CampFilter;

import java.util.ArrayList;
import java.util.Scanner;
import Camp.Camp;

/**
 * Class to filter camps by a specified location.
 */
public class ByLocation extends CampFilter {

    /**
     * Apply a filter to the camp list based on a specified location.
     *
     * @param campList The list of camps to be filtered.
     * @return An ArrayList of camps filtered by the specified location and sorted alphabetically.
     */
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
            return null; 
        }

        ArrayList<Camp> filteredCamp = new ArrayList<>();

        // Case-insensitive comparison
        String desiredLocationLowerCase = desiredLocation.toLowerCase();
        for (Camp camp : campList) {
            if (camp.getCampInfo().getLocation().toLowerCase().equals(desiredLocationLowerCase)) {
                filteredCamp.add(camp);
            }
        }

        ArrayList<Camp> filteredSortedCamps = super.sortByAlphabet(filteredCamp);
        return filteredSortedCamps;
    }
}
