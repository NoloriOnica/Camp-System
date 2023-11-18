package Staff;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.Camp;
import Login.User;
import Login.AllCampToText;

public class StaffMain {
	public static Staff staff;
	
	public static void createStaff(String userId, String name, String email, String faculty, String userType) {
		staff = new Staff(userId, name, email, faculty, userType);
	}
	
	
	public static void main(String[] args) {
        ArrayList<Camp> allCamps = AllCampToText.readCampsFromFile(); //get all camp Informations
        //System.out.println(allCamps);

        //Initialisation
        for(Camp camp : allCamps){
            if(camp.getCampInfo().getStaffInChargeID().equals(staff.getName())){
                staff.getCreatedCamp().add(camp);
            }
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
        	allCamps = AllCampToText.readCampsFromFile();
        	
        	System.out.println("\n#################################################");
        	System.out.println(":::STAFF MENU:::");
            System.out.println("1. Create a Camp");
            System.out.println("2. Edit a Camp");
            System.out.println("3. Delete a Camp");
            System.out.println("4. Toggle Camp Visibility");
            System.out.println("5. View Camps");
            System.out.println("6. View Own Camps");
            System.out.println("7. View Enquiries");
            System.out.println("8. Reply to Enquiries");
            System.out.println("9. View Suggestions");
            System.out.println("10. Approve Suggestions");
            System.out.println("11. Generate Camp Report");
            System.out.println("12. Generate Performance Report");
            System.out.println("0. Exit");
            System.out.println("#################################################");
            System.out.print("\nENTER YOU CHOICE: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
                choice = -1;
            }

            switch (choice) {
                case 1:
                    Camp createdCamp = staff.createCamp(allCamps);
                    if (createdCamp != null) {
                        allCamps.add(createdCamp);
                    }
                    break;
                case 2:
                    staff.editCamp(allCamps);
                    break;
                case 3:
                    staff.deleteCamp(allCamps);
                    break;
                case 4:
                    staff.toggleCampVisibility();
                    break;
                case 5:
                    staff.viewCamps(allCamps);
                    break;
                case 6:
                    staff.viewOwnCamps();
                    break;
                case 7:
                    staff.viewEnquiries();
                    break;
                case 8:
                    staff.replyEnquiries();
                    break;
                case 9:
                    staff.viewSuggestion();
                    break;
                case 10:
                    staff.approveSuggestion();
                    break;
                case 11:
                    staff.generateCampReport();
                    break;
                case 12:
                    staff.generatePerformanceReport();
                    break;
                case 0:
                    System.out.println("Going back to Login Menu!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 12.");
                    break;
            
            }
            
            AllCampToText.writeCampsToFile(allCamps);
            //System.out.println(allCamps);

        } while (choice != 0);

    }
}
