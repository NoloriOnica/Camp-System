package Feedback;

import java.io.Serializable;

public class Suggestion implements Serializable{
    private String suggetionString;
    private boolean isApproved;
    private String senderId;
    private String senderName;

    public Suggestion(String committeeId ,String committeeName) {
        this.senderId = committeeId;
        this.senderName = committeeName;
        isApproved = false;
    }
    
    public boolean getApprovalState(){
        return this.isApproved;
    }
    public void setApprovalState(boolean state){
        this.isApproved = state;
    }
    public String getSenderName(){
        return this.senderName;
    }

    public String getSenderId(){
        return this.senderId;
    }

    public void setSuggestion(String string){
        this.suggetionString = string;
    }

    public String toString() {

        String line =  "Suggestion {" + this.suggetionString + ", name = " + this.senderName+" } Approval State = " + this.isApproved;
        return line;
    }
}
