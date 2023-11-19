package Feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import Camp.Camp;
import Camp.CampCommittee;

public class SuggestionsHandler implements Serializable{

    public void makeSuggestions(Camp camp, CampCommittee campCommittee) {
		Scanner sc = new Scanner(System.in);
		System.out.println("You are making suggestion for Camp "+ camp.getCampInfo().getCampName());
	    System.out.println("Type your suggestion :");
	    String suggestionString = sc.nextLine();
        int tries = 0, maxTries = 3;
        while (tries < maxTries) {
            if (suggestionString.trim().isEmpty()) {
                System.out.println("Suggestion cannot be blank. Please enter a valid enquiry:");
                suggestionString = sc.nextLine();
                tries++;
            }
            else{
                break;
            }
        }
        if (tries >= maxTries) {
            System.out.println("Please try again later.");
            return; // Return if the user exceeds the maximum number of tries
        }
	    Suggestion suggestion = new Suggestion(campCommittee.getName());
        suggestion.setSuggestion(suggestionString);
        campCommittee.getSuggestionsList().add(suggestion);
		camp.getSuggestionsList().add(suggestion);
	}
	
	public ArrayList <Suggestion> viewSuggestions(CampCommittee campCommittee) { //return a list of suggestion that a camp committee can view
        ArrayList <Suggestion> suggestionsList = campCommittee.getSuggestionsList();
        if(suggestionsList == null ||suggestionsList.isEmpty()){
            System.out.println("You have not made any suggestions yet!");
        	return null;
        }
		ArrayList <Suggestion> suggestionsHolder = new ArrayList<>();
		int i = 0;
		for(Suggestion suggestion : suggestionsList){
            if(!suggestion.getApprovalState() && suggestion.getSenderName().equals(campCommittee.getName())){
				suggestionsHolder.add(suggestion);
				System.out.println((++i) + ") "+suggestion.toString()); //Only can view the suggestion have not been approved
			}
		}
		return suggestionsHolder;
	}

	public void editSuggestions(Camp camp ,CampCommittee campCommittee) {
	    int index = -1; // Initialize index with an invalid value
	    boolean validIndexEntered = false;
		ArrayList<Suggestion> availableSuggestions = campCommittee.viewSuggestions();
        if(availableSuggestions == null){
            System.out.println("You have not submitted any suggestion!");
            return;
        }
		Scanner sc = new Scanner(System.in);
	    // Keep asking for a valid index until one is entered
	    while (!validIndexEntered) {
	        System.out.println("Which suggestion do you want to edit?");
	        if (sc.hasNextInt()) {
	            index = sc.nextInt(); // Get the index from the user
	            sc.nextLine(); // Consume the newline character
	            if (index-1 >= 0 && index-1 < availableSuggestions.size()) {
	                validIndexEntered = true; // Valid index, break the loop
	            } else {
	                System.out.println("Invalid index. Please try again with a valid index.");
	            }
	        } else {
	            System.out.println("That's not a number. Please enter a number corresponding to the suggestion index.");
	            sc.nextLine(); // Consume the non-integer input
	        }
	    }

	    System.out.println("What would you like to change it to? (type 'END' to finish)");
	    StringBuilder suggestion = new StringBuilder();

	    while (sc.hasNextLine()) {
	        String line = sc.nextLine();
	        if ("END".equals(line)) {
	            break;
	        }
	        suggestion.append(line).append("\n");
	    }
		camp.getSuggestionsList().get(index-1).setSuggestion(suggestion.toString());
	    System.out.println("Suggestion updated!");
	}
	
	public void deleteSuggestions(Camp camp, CampCommittee campCommittee) {
	    int index = -1; // Initialize index with an invalid value
	    boolean validIndexEntered = false;
		ArrayList<Suggestion> availableSuggestions = campCommittee.viewSuggestions();
        if(availableSuggestions == null || availableSuggestions.isEmpty()){
            System.out.println("You have not submitted any suggestion!");
            return;
        }
		Scanner sc = new Scanner(System.in);
	    // Keep asking for a valid index until one is entered
	    while (!validIndexEntered) {
	        System.out.println("Which suggestion do you want to remove?");
	        if (sc.hasNextInt()) {
	            index = sc.nextInt(); // Get the index from the user
	            sc.nextLine(); // Consume the newline character
	            if (index > 0 && index <= availableSuggestions.size()) {
	                validIndexEntered = true; // Valid index, proceed with deletion
	            } else {
	                System.out.println("Invalid index. There is no suggestion at this position. Please try again.");
	            }
	        } else {
	            System.out.println("That's not a number. Please enter a numerical index for the suggestion.");
	            sc.nextLine(); // Consume the non-integer input
	        }
	    }

	    // Remove the suggestion at the given index
		camp.getSuggestionsList().remove(index -1);
	    System.out.println("Suggestion removed.");
	}
}
