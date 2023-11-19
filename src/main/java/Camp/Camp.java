package Camp;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Feedback.Suggestion;
import Feedback.Enquiries;

import Student.Student;
import java.io.*;

public class Camp implements Serializable{
    private CampInfo campInfo;
    private int remainingAttendeeSlot;
    private ArrayList<Enquiries> enquiriesList;
    private ArrayList<Suggestion> suggestionsList;
    private ArrayList<Student> registeredStudents; // This will keep track of students registered for the camp
    private ArrayList<Student> bannedStudents;
    private ArrayList<CampCommittee> registeredCampCommittees;

    public Camp(String campName, LocalDate startDate, LocalDate endDate, LocalDate regClosingDate,
            String campUserGroup, CampVisibility campVisibility, String location, int totalSlots,
            int campCommitteeSlot, String description, String staffInChargeID) {
        campInfo = new CampInfo(campName, startDate, endDate, regClosingDate, campUserGroup, campVisibility, 
                                location, totalSlots, campCommitteeSlot, description, staffInChargeID);
        this.remainingAttendeeSlot = campInfo.getTotalSlots()-campInfo.getCampCommitteeSlot();
        enquiriesList = new ArrayList<>();
        suggestionsList = new ArrayList<>();
        registeredStudents = new ArrayList<>();
        bannedStudents = new ArrayList<>();
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

    public ArrayList<Student> getRegisteredStudents(){
        return this.registeredStudents;
    }

    public void addBannedStudents(Student student){
        this.bannedStudents.add(student);
    }

    public ArrayList<Student> getBannedStudents(){
        return this.bannedStudents;
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
        suggestionsList.add(suggestion);
    }

    public ArrayList<Enquiries> getEnquiriesList() {
        return this.enquiriesList;
    }

    public ArrayList<Suggestion> getSuggestionsList() {
        return this.suggestionsList;
    }

}
