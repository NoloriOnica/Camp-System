package Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Camp.CampCommittee;
import Login.AllCampToText;


public class CampCommitteeMenu implements Serializable{

	public static Student student;
	public static Camp camp;
	public static CampCommittee campCommittee;

	public static void createStudent(Student studentC, Camp campC) {
		student = studentC;
		camp = campC; //correct bravo!
		//campCommittee = campCommitteeC;
		System.out.println(student.getRegisteredCamps());
		System.out.println("the camp name is: " + camp.getCampInfo().getCampName());
		ArrayList<CampCommittee> committee_array = campC.getRegisteredCampCommittee();

		for (int i = 0; i < committee_array.size(); i++)
		{
			CampCommittee committee = committee_array.get(i);
			System.out.println(committee.getName());
			System.out.println("this is the student name: " + student.getName());
			if (committee.getName().equals(student.getName())) //it is not entering even if they are the same
			{
				campCommittee = committee;
			}
		}
		if (campCommittee == null) //but campCommittee is null
		{
			System.out.println("campcommmittee is null");
		}
		System.out.println(campCommittee.getName()); //error here
	}


	public static void main(String[] args) {

		ArrayList<Camp> allCamps = AllCampToText.readCampsFromFile();
		student.setCampCommittee(campCommittee);
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			student.setCampCommittee(campCommittee);
        	allCamps = AllCampToText.readCampsFromFile();
			
        	System.out.println("\n#################################################");
			System.out.println("You are registed as a Committee Member in the following camp:");
			student.viewCampCommitteeCamp();
			
			System.out.println("\n:::Camp Committee Menu:::");
			System.out.println("1. View Suggestions");
			System.out.println("2. Make Suggestions");
			System.out.println("3. Edit Suggestions");
			System.out.println("4. Delete Suggestions");
			System.out.println("5. Generate Camp Report");
			System.out.println("6. View Enquiries of Camps");
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
				
				default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                    break;
				
			}
			student.setCampCommittee(campCommittee);
			
            AllCampToText.writeCampsToFile(allCamps);

		} while (choice != 0);
	}
	public static Student getStudent()
	{
		return student;
	}

	
}
