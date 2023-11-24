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
import Feedback.Enquiries;

/**
 * Implementation of the ReportGenerator interface for generating reports on camp enquiries.
 */

public class EnquiryReport implements ReportGenerator,Serializable{
	
	/**
     * Generates a report based on the provided list of camps and their enquiries.
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
                // Write each attendee's information to the file
                writer.write("Camp: " + camp.getCampInfo().getCampName() + "\n");
                int i = 1;
                ArrayList<Enquiries> enquiriesList = camp.getEnquiriesList();
                
                if(enquiriesList.isEmpty()){
                    System.out.println("No enquiry available for camp " + camp.getCampInfo().getCampName());
                    writer.write("No enquiry available\n");
                }
                

                for (Enquiries enquiry : enquiriesList) {
                    if(enquiry.getProcessState()){
                        writer.write(i++ + ") " + enquiry.toString() + "\n");
                    }
                }
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
