package CampCommittee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Camp.Camp;
import Login.AllCampToText;

/**
 * Represents the menu and functionalities for a Camp Committee member.
 */
public class CampCommitteeMenu implements Serializable {

    /** The Camp Committee member associated with this menu. */
    public static CampCommittee campCommittee;

    /**
     * Creates a CampCommittee object based on provided information.
     *
     * @param studentUserID    User ID of the committee member.
     * @param studentName      Name of the committee member.
     * @param studentEmail     Email of the committee member.
     * @param studentFaculty   Faculty of the committee member.
     * @param studentUserType  Type of the committee member.
     * @param camp             Camp associated with the committee member.
     */
    public static void createCampCommittee(String studentUserID, String studentName, String studentEmail,
                                           String studentFaculty, String studentUserType, Camp camp) {
        campCommittee = new CampCommittee(studentUserID, studentName, studentEmail, studentFaculty, studentUserType, camp);
    }

    /**
     * Main menu for Camp Committee functionalities.
     *
     * @param allCamps  List of all camps available.
     */
    public static void main(String[] args, ArrayList<Camp> allCamps) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n#################################################");
            System.out.println("You are registered as a Committee Member in the following camp:\n");
            System.out.println(campCommittee.getCamp().getCampInfo().toString());

            System.out.println("\n:::Camp Committee Menu:::");
            System.out.println("1. View Suggestions");
            System.out.println("2. Make Suggestions");
            System.out.println("3. Edit Suggestions");
            System.out.println("4. Delete Suggestions");
            System.out.println("5. Generate Report");
            System.out.println("6. View Enquiries of Camp");
            System.out.println("7. Reply Enquiries of Camp");
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
                    campCommittee.viewCampEnquiries();
                    break;
                case 7:
                    campCommittee.replyCampEnquiries();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 7.");
                    break;
            }

            for (Camp camp : allCamps) {
                if (camp.getCampInfo().getCampName().equals(campCommittee.getCamp().getCampInfo().getCampName())) {
                    allCamps.remove(camp);
                    allCamps.add(campCommittee.getCamp());
                    break;
                }
            }

            AllCampToText.writeCampsToFile(allCamps);
        } while (choice != 0);
    }
}
