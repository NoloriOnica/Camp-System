package Feedback;

public class Enquiries {
    private String enquiry;
    private boolean isProcessed;
<<<<<<< HEAD
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
=======
    private String sendertName;

    public Enquiries(String studentName) {
        this.sendertName = studentName;
        isProcessed = false;
    }
    
    public boolean getProcessState(){
        return isProcessed;
    }
    public void setProcessState(boolean state){
        isProcessed = state;
    }
    public String getSenderName(){
        return sendertName;
>>>>>>> d1526f33a465bfbd7f6b99f7d459f10f43b0a0fa
    }

    public void setEnquiry(String string){
        this.enquiry = string;
    }

<<<<<<< HEAD
    public void setReply(String string){
        this.reply = string;
    }

    public String toString() {

        String line;
        if (this.isProcessed && reply != null) {
            line = "Enquiry {" + enquiry + ", name = " + senderName+" }\n" +
            "Replied by " + replierName + "Reply: " + reply + ", Reply State = " + isProcessed + "\n";
        }else{
            line =  "Enquiry {" + enquiry + ", name = " + senderName+" } Reply State = " + isProcessed + "\n";
        }
        return line;
    }
}

=======
    public String toString() {

        String line =  "Enquiry {" + enquiry + ", name = " + sendertName+" } Reply State = " + isProcessed + "\n";
        return line;
    }
}
>>>>>>> d1526f33a465bfbd7f6b99f7d459f10f43b0a0fa
