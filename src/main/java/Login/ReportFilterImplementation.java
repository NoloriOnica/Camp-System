package Login;

import java.util.ArrayList;

public class ReportFilterImplementation implements ReportFilter {
    public ArrayList<Camp> filterAttendee(ArrayList<Camp> camplist,String attendeeName){

        ArrayList<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : camplist){
            ArrayList<Student> students = camp.getregisteredStudents();
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