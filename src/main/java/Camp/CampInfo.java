package Camp;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents information about a camp.
 */
public class CampInfo implements Serializable {

    // Camp Information

    /** Name of the camp. */
    private String campName;

    /** Start date of the camp. */
    private LocalDate startDate;

    /** End date of the camp. */
    private LocalDate endDate;

    /** Closing date for camp registration. */
    private LocalDate regClosingDate;

    /** User group associated with the camp. */
    private String campUserGroup;

    /** Visibility status of the camp. */
    private CampVisibility campVisibility;

    /** Location of the camp. */
    private String location;

    /** Total slots available for the camp. */
    private int totalSlots;

    /** Slots allocated for the camp committee. */
    private int campCommitteeSlot;

    /** Description of the camp. */
    private String description;

    /** ID of the staff in charge of the camp. */
    private String staffInChargeID;

    /**
     * Constructor to initialize CampInfo object.
     * 
     * @param campName            Name of the camp.
     * @param startDate           Start date of the camp.
     * @param endDate             End date of the camp.
     * @param regClosingDate      Closing date for camp registration.
     * @param campUserGroup       User group associated with the camp.
     * @param campVisibility      Visibility status of the camp.
     * @param location            Location of the camp.
     * @param totalSlots          Total slots available for the camp.
     * @param campCommitteeSlot   Slots allocated for the camp committee.
     * @param description         Description of the camp.
     * @param staffInChargeID     ID of the staff in charge of the camp.
     */
    public CampInfo(String campName, LocalDate startDate, LocalDate endDate, LocalDate regClosingDate,
                    String campUserGroup, CampVisibility campVisibility, String location, int totalSlots,
                    int campCommitteeSlot, String description, String staffInChargeID) {
        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regClosingDate = regClosingDate;
        this.campUserGroup = campUserGroup;
        this.campVisibility = campVisibility;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlot = campCommitteeSlot;
        this.description = description;
        this.staffInChargeID = staffInChargeID;
    }

    /**
     * Get a string representation of the CampInfo object.
     *
     * @return A string representation of camp information.
     */
    @Override
    public String toString() {
        return "Camp Name: " + campName +
               "\nStart Date: " + startDate +
               "\nEnd Date: " + endDate +
               "\nRegistration Closing Date: " + regClosingDate +
               "\nUser Group: " + campUserGroup +
               "\nCamp Visibility: " + campVisibility +
               "\nLocation: " + location +
               "\nTotal Slots: " + totalSlots +
               "\nCamp Committee Slot: " + campCommitteeSlot +
               "\nDescription: " + description +
               "\nStaff In Charge ID: " + staffInChargeID;
    }

    // Below are all getters and setters

    // (Getter and Setter methods with Javadoc comments)

    /**
     * Get the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Set the name of the camp.
     *
     * @param campName The name of the camp to be set.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Get the start date of the camp.
     *
     * @return The start date of the camp.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the camp.
     *
     * @param startDate The start date of the camp to be set.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end date of the camp.
     *
     * @return The end date of the camp.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Set the end date of the camp.
     *
     * @param endDate The end date of the camp to be set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the closing date for camp registration.
     *
     * @return The closing date for camp registration.
     */
    public LocalDate getRegClosingDate() {
        return regClosingDate;
    }

    /**
     * Set the closing date for camp registration.
     *
     * @param regClosingDate The closing date for camp registration to be set.
     */
    public void setRegClosingDate(LocalDate regClosingDate) {
        this.regClosingDate = regClosingDate;
    }

    /**
     * Get the user group associated with the camp.
     *
     * @return The user group associated with the camp.
     */
    public String getCampUserGroup() {
        return this.campUserGroup;
    }

    /**
     * Set the user group associated with the camp.
     *
     * @param campUserGroup The user group associated with the camp to be set.
     */
    public void setCampUserGroup(String campUserGroup) {
        this.campUserGroup = campUserGroup;
    }

    /**
     * Get the visibility status of the camp.
     *
     * @return The visibility status of the camp.
     */
    public CampVisibility getCampVisibility() {
        return this.campVisibility;
    }

    /**
     * Set the visibility status of the camp.
     *
     * @param campVisibility The visibility status of the camp to be set.
     */
    public void setCampVisibility(CampVisibility campVisibility) {
        this.campVisibility = campVisibility;
    }

    /**
     * Get the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of the camp.
     *
     * @param location The location of the camp to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the total slots available for the camp.
     *
     * @return The total slots available for the camp.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Set the total slots available for the camp.
     *
     * @param totalSlots The total slots available for the camp to be set.
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Get the slots allocated for the camp committee.
     *
     * @return The slots allocated for the camp committee.
     */
    public int getCampCommitteeSlot() {
        return campCommitteeSlot;
    }

    /**
     * Set the slots allocated for the camp committee.
     *
     * @param campCommitteeSlot The slots allocated for the camp committee to be set.
     */
    public void setCampCommitteeSlot(int campCommitteeSlot) {
        this.campCommitteeSlot = campCommitteeSlot;
    }

    /**
     * Get the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the camp.
     *
     * @param description The description of the camp to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the ID of the staff in charge of the camp.
     *
     * @return The ID of the staff in charge of the camp.
     */
    public String getStaffInChargeID() {
        return staffInChargeID;
    }

    /**
     * Set the ID of the staff in charge of the camp.
     *
     * @param staffInChargeID The ID of the staff in charge of the camp to be set.
     */
    public void setStaffInChargeID(String staffInChargeID) {
        this.staffInChargeID = staffInChargeID;
    }

}
