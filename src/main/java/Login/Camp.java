package Login;


import java.time.LocalDate;
import java.util.ArrayList;


public class Camp {
    private CampInfo campInfo;
    private int remainingAttendeeSlot;
    private ArrayList<Enquiries> enquiriesList;
    private ArrayList<Suggestion> suggestionsLsit;
    private ArrayList<Student> registeredStudents; // This will keep track of students registered for the camp
    private ArrayList<CampCommittee> registeredCampCommittees;

    public Camp(String campName, LocalDate startDate, LocalDate endDate, LocalDate regClosingDate,
            CampUserGroup campUserGroup, CampVisibility campVisibility, String location, int totalSlots,
            int campCommitteeSlot, String description, String staffInChargeID) {
        campInfo = new CampInfo(campName, startDate, endDate, regClosingDate, campUserGroup, campVisibility, 
                                location, totalSlots, campCommitteeSlot, description, staffInChargeID);
        this.remainingAttendeeSlot = campInfo.getTotalSlots()-campInfo.getCampCommitteeSlot();
        enquiriesList = new ArrayList<>();
        suggestionsLsit = new ArrayList<>();
        registeredStudents = new ArrayList<>();
        registeredCampCommittees = new ArrayList<>();
    }

    public CampCommittee findCampCommittee(String studentName) {
        for (CampCommittee campCommittee : this.registeredCampCommittees) {
            if (campCommittee.getName().equals(studentName)) {
                return campCommittee;
            }
        }
        // If no matching CampCommittee is found
        return null;
    }

    //Below are all getters and setters
    public CampInfo getCampInfo(){
        return this.campInfo;
    }

    public int getRemainingAttendeeSlot(){
        return this.remainingAttendeeSlot;
    }

    public void setRemainingAttendeeSlot(int remainingAttendeeSlot){
        this.remainingAttendeeSlot = remainingAttendeeSlot;
    }

    public void addRegisteredStudents(Student student){
        this.registeredStudents.add(student);
    }

    public ArrayList<Student> getregisteredStudents(){
        return this.registeredStudents;
    }

    public void addRegisteredCampCommittee(CampCommittee campCommittee){
        this.registeredCampCommittees.add(campCommittee);
    }

    public ArrayList<CampCommittee> getRegisteredCampCommittee(){
        return this.registeredCampCommittees;
    }

    public void addEnquiriesList(Enquiries enquiry){
        enquiriesList.add(enquiry);
    }

    public void addSuggestionsList(Suggestion suggestion){
        suggestionsLsit.add(suggestion);
    }

    public ArrayList<Enquiries> getEnquiriesList() {
        return this.enquiriesList;
    }

    public ArrayList<Suggestion> getSuggestionsList() {
        return this.suggestionsLsit;
    }

}
