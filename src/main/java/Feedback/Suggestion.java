package Feedback;

import java.io.Serializable;

/**
 * Represents a suggestion made by a committee member.
 */
public class Suggestion implements Serializable {
    private String suggestionString;
    private boolean isApproved;
    private String senderId;
    private String senderName;

    /**
     * Constructor for the Suggestion class.
     *
     * @param committeeId   The ID of the committee member making the suggestion.
     * @param committeeName The name of the committee member making the suggestion.
     */
    public Suggestion(String committeeId, String committeeName) {
        this.senderId = committeeId;
        this.senderName = committeeName;
        isApproved = false;
    }

    /**
     * Retrieves the approval state of the suggestion.
     *
     * @return True if the suggestion is approved, false otherwise.
     */
    public boolean getApprovalState() {
        return this.isApproved;
    }

    /**
     * Sets the approval state of the suggestion.
     *
     * @param state The state to be set for the approval of the suggestion.
     */
    public void setApprovalState(boolean state) {
        this.isApproved = state;
    }

    /**
     * Retrieves the name of the sender of the suggestion.
     *
     * @return The name of the sender.
     */
    public String getSenderName() {
        return this.senderName;
    }

    /**
     * Retrieves the ID of the sender of the suggestion.
     *
     * @return The ID of the sender.
     */
    public String getSenderId() {
        return this.senderId;
    }

    /**
     * Sets the suggestion text.
     *
     * @param string The suggestion text to be set.
     */
    public void setSuggestion(String string) {
        this.suggestionString = string;
    }

    /**
     * Provides a string representation of the suggestion.
     *
     * @return A string displaying the suggestion text, sender's name, and approval state.
     */
    public String toString() {
        return "Suggestion {" + this.suggestionString + ",Sender name = " + this.senderName + "} Approval State = " + this.isApproved;
    }
}
