package com.crusherd.speedportinfo.html;

public class SpeedportPhoneEntry {

    private int id;
    private String phoneNumber = "";
    private int failReason;
    private String status = "";
    private String voipError = "";

    public SpeedportPhoneEntry() {

    }

    public SpeedportPhoneEntry(final int id, final String phoneNumber, final int failReason, final String status,
            final String voipError) {
        this.id = id;
        this.failReason = failReason;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.voipError = voipError;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFailReason() {
        return failReason;
    }

    public void setFailReason(final int failReason) {
        this.failReason = failReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getVoipError() {
        return voipError;
    }

    public void setVoipError(final String voipError) {
        this.voipError = voipError;
    }

}
