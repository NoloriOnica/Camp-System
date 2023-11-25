package Feedback;

import java.io.Serializable;
import Camp.Camp;

/**
 * Represents an enquiry related to a camp.
 */
public class Enquiries implements Serializable {

    private String enquiryString;
    private boolean isProcessed;
    private String senderId;
    private String senderName;
    private String replierName;
    private String reply;
    private Camp belongedCamp;

    /**
     * Constructor to create an Enquiries object.
     *
     * @param studentId    ID of the student sending the enquiry.
     * @param studentName  Name of the student sending the enquiry.
     * @param belongedCamp Camp associated with the enquiry.
     */
    public Enquiries(String studentId, String studentName, Camp belongedCamp) {
        this.senderId = studentId;
        this.senderName = studentName;
        this.isProcessed = false;
        this.belongedCamp = belongedCamp;
    }

    /**
     * Set the name of the person who replied to the enquiry.
     *
     * @param replierName Name of the person replying to the enquiry.
     */
    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    /**
     * Get the enquiry string.
     *
     * @return The enquiry string.
     */
    public String getEnquiryString() {
        return this.enquiryString;
    }

    /**
     * Get the process state of the enquiry.
     *
     * @return The process state of the enquiry.
     */
    public boolean getProcessState() {
        return this.isProcessed;
    }

    /**
     * Set the process state of the enquiry.
     *
     * @param state The state to set for the enquiry.
     */
    public void setProcessState(boolean state) {
        this.isProcessed = state;
    }

    /**
     * Get the sender's name.
     *
     * @return The sender's name.
     */
    public String getSenderName() {
        return this.senderName;
    }

    /**
     * Get the sender's ID.
     *
     * @return The sender's ID.
     */
    public String getSenderId() {
        return this.senderId;
    }

    /**
     * Set the enquiry string.
     *
     * @param string The enquiry string to set.
     */
    public void setEnquiryString(String string) {
        this.enquiryString = string;
    }

    /**
     * Set the reply for the enquiry.
     *
     * @param string The reply string to set.
     */
    public void setReply(String string) {
        this.reply = string;
    }

    /**
     * Get the camp associated with the enquiry.
     *
     * @return The camp associated with the enquiry.
     */
    public Camp getCamp() {
        return this.belongedCamp;
    }

    /**
     * Generates a string representation of the enquiry.
     *
     * @return A formatted string representation of the enquiry.
     */
    public String toString() {
        String line;
        if (this.isProcessed && reply != null) {
            line = "To Camp " + belongedCamp.getCampInfo().getCampName() + "\n" + "Enquiry {"
                    + enquiryString + ", sender's name = " + senderName + " }\n" + "Replied by "
                    + replierName + ", Reply: " + reply + ", Reply State = " + isProcessed;
        } else {
            line = "To Camp " + belongedCamp.getCampInfo().getCampName() + "\n" + "Enquiry {"
                    + enquiryString + ", sender's name = " + senderName + " } Reply State = " + isProcessed;
        }
        return line;
    }
}
