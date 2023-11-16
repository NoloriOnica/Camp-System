package Student;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.Camp;
import Camp.CampUserGroup;
import Login.AllCampToText;
import Login.User;
import Staff.Staff;


public class StudentMain {
	public static Student student;
	
	public static void createStudent(String userId, String name, String email, String faculty, String userType) {
		student = new Student(userId, name, email, faculty, userType);
	}
	
		
    public static void main(String[] args) {

        ArrayList<Camp> allCamps = AllCampToText.readCampsFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Registered Camps");
            System.out.println("2. View Available Camps");
            System.out.println("3. View Camp Slots");
            System.out.println("4. Register for Camp");
            System.out.println("5. Withdraw from Camp");
            System.out.println("6. Make Enquiries");
            System.out.println("7. View Enquiries");
            System.out.println("8. Edit Enquiry");
            System.out.println("9. Delete Enquiry");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
                choice = -1;
            }

            switch (choice) {
                case 1:
                    student.viewRegisteredCamps();
                    break;
                case 2:
                    student.viewCamps(allCamps);
                    break;
                case 3:
                    student.viewCampSlots(allCamps);
                    break;
                case 4:
                    student.registerForCamp(allCamps);
                    break;
                case 5:
                    student.withdrawFromCamp(allCamps);
                    break;
                case 6:
                    student.makeEnquiries(allCamps);
                    break;
                case 7:
                    student.viewEnquiries();
                    break;
                case 8:
                    student.editEnquiry();
                    break;
                case 9:
                    student.deleteEnquiry();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                    break;
            }
            
            AllCampToText.writeCampsToFile(allCamps);

        } while (choice != 0);

    }
}
