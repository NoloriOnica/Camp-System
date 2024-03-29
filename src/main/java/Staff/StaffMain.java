package Staff;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.Camp;
import Login.User;
import Login.AllCampToText;


/**
 * Main class handling staff-related operations for camps.
 */
public class StaffMain {
	public static Staff staff;
	
	
	/**
     * Creates a new staff member instance.
     *
     * @param userId    The ID of the staff member.
     * @param name      The name of the staff member.
     * @param email     The email of the staff member.
     * @param faculty   The faculty of the staff member.
     * @param userType  The type of user (staff).
     */
	public static void createStaff(String userId, String name, String email, String faculty, String userType) {
		staff = new Staff(userId, name, email, faculty, userType);
	}
	
	/**
     * Main method executing staff-related functionalities for camps.
     */
	public static void main(String[] args) {
        ArrayList<Camp> allCamps = AllCampToText.readCampsFromFile(); //get all camp Informations
        //System.out.println(allCamps);

        //Initialisation
        for(Camp camp : allCamps){
            if(camp.getCampInfo().getStaffInChargeID().equals(staff.getUserID())){
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
            System.out.println("11. Generate Report");
            System.out.println("0. Exit");
            System.out.println("#################################################");
            System.out.print("\nENTER YOUR CHOICE: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                choice = -1;
                continue;
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
                    staff.viewOwnCamps(true);
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
                    staff.generateReport();
                    break;
                case 0:
                    System.out.println("Going back to Login Menu!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 11.");
                    break;
            
            }

            ArrayList<Camp> staffCreatedCamps = staff.getCreatedCamp();
            ArrayList<Camp> newAllCamps = new ArrayList<>(allCamps);
            for (Camp camp : allCamps) {
                for (Camp staffCamp : staffCreatedCamps) {
                    if (camp.getCampInfo().getCampName().equals(staffCamp.getCampInfo().getCampName())) {
                        newAllCamps.remove(camp);
                        newAllCamps.add(staffCamp);
                        break;
                    }
                }
            }
            AllCampToText.writeCampsToFile(newAllCamps);
            //System.out.println(allCamps);

        } while (choice != 0);

    }
}
