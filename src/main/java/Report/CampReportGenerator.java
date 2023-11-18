package Report;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Desktop;
import java.io.File;

import Camp.Camp;
import Camp.CampCommittee;
import Report.ReportFilter;
import Student.Student;

public class CampReportGenerator implements Serializable {
    private ReportFilter reportFilter = new ReportFilterImplementation();

    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = null;
        System.out.println("Enter what filter you want to apply for generating Camp Report: ");
        System.out.println("1)Attendee\n2)Camp Committee\n3)None");
        Scanner sc = new Scanner(System.in);
        int maxTries = 3;
        int tries = 0;
        int choice = 0;
        while (tries < maxTries) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        System.out.println("Enter attendee's name:");
                        String desiredAttendeeName = sc.nextLine();
                        filteredCamps = this.reportFilter.filterAttendee(campList, desiredAttendeeName);
                        if (filteredCamps.isEmpty()) {
                            return null;
                        }
                        break;
                    case 2:
                        System.out.println("Enter Camp Committee's name:");
                        String desiredCampCommitteeName = sc.nextLine();
                        filteredCamps = this.reportFilter.filterCampCommittee(campList, desiredCampCommitteeName);
                        if (filteredCamps.isEmpty()) {
                            return null;
                        }
                        break;
                    case 3:
                        filteredCamps = campList;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        tries++;
                        break;
                }

                // Break out of the loop if the choice was valid
                if (choice >= 1 && choice <= 3) {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume the invalid input
                tries++;
            }
        }

        if (tries == maxTries) {
            System.out.println("You've reached the maximum number of tries. Please try again later.");
            return null;
        }

        return filteredCamps;
    }

    public void generateCampReport(ArrayList<Camp> campList) {
        ArrayList<Camp> filteredCamps = filterSelection(campList);
        if (filteredCamps == null || filteredCamps.isEmpty()) {
            System.out.println("NOT FOUND!");
            return;
        }
        // Create a .txt file to write the report
        File reportFile = new File("Camp_Report.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            // Write the header of the report
            for (Camp camp : filteredCamps) {
                // Use Camp Committee's object to Call viewCampDetails()
                writer.write(camp.getCampInfo().toString() + "\n");
                // Write each attendee's information to the file
                int i = 1;
                ArrayList<Student> studentList = camp.getRegisteredStudents();
                for (Student student : studentList) {
                    System.out.println();
                    if(student.getCommitteeForCamp()!=null && student.getCommitteeForCamp().equals(camp)){
                        writer.write(
                            (i++) + " " + student.getName() + " Faculty: " + student.getFaculty().toString() + " Role: Camp Committee\n");
                    }else{
                        writer.write(
                            (i++) + " " + student.getName() + " Faculty: " + student.getFaculty().toString() + " Role: Attendee\n");
                    }
                }
                writer.write("\n");
            }
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report.");
            e.printStackTrace();
            return;
        }

        // After writing the report, try to open it
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(reportFile);
                System.out.println("Report generated and opened successfully.");
            } catch (IOException e) {
                System.out.println("The report was generated, but there was an error opening it.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported on this platform.");
        }

    }
}
