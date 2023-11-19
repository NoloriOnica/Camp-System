package Login;

import java.util.ArrayList;


public interface ReportFilter {
    public ArrayList<Camp> filterAttendee(ArrayList<Camp> camplist, String attendeeName);

    public ArrayList<Camp> filterCampCommittee(ArrayList<Camp> camplist, String CampCommitteeName);
}
