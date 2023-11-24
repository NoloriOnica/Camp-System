package Report;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import Camp.Camp;
import Camp.CampCommittee;
import Report.ReportFilter;
import Student.Student;

public class CampReportGenerator implements Serializable{
    private ReportFilter reportFilter = new ReportFilterImplementation();

    private ArrayList<Camp> filterSelection(ArrayList<Camp> campList){
        ArrayList<Camp> filteredCamps = null;
        System.out.println("Enter what filter you want to apply for generating Camp Report: ");
        System.out.println("1)Attendee\n2)Camp Committee\n3)None");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                System.out.println("Enter attendee's name:");
                String desiredAttendeeName = sc.nextLine();
                filteredCamps = this.reportFilter.filterAttendee(campList,desiredAttendeeName);
                if(filteredCamps.isEmpty()) return null;
                break;
            case 2:
                System.out.println("Enter Camp Committee's name:");
                String desiredCampCommitteeName = sc.nextLine();
                filteredCamps = this.reportFilter.filterCampCommittee(campList,desiredCampCommitteeName);
                if(filteredCamps.isEmpty()) return null;
                break;
            case 3:
                filteredCamps = campList;
            default:
                System.out.println("Invalid choice.");
        }
        return filteredCamps;
    }

    public void generateCampReport(ArrayList<Camp> campList){
        ArrayList<Camp> filteredCamps = filterSelection(campList);
        if(filteredCamps.isEmpty() || filteredCamps == null){
            System.out.println("NOT FOUND!");
            return;
        }
        // Create a .txt file to write the report
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Camp_Report.txt"))) {
            // Write the header of the report
            for (Camp camp : filteredCamps){
                //Use Camp Committee's object to Call viewCampDetails()
                CampCommittee campCommittee = camp.getRegisteredCampCommittee().get(0);
                writer.write(campCommittee.viewCampDetails()); //Print camp detail
                // Write each attendee's information to the file
                int i = 1;
                ArrayList<Student> studentList = camp.getRegisteredStudents();
                for(Student student : studentList){
                     writer.write((i++) +" "+ student.getName() + " Faculty: "+student.getCampUserGroup().toString() + "\n");
                }
            }
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report.");
            e.printStackTrace();
        }
    }
}
