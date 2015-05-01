package com.crusherd.speedportinfo.html;

/**
 * Contains the data for a phone entry.
 *
 * @author Robert Danczak
 */
public class SpeedportPhoneEntry {

    private int id;
    private String phoneNumber = "";
    private int failReason;
    private String status = "";
    private String voipError = "";

    /**
     * Creates an empty {@link SpeedportPhoneEntry}.
     */
    public SpeedportPhoneEntry() {

    }

    /**
     * Creates an {@link SpeedportPhoneEntry} with the given data.
     *
     * @param id
     *            - phone id in the Speedport
     * @param phoneNumber
     *            - associated number of the phone
     * @param failReason
     *            - reason for not connected
     * @param status
     *            - current status of the phone
     * @param voipError
     *            - VOIP related error
     */
    public SpeedportPhoneEntry(final int id, final String phoneNumber, final int failReason, final String status,
            final String voipError) {
        this.id = id;
        this.failReason = failReason;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.voipError = voipError;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFailReason() {
        return this.failReason;
    }

    public void setFailReason(final int failReason) {
        this.failReason = failReason;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getVoipError() {
        return this.voipError;
    }

    public void setVoipError(final String voipError) {
        this.voipError = voipError;
    }

}
