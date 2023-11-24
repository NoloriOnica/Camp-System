package CampCommittee;

import java.io.Serializable;
import java.util.ArrayList;

import Camp.Camp;
import Feedback.EnquiriesController;
import Feedback.Suggestion;
import Feedback.SuggestionsHandler;
import Report.GenerateReport;
import Student.Student;

/**
 * Represents a member of the camp committee.
 */
public class CampCommittee extends Student implements Serializable {
    private Camp camp;
    private int point = 0;
    private EnquiriesController enquiriesController;
    private SuggestionsHandler suggestionsHandler;
    private GenerateReport generateReport;

    /**
     * Constructor to initialize a CampCommittee object.
     *
     * @param userID    User ID of the committee member.
     * @param name      Name of the committee member.
     * @param email     Email of the committee member.
     * @param faculty   Faculty of the committee member.
     * @param userType  Type of the committee member.
     * @param camp      Camp associated with the committee member.
     */
    public CampCommittee(String userID, String name, String email, String faculty, String userType, Camp camp) {
        super(userID, name, email, faculty, userType);
        this.camp = camp;
        this.enquiriesController = new EnquiriesController();
        this.suggestionsHandler = new SuggestionsHandler();
        this.generateReport = new GenerateReport();
    }

    /**
     * View enquiries related to the camp.
     */
    public void viewCampEnquiries() {
        ArrayList<Camp> singleCampHolder = new ArrayList<>();
        singleCampHolder.add(this.camp);
        enquiriesController.viewEnquiries(singleCampHolder);
    }

    /**
     * Reply to camp enquiries.
     */
    public void replyCampEnquiries() {
        ArrayList<Camp> singleCampHolder = new ArrayList<>();
        singleCampHolder.add(this.camp);
        if (enquiriesController.replyEnquiries(singleCampHolder, super.getName())) // Returns boolean
            this.point++;
    }

    /**
     * Make suggestions for the camp.
     */
    public void makeSuggestions() {
        suggestionsHandler.makeSuggestions(this.camp, this);
    }

    /**
     * View suggestions related to the camp committee.
     *
     * @return A list of suggestions.
     */
    public ArrayList<Suggestion> viewSuggestions() {
        return suggestionsHandler.viewSuggestions(this);
    }

    /**
     * Edit suggestions related to the camp.
     */
    public void editSuggestions() {
        suggestionsHandler.editSuggestions(this.camp, this);
    }

    /**
     * Delete suggestions related to the camp.
     */
    public void deleteSuggestions() {
        suggestionsHandler.deleteSuggestions(this.camp, this);
    }

    /**
     * Generate a report for the camp committee.
     */
    public void generateReport() {
        ArrayList<Camp> campList = new ArrayList<>();
        campList.add(this.camp);
        boolean result = this.generateReport.reportSelection(campList, this);
        if (!result) {
            System.out.println("NOT FOUND!");
        }
    }

    // Below are all the getters and setters

    /**
     * Get the points of the camp committee member.
     *
     * @return The points earned by the committee member.
     */
    public int getPoints() {
        return point;
    }

    /**
     * Set the points of the camp committee member.
     *
     * @param point The points to set for the committee member.
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Get the camp associated with the committee member.
     *
     * @return The camp associated with the committee member.
     */
    public Camp getCamp() {
        return this.camp;
    }
}
