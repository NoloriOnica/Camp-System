package CampFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.Serializable;
import Camp.Camp;

/**
 * Abstract class for filtering camps.
 */
public abstract class CampFilter implements Serializable {

    /**
     * Sort the list of camps by alphabetical order of the camp name.
     *
     * @param campList The list of camps to be sorted.
     * @return An ArrayList of camps sorted by the alphabetical order of camp names.
     */
    public static ArrayList<Camp> sortByAlphabet(ArrayList<Camp> campList) {
        ArrayList<Camp> campListCopy = new ArrayList<>(campList);

        // Sort the copy using a custom comparator
        Collections.sort(campListCopy, Comparator.comparing(camp -> camp.getCampInfo().getCampName()));
        // Return the sorted copy
        return campListCopy;
    }

    /**
     * Abstract method to apply a filter to the camp list.
     *
     * @param campList The list of camps to be filtered.
     * @return An ArrayList of camps based on the applied filter.
     */
    public abstract ArrayList<Camp> applyCampFilter(ArrayList<Camp> campList);
}
