package Report;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import Camp.Camp;
import CampCommittee.CampCommittee;

/**
 * Implementation of the ReportFilter interface for filtering camps by Camp Committee's name.
 */
public class FilterCampCommittee implements ReportFilter,Serializable{
    
	/**
     * Filters the provided list of camps by Camp Committee's name.
     *
     * @param campList The list of camps to be filtered.
     * @return An ArrayList of camps filtered by the desired Camp Committee's name.
     */
	public ArrayList<Camp> applyFilter(ArrayList<Camp> campList){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Camp Committee's name:");
        String desiredCampCommitteeName = sc.nextLine();
        ArrayList<Camp> filteredCamps = new ArrayList<>();
        for(Camp camp : campList){
            ArrayList<CampCommittee> CampCommittees = camp.getRegisteredCampCommittee();
            for(CampCommittee campCommittee : CampCommittees){
                if(campCommittee.getName().equals(desiredCampCommitteeName)){
                    filteredCamps.add(camp);
                }
            }
        }
        return filteredCamps;
    }
}
