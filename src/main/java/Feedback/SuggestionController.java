package Feedback;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Camp.CampCommittee;

public class SuggestionController {

    public void viewSuggestion(ArrayList<Camp> camps) {
        for (Camp camp : camps) {
            ArrayList<Suggestion> suggestions = camp.getSuggestionsList();
            System.out.println("\n" + camp.getCampInfo().getCampName());
            for (Suggestion suggestion : suggestions) {
                System.out.println(suggestion.toString());
            }
        }
    }

    public void approveSuggestion(ArrayList<Camp> camps) {
        int maxTries = 3;
        int tries = 0;

        while (tries < maxTries) {
            System.out.println("Which camp do you want to approve a suggestion for?");

            try {
                Scanner sc = new Scanner(System.in);

                // Display camps and their indices
                for (int i = 0; i < camps.size(); i++) {
                    System.out.println((i + 1) + ". " + camps.get(i).getCampInfo().getCampName());
                }

                int campChoice = sc.nextInt() - 1;

                if (campChoice >= 0 && campChoice < camps.size()) {
                    Camp selectedCamp = camps.get(campChoice);

                    ArrayList<Suggestion> suggestions = selectedCamp.getSuggestionsList();

                    if (suggestions.isEmpty()) {
                        System.out.println(
                                "No suggestions found for " + selectedCamp.getCampInfo().getCampName() + ". Please select another camp.");
                        continue; // Restart the loop to allow the user to choose another camp
                    }

                    System.out.println("Choose a suggestion to approve (Enter alphabet):");
                    for (int i = 0; i < suggestions.size(); i++) {
                        System.out.println((char) ('A' + i) + ". " + suggestions.get(i).toString());
                    }

                    char suggestionChoice = sc.next().toUpperCase().charAt(0);

                    // Validate the input
                    if (suggestionChoice >= 'A' && suggestionChoice < 'A' + suggestions.size()) {
                        int index = suggestionChoice - 'A';

                        if (suggestions.get(index).getApprovalState()) {
                            System.out.println("That suggestion by " + suggestions.get(index).getSenderName()
                                    + " is ALREADY approved.");
                        } else {
                            suggestions.get(index).setApprovalState(true);
                            //give one extra point to the Camp Committee
                            String campCommitteeName = suggestions.get(index).getSenderName();
                            CampCommittee campCommittee = selectedCamp.findCampCommittee(campCommitteeName);
                            if(campCommittee != null)
                                campCommittee.setPoint(campCommittee.getPoints()+1);
                            else{
                                System.out.println("Error");
                                return;
                            }
                            System.out.println(
                                    "That suggestion by " + suggestions.get(index).getSenderName() + " is approved.");
                        }

                        return; // End the function after successful approval
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
    }
}
