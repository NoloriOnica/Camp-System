package Feedback;

import java.io.Serializable;

public class Suggestion implements Serializable{
    private String suggetionString;
    private boolean isApproved;
    private String sendertName;

    public Suggestion(String committeeName) {
        this.sendertName = committeeName;
        isApproved = false;
    }
    
    public boolean getApprovalState(){
        return isApproved;
    }
    public void setApprovalState(boolean state){
        isApproved = state;
    }
    public String getSenderName(){
        return sendertName;
    }

    public void setSuggestion(String string){
        this.suggetionString = string;
    }

    public String toString() {

        String line =  "Suggestion {" + suggetionString + ", name = " + sendertName+" } Approval State = " + isApproved + "\n";
        return line;
    }
}
