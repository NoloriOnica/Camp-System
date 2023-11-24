package Report;

import java.util.ArrayList;

import Camp.Camp;

/**
 * The ReportFilter interface outlines the structure for implementing different filtering methods for camp data.
 */
public interface ReportFilter{
	/**
     * Applies a filter for the report to the provided list of camps based on specific criteria.
     *
     * @param campList The list of camps to be filtered.
     * @return An ArrayList of camps that meet the filtering criteria.
     */
    public ArrayList<Camp> applyFilter(ArrayList<Camp> campList);
}
