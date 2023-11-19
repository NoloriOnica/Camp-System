package Login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDate;

public class CampsFilterImplementation implements CampsFilter{
    
    public ArrayList<Camp> sortByAlphabet(ArrayList<Camp> campList) {  //Sort the camp by alphabetical order of the camp name
        ArrayList<Camp> campListCopy = new ArrayList<>(campList);

        // Sort the copy using a custom comparator
        Collections.sort(campListCopy, Comparator.comparing(camp -> camp.getCampInfo().getCampName()));
        // Return the sorted copy
        return campListCopy;
    }

    public ArrayList<Camp> byDate(ArrayList<Camp> campList, LocalDate desiredDate){ //select the camp that held before the desired date
        
        ArrayList<Camp> filteredCamp = new ArrayList<>();
        // Sort the copy using a custom comparator
        for(Camp camp : campList){
            if(camp.getCampInfo().getStartDate().isBefore(desiredDate))
                filteredCamp.add(camp);
        }
        // Return the sorted copy
        ArrayList<Camp> filteredSortedCamps = sortByAlphabet(filteredCamp);
        return filteredSortedCamps;
    }

    public ArrayList<Camp> byLocation(ArrayList<Camp> campList, String desiredLocation){ //Select the camp that have the desired location
        ArrayList<Camp> filteredCamp = new ArrayList<>();
        // Sort the copy using a custom comparator
        for(Camp camp : campList){
            if(camp.getCampInfo().getLocation().equals(desiredLocation))
                filteredCamp.add(camp);
        }
        // Return the sorted copy
        ArrayList<Camp> filteredSortedCamps = sortByAlphabet(filteredCamp);
        return filteredSortedCamps;
    }
}