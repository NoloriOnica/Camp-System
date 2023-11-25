package Feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.Camp;
import CampCommittee.CampCommittee;

/**
 * Manages operations related to suggestions made by camp committees for camps.
 */

public class SuggestionsHandler implements Serializable{

	/**
     * Creates a suggestion for a specific camp by a camp committee.
     *
     * @param camp           The camp to make a suggestion for.
     * @param campCommittee  The camp committee who is making the suggestion.
     */
	
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
	    Suggestion suggestion = new Suggestion(campCommittee.getUserID(),campCommittee.getName());
        suggestion.setSuggestion(suggestionString);
        //campCommittee.getSuggestionsList().add(suggestion);
		camp.getSuggestionsList().add(suggestion);
        System.out.println("Suggestion are made successfully!");
	}
	
    /**
     * Displays suggestions made by a specific camp committee that are awaiting approval.
     *
     * @param campCommittee  The camp committee whose suggestions are being viewed.
     * @return               List of suggestions made by the committee awaiting approval.
     */
    
	public ArrayList <Suggestion> viewSuggestions(CampCommittee campCommittee) { //return a list of suggestion that a camp committee can view
        ArrayList <Suggestion> suggestionsList = campCommittee.getCamp().getSuggestionsList();
        if(suggestionsList == null ||suggestionsList.isEmpty()){
            System.out.println("You have not made any suggestions yet!");
        	return null;
        }
		ArrayList <Suggestion> suggestionsHolder = new ArrayList<>();
		int i = 0;
		for(Suggestion suggestion : suggestionsList){
            if(suggestion.getSenderId().equals(campCommittee.getUserID()) && !suggestion.getApprovalState()){
				suggestionsHolder.add(suggestion);
				System.out.println((++i) + ") "+suggestion.toString()); //Only can view the suggestion have not been approved
			}
		}
		if(suggestionsHolder.isEmpty()){
			System.out.println("No available suggestion to be viewed.");
		}
		return suggestionsHolder;
	}

	/**
     * Edit a previously submitted, unapproved suggestion made by a camp committee.
     *
     * @param camp           The camp associated with the suggestions.
     * @param campCommittee  The camp committee who made the suggestions.
     */
	
	public void editSuggestions(Camp camp ,CampCommittee campCommittee) {

		ArrayList<Suggestion> availableSuggestions = this.viewSuggestions(campCommittee);
        if(availableSuggestions == null || availableSuggestions.isEmpty()){
            System.out.println("You have not submitted any suggestion!");
            return;
        }
		Scanner sc = new Scanner(System.in);
	   int tries = 0, maxTries = 3;
        int index = -1; // Initialize index with an invalid value
        while(tries < maxTries){
            try{
                System.out.println("Choose which suggestion to edit");
                index = sc.nextInt(); // Get the index from the user
                 if (index - 1 >= 0 && index - 1 < availableSuggestions.size()){
                    break;
                 }else{
                    System.out.println("Invalid index. Please enter a valid index.");
                    tries++;
                 }

            }catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return;
        }

        sc.nextLine(); //consume next line

	    System.out.println("What would you like to change it to?");
	    String suggestionString = sc.nextLine();
		tries = 0;
        while (tries < maxTries) {
            if (suggestionString.trim().isEmpty()) {
                System.out.println("Suggestion cannot be blank. Please enter a valid suggestion:");
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
		camp.getSuggestionsList().get(index-1).setSuggestion(suggestionString);
	    System.out.println("Suggestion updated!");
	}
	
	/**
     * Delete a previously submitted, unapproved suggestion made by a camp committee.
     *
     * @param camp           The camp associated with the suggestions.
     * @param campCommittee  The camp committee who made the suggestions.
     */
	
	public void deleteSuggestions(Camp camp, CampCommittee campCommittee) {

		ArrayList<Suggestion> availableSuggestions = this.viewSuggestions(campCommittee);
        if(availableSuggestions == null || availableSuggestions.isEmpty()){
            System.out.println("You have not submitted any suggestion!");
            return;
        }
		Scanner sc = new Scanner(System.in);
	   int tries = 0, maxTries = 3;
        int index = -1; // Initialize index with an invalid value
        while(tries < maxTries){
            try{
                System.out.println("Choose which suggestion to delete");
                index = sc.nextInt(); // Get the index from the user
                 if (index - 1 >= 0 && index - 1 < availableSuggestions.size()){
                    break;
                 }else{
                    System.out.println("Invalid index. Please enter a valid index.");
                    tries++;
                 }

            }catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Consume the invalid input to prevent an infinite loop
                tries++;
            }
        }
        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            // Handle the case where the user exceeds the maximum number of tries
            return;
        }

	    // Remove the suggestion at the given index
		camp.getSuggestionsList().remove(index -1);
	    System.out.println("Suggestion removed.");
	}
}
