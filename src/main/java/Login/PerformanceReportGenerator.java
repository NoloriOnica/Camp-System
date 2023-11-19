package Login;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PerformanceReportGenerator {
    public void generatePerformanceReport(ArrayList<Camp> camplist){
        if(camplist.isEmpty() || camplist ==null){
            System.out.println("You have not created any camp/Your created camp has not camp committee");
            return;
        }
         // Create a .txt file to write the report
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("Performance_Report.txt"))) {
            // Write the header of the report
            for (Camp camp : camplist){
                ArrayList<CampCommittee> campCommitteeList = camp.getRegisteredCampCommittee();
                writer.write(campCommitteeList.get(0).viewCampDetails()); //Print camp detail
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
    }
}
