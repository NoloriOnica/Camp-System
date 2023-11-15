package Feedback;

public class Enquiries {
    private String enquiry;
    private boolean isProcessed;
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
    }

    public void setEnquiry(String string){
        this.enquiry = string;
    }

    public String toString() {

        String line =  "Enquiry {" + enquiry + ", name = " + sendertName+" } Reply State = " + isProcessed + "\n";
        return line;
    }
}
