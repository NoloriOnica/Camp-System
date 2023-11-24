package CampFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import Camp.Camp;

public class ByDate extends CampFilter{

    public ArrayList<Camp> applyCampFilter(ArrayList<Camp> campList){
        Scanner sc = new Scanner(System.in);
        int tries = 0, maxTries = 3;
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
        ArrayList<Camp> filteredCamp = new ArrayList<>();
        for (Camp camp : campList) {
            if (camp.getCampInfo().getRegClosingDate().isBefore(desiredDate))
                filteredCamp.add(camp);
        }
        // Return the sorted copy
        ArrayList<Camp> filteredSortedCamps = super.sortByAlphabet(filteredCamp);
        return filteredSortedCamps;
    }
}
