package Feedback;

import java.io.Serializable;
import Camp.Camp;

public class Enquiries implements Serializable{
	
    private String enquiryString;
    private boolean isProcessed;
    private String senderId;
    private String senderName;
    private String replierName;
    private String reply;
    private Camp belongedCamp;


    public Enquiries(String studentId, String studentName, Camp belongedCamp) {
        this.senderId = studentId;
        this.senderName = studentName;
        this.isProcessed = false;
        this.belongedCamp = belongedCamp;
    }
    
    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    public String getEnquireString(){
        return this.enquiryString;
    }

    public boolean getProcessState(){
        return this.isProcessed;
    }
    public void setProcessState(boolean state){
        this.isProcessed = state;
    }
    public String getSenderName(){
        return this.senderName;
    }

    public String getSenderId(){
        return this.senderId;
    }

    public void setEnquiryString(String string){
        this.enquiryString = string;
    }

    public void setReply(String string){
        this.reply = string;
    }

    public Camp getCamp(){
        return this.belongedCamp;
    }

    public String toString() {

        String line;
        if (this.isProcessed && reply != null) {
            line = "To Camp "+ belongedCamp.getCampInfo().getCampName()+ "\n"+ "Enquiry {" + enquiryString + ", sender's name = " + senderName+" }\n" +
            "Replied by " + replierName + " Reply: " + reply + ", Reply State = " + isProcessed;
        }else{
            line =  "To Camp "+ belongedCamp.getCampInfo().getCampName()+ "\n" +"Enquiry {" + enquiryString + ", sender's name = " + senderName+" } Reply State = " + isProcessed;
        }
        return line;
    }
}

