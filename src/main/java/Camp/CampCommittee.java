package Camp;
import java.io.Serializable;
import java.util.ArrayList;

import Feedback.EnquiriesController;
import Feedback.Suggestion;
import Feedback.SuggestionsHandler;

import Report.CampReportGenerator;


public class CampCommittee implements Serializable{
    private Camp camp;
	private String name;
	private CampUserGroup campUserGroup;
	private int point = 0;
	private EnquiriesController enquiriesController ;
	private CampReportGenerator campReportGenerator;
	private SuggestionsHandler suggestionsHandler;

	public CampCommittee(String name, Camp camp, CampUserGroup campUserGroup)
	{
		this.name = name;
        this.camp = camp;
		this.campUserGroup = campUserGroup;
		enquiriesController = new EnquiriesController();
		campReportGenerator = new CampReportGenerator();
		suggestionsHandler = new SuggestionsHandler();
	}
	
	public String viewCampDetails() {
		StringBuilder details = new StringBuilder();
		details.append("Camp Name: ").append(this.camp.getCampInfo().getCampName()).append("\n");
		details.append("Start Date: ").append(this.camp.getCampInfo().getStartDate()).append("\n");
		details.append("End Date: ").append(this.camp.getCampInfo().getEndDate()).append("\n");
		details.append("Registration Closing Date: ").append(this.camp.getCampInfo().getRegClosingDate()).append("\n");
		details.append("User Group: ").append(this.camp.getCampInfo().getCampUserGroup().toString()).append("\n");
		details.append("Camp Visibility: ").append(this.camp.getCampInfo().getCampVisibility().toString()).append("\n");
		details.append("Location: ").append(this.camp.getCampInfo().getLocation()).append("\n");
		details.append("Total Slots: ").append(this.camp.getCampInfo().getTotalSlots()).append("\n");
		details.append("Camp Committee Slot: ").append(this.camp.getCampInfo().getCampCommitteeSlot()).append("\n");
		details.append("Description: ").append(this.camp.getCampInfo().getDescription()).append("\n");
		details.append("Staff In Charge ID: ").append(this.camp.getCampInfo().getStaffInChargeID()).append("\n");
	
		// Print to the console
		System.out.println(details.toString());
	
		// Return the details as a string
		return details.toString();
	}
	
	public void viewEnquiries() {
		//view enquiries
		//based on student
        ArrayList<Camp> singleCampHolder = new ArrayList<>(); 
        singleCampHolder.add(this.camp);
        enquiriesController.viewEnquiries(singleCampHolder);
	}
	
	public void replyEnquiries() {
		//reply enquiries
		ArrayList<Camp> singleCampHolder = new ArrayList<>(); 
        singleCampHolder.add(this.camp);
        if(enquiriesController.replyEnquiries(singleCampHolder, this.name))//return boolean
            this.point++;
	}
	
	public void makeSuggestions() {
		suggestionsHandler.makeSuggestions(this.camp, this);
	}

	public ArrayList <Suggestion> viewSuggestions() { //return a list of suggestion that a camp committee can view
        return suggestionsHandler.viewSuggestions(this.camp,this);
	}

	public void editSuggestions() {
	    suggestionsHandler.editSuggestions(this.camp,this);
	}
	
	public void deleteSuggestions() {
	    suggestionsHandler.deleteSuggestions(this.camp, this);
	}

	public void generateReport() {
		ArrayList<Camp> campList = new ArrayList<>();
		campList.add(this.camp);
		this.campReportGenerator.generateCampReport(campList);
		//based on staff
	}

//Below are all the getters and setters
public int getPoints() {
		return point;
	}
public void setPoint(int point){
	this.point = point;
}
public String getName(){
	return this.name;
}
public CampUserGroup getCampUserGroup(){
	return this.campUserGroup;
}
}