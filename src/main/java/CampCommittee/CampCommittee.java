package CampCommittee;
import java.io.Serializable;
import java.util.ArrayList;


import Camp.Camp;
import Feedback.EnquiriesController;
import Feedback.Suggestion;
import Feedback.SuggestionsHandler;

import Report.GenerateReport;
import Student.Student;

public class CampCommittee extends Student implements Serializable{
    private Camp camp;
	private int point = 0;
	private EnquiriesController enquiriesController ;
	private SuggestionsHandler suggestionsHandler;
	private GenerateReport generateReport;

	public CampCommittee(String userID, String name, String email, String faculty, String userType, Camp camp)
	{
		super(userID,name,email,faculty,userType);
        this.camp = camp;
		this.enquiriesController = new EnquiriesController();
		this.suggestionsHandler = new SuggestionsHandler();
		this.generateReport = new GenerateReport();
	}

	public void viewCampEnquiries() {
		//view enquiries
		//based on student
		ArrayList<Camp> singleCampHolder = new ArrayList<>();
        singleCampHolder.add(this.camp);
        enquiriesController.viewEnquiries(singleCampHolder);
	}
	
	public void replyCampEnquiries() {
		//reply enquiries
		ArrayList<Camp> singleCampHolder = new ArrayList<>(); 
        singleCampHolder.add(this.camp);
        if(enquiriesController.replyEnquiries(singleCampHolder, super.getName()))//return boolean
            this.point++;
	}
	
	public void makeSuggestions() {
		suggestionsHandler.makeSuggestions(this.camp, this);
	}

	public ArrayList <Suggestion> viewSuggestions() { //return a list of suggestion that a camp committee can view
        return suggestionsHandler.viewSuggestions(this);
	}
	
	public void editSuggestions() {
	    suggestionsHandler.editSuggestions(this.camp,this);
	}
	
	public void deleteSuggestions() {
	    suggestionsHandler.deleteSuggestions(this.camp, this);
	}

	public void generateReport(){
		ArrayList<Camp> campList = new ArrayList<>();
		campList.add(this.camp);
		boolean result = this.generateReport.reportSelection(campList, this);
		if(!result){
            System.out.println("NOT FOUND!");
        }
	}
//Below are all the getters and setters

public int getPoints() {
		return point;
	}
public void setPoint(int point){
	this.point = point;
}

public Camp getCamp(){
	return this.camp;
}
}