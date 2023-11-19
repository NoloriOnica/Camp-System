package CampCommittee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Login.AllCampToText;


public class CampCommitteeMenu implements Serializable{

	public static CampCommittee campCommittee;

	public static void createCampCommittee(String studentName, Camp camp, String faculty){
		campCommittee = new CampCommittee(studentName,camp,faculty);
	}


	public static void main(String[] args,ArrayList<Camp> allCamps) {

		ArrayList<Camp> allCamps1 = allCamps;
		
		
		
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
        	System.out.println("\n#################################################");
			System.out.println("You are registed as a Committee Member in the following camp:\n");
			System.out.println(campCommittee.getCamp().getCampInfo().toString());
			
			System.out.println("\n:::Camp Committee Menu:::");
			System.out.println("1. View Suggestions");
			System.out.println("2. Make Suggestions");
			System.out.println("3. Edit Suggestions");
			System.out.println("4. Delete Suggestions");
			System.out.println("5. Generate Camp Report");
			System.out.println("6. View Enquiries of Camp");
			System.out.println("7. Reply Enquiries of Camp");
			System.out.println("0. Exit");
			System.out.println("#################################################");
			System.out.print("\nENTER YOU CHOICE: ");

			try {
				choice = scanner.nextInt();
				scanner.nextLine(); 
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid integer.");
				scanner.nextLine(); 
				choice = -1;
			}


			switch(choice) {
				case 0:
					System.out.println("Going back to Student Main Menu");
					break;
				case 1:
					campCommittee.viewSuggestions();
					break;
				case 2:
					campCommittee.makeSuggestions();
					break;
				case 3:
					campCommittee.editSuggestions();
					break;
				case 4:
					campCommittee.deleteSuggestions();
					break;
				case 5:
					campCommittee.generateReport();
					break;
				case 6:
					campCommittee.viewEnquiries();
					break;
				case 7:
					campCommittee.replyEnquiries();
					break;
				default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                    break;
				
			}
			
			for (Camp camp : allCamps1) {
				if (camp.getCampInfo().getCampName().equals(campCommittee.getCamp().getCampInfo().getCampName())) {
					allCamps1.remove(camp);
					allCamps1.add(campCommittee.getCamp());
					break;
				}
			}
			
			AllCampToText.writeCampsToFile(allCamps1);
		} while (choice != 0);
	}

	
}
