package Feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import CampCommittee.CampCommittee;

public class SuggestionController implements Serializable {

    public void viewSuggestion(ArrayList<Camp> camps) {
        if (camps.isEmpty()) {
            System.out.println("\nNo suggestions made to any camp");
            System.out.println("Returning to main menu ...");
            return;
        }
        for (Camp camp : camps) {
            ArrayList<Suggestion> suggestions = camp.getSuggestionsList();
            System.out.println("\n" + camp.getCampInfo().getCampName());
            if (suggestions.isEmpty()) {
                System.out.println("No Suggestions made for " + camp.getCampInfo().getCampName());
            } else {
                for (Suggestion suggestion : suggestions) {
                    System.out.println(suggestion.toString());
                }
            }
        }
    }

    public void approveSuggestion(ArrayList<Camp> camps) {
        if (camps.isEmpty()) {
            System.out.println("\nNo suggestions made to any camp, please check again later");
            System.out.println("Exiting to main menu.....");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int maxTries = 3;

        for (int tries = 0; tries < maxTries; tries++) {
            System.out.println("Which camp do you want to approve a suggestion for? (Enter number or 0 to exit):");

            // Display camps and their indices
            for (int i = 0; i < camps.size(); i++) {
                System.out.println((i + 1) + ". " + camps.get(i).getCampInfo().getCampName());
            }
            System.out.println("0. Exit to main menu");

            try {
                int campChoice = sc.nextInt() - 1;

                if (campChoice == -1) {
                    System.out.println("Exiting to main menu...");
                    return;
                }

                if (campChoice >= 0 && campChoice < camps.size()) {
                    Camp selectedCamp = camps.get(campChoice);
                    ArrayList<Suggestion> suggestions = selectedCamp.getSuggestionsList();

                    if (suggestions.isEmpty()) {
                        System.out.println("No suggestions found for " + selectedCamp.getCampInfo().getCampName() + ". Please select another camp.");
                        if (camps.size() == 1) {
                            System.out.println("No other camps to choose from. Exiting to main menu...");
                            return;
                        }
                        continue; // Restart the loop to allow the user to choose another camp
                    }

                    System.out.println("Choose a suggestion to approve (Enter alphabet):");
                    for (int i = 0; i < suggestions.size(); i++) {
                        System.out.println((char) ('A' + i) + ". " + suggestions.get(i).toString());
                    }

                    char suggestionChoice = sc.next().toUpperCase().charAt(0);

                    if (suggestionChoice >= 'A' && suggestionChoice < 'A' + suggestions.size()) {
                        int index = suggestionChoice - 'A';
                        Suggestion selectedSuggestion = suggestions.get(index);

                        if (selectedSuggestion.getApprovalState()) {
                            System.out.println("That suggestion by " + selectedSuggestion.getSenderName() + " is ALREADY approved.");
                            continue;
                        }

                        selectedSuggestion.setApprovalState(true);
                        String campCommitteeName = selectedSuggestion.getSenderName();
                        CampCommittee campCommittee = selectedCamp.findCampCommittee(campCommitteeName);
                        if (campCommittee != null) {
                            // Give one extra point to the Camp Committee
                            campCommittee.setPoint(campCommittee.getPoints() + 1);
                            System.out.println("That suggestion by " + selectedSuggestion.getSenderName() + " is approved.");
                        } else {
                            System.out.println("Error: Camp Committee not found.");
                            continue;
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
                sc.nextLine(); // Clear the buffer
            }

            if (tries < maxTries - 1) {
                System.out.println("Try again. " + (maxTries - tries - 1) + " tries remaining.");
            } else {
                System.out.println("Try again later.");
            }
        }

        System.out.println("Exiting to main menu...");
    }
}
