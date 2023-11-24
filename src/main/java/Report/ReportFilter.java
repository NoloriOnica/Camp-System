package Report;

import java.util.ArrayList;

import Camp.Camp;


public interface ReportFilter{
    public ArrayList<Camp> applyFilter(ArrayList<Camp> campList);
}
