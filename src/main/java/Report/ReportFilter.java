package Report;

import java.util.ArrayList;

import Camp.Camp;


public interface ReportFilter{
    public ArrayList<Camp> filterAttendee(ArrayList<Camp> camplist, String attendeeName);

    public ArrayList<Camp> filterCampCommittee(ArrayList<Camp> camplist, String CampCommitteeName);
}
