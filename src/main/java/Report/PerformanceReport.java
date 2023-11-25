package Report;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Desktop;
import java.io.File;

import Camp.Camp;
import CampCommittee.CampCommittee;

/**
 * Generates a performance report based on camp committee data.
 */

public class PerformanceReport implements ReportGenerator,Serializable{
    
	/**
     * Generates a performance report for provided camp data.
     *
     * @param campList The list of camps to generate the report for.
     */
	
     public void generateReport(ArrayList<Camp> campList){
        if (campList.isEmpty() || campList == null) {
            System.out.println("NOT FOUND!");
            return;
        }
        // Create a .txt file to write the report
        File reportFile = new File("Performance_Report.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            // Write the header of the report
            for (Camp camp : campList) {
                ArrayList<CampCommittee> campCommitteeList = camp.getRegisteredCampCommittee();
                writer.write(camp.getCampInfo().toString() + "\n");
                if (campCommitteeList.isEmpty()) { // Check if the list is empty
                    // Handle the case where there are no registered camp committees
                    writer.write("No registered camp committee details available.");
                } else {
                    int i = 1;
                    for (CampCommittee campCommittee : campCommitteeList) {
                        writer.write((i++) + ") " + campCommittee.getName() + " Faculty: " + campCommittee.getFaculty() +
                                " Points : " + campCommittee.getPoints() + "\n");
                    }
                }
                writer.write("\n");
            }
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report.");
            e.printStackTrace();
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
