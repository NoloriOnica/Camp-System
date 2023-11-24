package Report;

import java.io.Serializable;
import java.util.ArrayList;
import Camp.Camp;
import Camp.CampCommittee;
import Student.Student;

public class ReportFilterImplementation implements ReportFilter, Serializable {
    public ArrayList<Camp> filterAttendee(ArrayList<Camp> camplist,String attendeeName){

        ArrayList<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : camplist){
            ArrayList<Student> students = camp.getRegisteredStudents();
            for(Student student : students){
                if(student.getName().equals(attendeeName) && !student.getCommitteeForCamp().equals(camp)){
                    filteredCamps.add(camp);
                }
            }
        }

        return filteredCamps;
    }

    public ArrayList<Camp> filterCampCommittee(ArrayList<Camp> camplist, String campCommitteeName) {

        ArrayList<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : camplist){
            ArrayList<CampCommittee> CampCommittees = camp.getRegisteredCampCommittee();
            for(CampCommittee campCommittee : CampCommittees){
                if(campCommittee.getName().equals(campCommitteeName)){
                    filteredCamps.add(camp);
                }
            }
        }
        return filteredCamps;
        
    }
}