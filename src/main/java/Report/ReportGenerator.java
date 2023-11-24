package Report;

import java.util.ArrayList;
import Camp.Camp;

/**
 * The ReportGenerator interface outlines the structure for generating reports based on camp data.
 */

public interface ReportGenerator {
    
	/**
     * Generates a report based on the provided list of camps.
     *
     * @param campList The list of camps used to generate the report.
     */
	public void generateReport(ArrayList<Camp> campList);
}