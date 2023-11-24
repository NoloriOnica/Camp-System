package Camp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import CampCommittee.CampCommittee;
import Feedback.Suggestion;
import Feedback.Enquiries;

import Student.Student;
import java.io.*;

/**
 * Represents a camp with its information, attendees, committees, and feedback.
 */
public class Camp implements Serializable {

    private CampInfo campInfo;
    private int remainingAttendeeSlot;
    private ArrayList<Enquiries> enquiriesList;
    private ArrayList<Suggestion> suggestionsList;
    private ArrayList<Student> registeredStudents; // Keeps track of students registered for the camp
    private ArrayList<Student> bannedStudents;
    private ArrayList<CampCommittee> registeredCampCommittees;

    /**
     * Constructor to initialize a Camp object with camp information.
     *
     * @param campName            Name of the camp.
     * @param startDate           Start date of the camp.
     * @param endDate             End date of the camp.
     * @param regClosingDate      Closing date for camp registration.
     * @param campUserGroup       User group associated with the camp.
     * @param campVisibility      Visibility status of the camp.
     * @param location            Location of the camp.
     * @param totalSlots          Total slots available for the camp.
     * @param campCommitteeSlot   Slots allocated for the camp committee.
     * @param description         Description of the camp.
     * @param staffInChargeID     ID of the staff in charge of the camp.
     */
    public Camp(String campName, LocalDate startDate, LocalDate endDate, LocalDate regClosingDate,
                String campUserGroup, CampVisibility campVisibility, String location, int totalSlots,
                int campCommitteeSlot, String description, String staffInChargeID) {
        campInfo = new CampInfo(campName, startDate, endDate, regClosingDate, campUserGroup, campVisibility,
                location, totalSlots, campCommitteeSlot, description, staffInChargeID);
        this.remainingAttendeeSlot = campInfo.getTotalSlots() - campInfo.getCampCommitteeSlot();
        enquiriesList = new ArrayList<>();
        suggestionsList = new ArrayList<>();
        registeredStudents = new ArrayList<>();
        bannedStudents = new ArrayList<>();
        registeredCampCommittees = new ArrayList<>();
    }

    /**
     * Finds a CampCommittee based on the student ID.
     *
     * @param studentID The ID of the student to search for in the camp committees.
     * @return The CampCommittee object if found; otherwise, returns null.
     */
    public CampCommittee findCampCommittee(String studentID) {
        for (CampCommittee campCommittee : this.registeredCampCommittees) {
            if (campCommittee.getUserID().equals(studentID)) {
                return campCommittee;
            }
        }
        // If no matching CampCommittee is found
        return null;
    }

    // Below are all getters and setters

    /**
     * Get the camp information.
     *
     * @return The CampInfo object representing camp information.
     */
    public CampInfo getCampInfo() {
        return this.campInfo;
    }

    /**
     * Get the remaining available attendee slots for the camp.
     *
     * @return The number of remaining attendee slots.
     */
    public int getRemainingAttendeeSlot() {
        return this.remainingAttendeeSlot;
    }

    /**
     * Set the remaining available attendee slots for the camp.
     *
     * @param remainingAttendeeSlot The number of remaining attendee slots to be set.
     */
    public void setRemainingAttendeeSlot(int remainingAttendeeSlot) {
        this.remainingAttendeeSlot = remainingAttendeeSlot;
    }

    /**
     * Add a registered student to the camp.
     *
     * @param student The Student object to be added as a registered student.
     */
    public void addRegisteredStudents(Student student) {
        this.registeredStudents.add(student);
    }

    /**
     * Get the list of registered students for the camp.
     *
     * @return The list of registered students.
     */
    public ArrayList<Student> getRegisteredStudents() {
        return this.registeredStudents;
    }

    /**
     * Add a banned student to the camp.
     *
     * @param student The Student object to be added as a banned student.
     */
    public void addBannedStudents(Student student) {
        this.bannedStudents.add(student);
    }

    /**
     * Get the list of banned students for the camp.
     *
     * @return The list of banned students.
     */
    public ArrayList<Student> getBannedStudents() {
        return this.bannedStudents;
    }

    /**
     * Add a registered CampCommittee to the camp.
     *
     * @param campCommittee The CampCommittee object to be added as a registered committee member.
     */
    public void addRegisteredCampCommittee(CampCommittee campCommittee) {
        this.registeredCampCommittees.add(campCommittee);
    }

    /**
     * Get the list of registered CampCommittees for the camp.
     *
     * @return The list of registered CampCommittees.
     */
    public ArrayList<CampCommittee> getRegisteredCampCommittee() {
        return this.registeredCampCommittees;
    }

    /**
     * Add an enquiry to the enquiries list for the camp.
     *
     * @param enquiry The Enquiries object to be added to the enquiries list.
     */
    public void addEnquiriesList(Enquiries enquiry) {
        enquiriesList.add(enquiry);
    }

    /**
     * Add a suggestion to the suggestions list for the camp.
     *
     * @param suggestion The Suggestion object to be added to the suggestions list.
     */
    public void addSuggestionsList(Suggestion suggestion) {
        suggestionsList.add(suggestion);
    }

    /**
     * Get the list of enquiries for the camp.
     *
     * @return The list of enquiries.
     */
    public ArrayList<Enquiries> getEnquiriesList() {
        return this.enquiriesList;
    }

    /**
     * Get the list of suggestions for the camp.
     *
     * @return The list of suggestions.
     */
    public ArrayList<Suggestion> getSuggestionsList() {
        return this.suggestionsList;
    }
}
