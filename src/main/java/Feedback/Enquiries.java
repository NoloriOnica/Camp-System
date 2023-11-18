package Feedback;

import java.io.Serializable;

public class Enquiries implements Serializable{
    private String enquiry;
    private boolean isProcessed;
    private String senderName;
    private String replierName;
    private String reply;


    public Enquiries(String studentName) {
        this.senderName = studentName;
        this.isProcessed = false;
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

    public void setEnquiry(String string){
        this.enquiry = string;
    }

    public void setReply(String string){
        this.reply = string;
    }

    public String toString() {

        String line;
        if (this.isProcessed && reply != null) {
            line = "Enquiry {" + enquiry + ", sender's name = " + senderName+" }\n" +
            "Replied by " + replierName + "Reply: " + reply + ", Reply State = " + isProcessed;
        }else{
            line =  "Enquiry {" + enquiry + ", sender's name = " + senderName+" } Reply State = " + isProcessed;
        }
        return line;
    }
}

