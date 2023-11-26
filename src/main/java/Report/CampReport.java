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
import Student.Student;

/**
 * Implementation of the ReportGenerator interface for generating camp reports.
 */


public class CampReport implements ReportGenerator, Serializable{
	/**
     * Generates a report based on the provided list of camps and their attendees/committees.
     *
     * @param campList The list of camps to generate the report for.
     */
	
	public void generateReport(ArrayList<Camp> campList){
        if (campList == null || campList.isEmpty()) {
            System.out.println("NOT FOUND!");
            return;
        }
        // Create a .txt file to write the report
        File reportFile = new File("Camp_Report.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            // Write the header of the report
            for (Camp camp : campList) {
                // Use Camp Committee's object to Call viewCampDetails()
                writer.write(camp.getCampInfo().toString() + "\n");
                // Write each attendee's information to the file
                int i = 1;
                ArrayList<Student> studentList = camp.getRegisteredStudents();
                if(studentList.isEmpty()){
                    System.out.println("No registered attendee/camp committee details available.\n");
                    writer.write("No registered attendee/camp committee details available.");
                }
                for (Student student : studentList) {
                    System.out.println();
                    if(student.getCommitteeForCamp()!=null && student.getCommitteeForCamp().equals(camp)){
                        writer.write(
                            (i++) + ") " + student.getName() + ", Faculty: " + student.getFaculty().toString() + ", Role: Camp Committee\n");
                    }else{
                        writer.write(
                            (i++) + ") " + student.getName() + ", Faculty: " + student.getFaculty().toString() + ", Role: Attendee\n");
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
