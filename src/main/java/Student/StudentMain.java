package Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Camp.Camp;
import CampCommittee.CampCommittee;
import CampCommittee.CampCommitteeMenu;
import Feedback.Enquiries;
import Login.AllCampToText;
import Login.User;
import Staff.Staff;


public class StudentMain implements Serializable{
	public static Student student;
	
	public static void createStudent(String userId, String name, String email, String faculty, String userType) {
		student = new Student(userId, name, email, faculty, userType);
	}
	
		
    public static void main(String[] args) {

        ArrayList<Camp> allCamps = AllCampToText.readCampsFromFile();
        
        // Initialisation
            for (Camp camp : allCamps) {
                // Registered student
                ArrayList<Student> registeredStudentList = camp.getRegisteredStudents();
                for (Student registeredStudent : registeredStudentList) {
                    if (registeredStudent.getUserID().equals(student.getUserID())) {
                        student.getRegisteredCamps().add(camp);
                        break;
                    }
                }

                // Registered camp committee
                ArrayList<CampCommittee> campCommitteeList = camp.getRegisteredCampCommittee();
                for (CampCommittee campCommittee : campCommitteeList) {
                    if (campCommittee.getUserID().equals(student.getUserID())) {
                        student.setCommitteeForCamp(camp);
                        student.setCampCommittee(true);
                        break;
                    }
                }

                // Load the enquiries
                ArrayList<Enquiries> campEnquiries = camp.getEnquiriesList();
                for (Enquiries enquiry : campEnquiries) {
                    if (enquiry.getSenderId().equals(student.getUserID())) {
                        student.getEnquiriesList().add(enquiry);
                    }
                }
            }
        
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            
        	allCamps = AllCampToText.readCampsFromFile();

        	System.out.println("\n#################################################");
        	System.out.println(":::Student Menu:::");
            System.out.println("1. View Registered Camps");
            System.out.println("2. View Available Camps");
            System.out.println("3. View Camp Slots");
            System.out.println("4. Register for Camp");
            System.out.println("5. Withdraw from Camp");
            System.out.println("6. Make Enquiries");
            System.out.println("7. View Enquiries");
            System.out.println("8. Edit Enquiry");
            System.out.println("9. Delete Enquiry");
            if (student.isCampCommittee())
            {
            	 System.out.println("10. Camp Committee Menu");
            }
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
                    student.editEnquiry(allCamps);
                    break;
                case 9:
                    student.deleteEnquiry(allCamps);
                    break;
                case 0:
                    System.out.println("Going back to Login Menu!!");
                    break;
                case 10:
                	if (student.isCampCommittee()) {
                        CampCommitteeMenu.createCampCommittee(student.getUserID(),student.getName(),student.getEmail(), student.getFaculty(),student.getUserType(), student.getCommitteeForCamp());
                		CampCommitteeMenu.main(new String[] {}, allCamps);
                	}
                	else System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                	break;
                    
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                    break;
            }

            AllCampToText.writeCampsToFile(allCamps);

        } while (choice != 0);

    }
}
