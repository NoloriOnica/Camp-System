package Camp;
import java.io.Serializable;
import java.util.ArrayList;

import Feedback.Enquiries;
import Feedback.EnquiriesController;
import Feedback.Suggestion;
import Feedback.SuggestionsHandler;

import Report.CampReportGenerator;


public class CampCommittee implements Serializable{
    private Camp camp;
	private String name;
	private String faculty;
	private int point = 0;
	private EnquiriesController enquiriesController ;
	private CampReportGenerator campReportGenerator;
	private SuggestionsHandler suggestionsHandler;
	private ArrayList<Suggestion> suggestionsList;

	public CampCommittee(String name, Camp camp, String faculty)
	{
		this.name = name;
        this.camp = camp;
		this.faculty = faculty;
		enquiriesController = new EnquiriesController();
		campReportGenerator = new CampReportGenerator();
		suggestionsHandler = new SuggestionsHandler();
		this.suggestionsList = new ArrayList<Suggestion>();
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
	
	public void makeSuggestions(Camp camp) {
		suggestionsHandler.makeSuggestions(camp, this);
	}
	public ArrayList<Suggestion> getSuggestionsList()
	{
		return this.suggestionsList;
	}

	public ArrayList <Suggestion> viewSuggestions(Camp camp) { //return a list of suggestion that a camp committee can view
        return suggestionsHandler.viewSuggestions(camp,this);
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
public String getFaculty(){
	return this.faculty;
}
}