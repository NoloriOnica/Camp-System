package Login;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffViewsCamps{
    private CampsFilter campsFilter;

    public StaffViewsCamps(){
        campsFilter = new CampsFilterImplementation();
    }

    private ArrayList<Camp> filterSelection( ArrayList<Camp> campList){
        ArrayList<Camp> filteredCamps;
        Scanner sc = new Scanner(System.in);
        int choice , index;
        System.out.println("Do you want to filter to view Camps?");
        System.out.println("1)Yes\n2)No");
        index = sc.nextInt();
        if(index == 2){
            choice = 3;
        }else if(index == 1){
            System.out.println("Choose the filter types?");
            System.out.println("1)By Date Before\n2)By Location");
            choice = sc.nextInt();
        }else{
            System.out.println("Invalid Choice");
            return null;
        }
        switch(choice){
            case 1:
                System.out.println("Enter Start Date (YYYY-MM-DD):");
                LocalDate desiredDate = LocalDate.parse(sc.nextLine());
                filteredCamps = this.campsFilter.byDate(campList,desiredDate);
                break;
            case 2:
                System.out.println("Enter desired Location");
                String desiredLocation = sc.nextLine();
                filteredCamps = this.campsFilter.byLocation(campList,desiredLocation);
                break;
            case 3:
                filteredCamps = this.campsFilter.sortByAlphabet(campList);
                break;
            default:
                System.out.println("Invalid Choice");
                return null;
        } 
        return filteredCamps;
    }

    public void viewCamps(ArrayList<Camp> campList){  
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if(filteredSortedCamps.isEmpty() || filteredSortedCamps ==null){
            System.out.println("NOT FOUND");
            return;
        }
        for(Camp camp : filteredSortedCamps){
            int i = 1;
            System.out.println((++i) + ") " + camp.getCampInfo().getCampName()+ " Start Date: "+ camp.getCampInfo().getStartDate()
            + " Location: "+camp.getCampInfo().getLocation());
        }
            
    }   

    public void viewOwnCamps(ArrayList<Camp> campList){ 
        ArrayList<Camp> filteredSortedCamps = filterSelection(campList);
        if(filteredSortedCamps.isEmpty() || filteredSortedCamps ==null){
            System.out.println("NOT FOUND");
            return;
        }
        for(Camp camp : filteredSortedCamps){
            int i = 1;
            System.out.println((++i) + ") " + camp.getCampInfo().getCampName()+ " Start Date: "+ camp.getCampInfo().getStartDate()
            + " Location: "+camp.getCampInfo().getLocation());
        }
    }
}