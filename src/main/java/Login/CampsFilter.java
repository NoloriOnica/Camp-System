package Login;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CampsFilter {
    public ArrayList<Camp> sortByAlphabet(ArrayList<Camp> campList);

    public ArrayList<Camp> byDate(ArrayList<Camp> campList, LocalDate desiredDate);

    public ArrayList<Camp> byLocation(ArrayList<Camp> campList, String desiredLocation);

}