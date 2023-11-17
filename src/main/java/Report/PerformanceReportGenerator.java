package Report;

import java.io.BufferedWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import Camp.Camp;
import Camp.CampCommittee;

public class PerformanceReportGenerator implements Serializable{
    public void generatePerformanceReport(ArrayList<Camp> camplist){
        if(camplist.isEmpty() || camplist ==null){
            System.out.println("You have not created any camp/Your created camp has not camp committee");
            return;
        }
         // Create a .txt file to write the report
        File reportFile = new File("Performance_Report.txt");
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            // Write the header of the report
            for (Camp camp : camplist){
                ArrayList<CampCommittee> campCommitteeList = camp.getRegisteredCampCommittee();
                if (!campCommitteeList.isEmpty()) { // Check if the list is not empty
        	        CampCommittee campCommittee = campCommitteeList.get(0);
        	        writer.write(campCommittee.viewCampDetails()); //Print camp detail
        	    } else {
        	        // Handle the case where there are no registered camp committees
        	        writer.write("No registered camp committee details available.");
        	    }
                // Write each attendee's information to the file
                int i = 1;
                for(CampCommittee campCommittee : campCommitteeList){
                     writer.write((i++) + " "+ campCommittee.getName() + " Faculty: "+campCommittee.getCampUserGroup().name() + 
                     "Points : "+ campCommittee.getPoints() +"\n");
                }
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
