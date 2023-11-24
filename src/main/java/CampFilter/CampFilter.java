package CampFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.Serializable;

import Camp.Camp;

public abstract class CampFilter implements Serializable{

    public static ArrayList<Camp> sortByAlphabet(ArrayList<Camp> campList) {  //Sort the camp by alphabetical order of the camp name
        ArrayList<Camp> campListCopy = new ArrayList<>(campList);

        // Sort the copy using a custom comparator
        Collections.sort(campListCopy, Comparator.comparing(camp -> camp.getCampInfo().getCampName()));
        // Return the sorted copy
        return campListCopy;
    }

    public abstract ArrayList<Camp> applyCampFilter(ArrayList<Camp> campList);
}
