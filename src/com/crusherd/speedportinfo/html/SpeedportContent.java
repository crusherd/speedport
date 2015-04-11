package com.crusherd.speedportinfo.html;

import java.util.ArrayList;
import java.util.List;

public class SpeedportContent {

    private String deviceName = "";
    private String date = "";
    private String dslState = "";
    private String internetActive = "";
    private String downstream = "";
    private String upstream = "";
    private boolean wlan24Active;
    private boolean wlan5Active;
    private String ssid = "";
    private boolean wpsActive;
    private boolean wlanTOGOActive;
    private String firmware = "";
    private String serial = "";
    private boolean valid;
    List<SpeedportPhoneEntry> phoneEntries = new ArrayList<SpeedportPhoneEntry>();

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getDslState() {
        return dslState;
    }

    public void setDslState(final String dslState) {
        this.dslState = dslState;
    }

    public String getInternetState() {
        return internetActive;
    }

    public void setInternetState(final String internetActive) {
        this.internetActive = internetActive;
    }

    public String getDownstream() {
        return downstream;
    }

    public void setDownstream(final String downstream) {
        this.downstream = downstream;
    }

    public String getUpstream() {
        return upstream;
    }

    public void setUpstream(final String upstream) {
        this.upstream = upstream;
    }

    public boolean isWlan24Active() {
        return wlan24Active;
    }

    public void setWlan24Active(final boolean wlan24Active) {
        this.wlan24Active = wlan24Active;
    }

    public boolean isWlan5Active() {
        return wlan5Active;
    }

    public void setWlan5Active(final boolean wlan5Active) {
        this.wlan5Active = wlan5Active;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(final String ssid) {
        this.ssid = ssid;
    }

    public boolean isWpsActive() {
        return wpsActive;
    }

    public void setWpsActive(final boolean wpsActive) {
        this.wpsActive = wpsActive;
    }

    public boolean isWlanTOGOActive() {
        return wlanTOGOActive;
    }

    public void setWlanTOGOActive(final boolean wlanTOGOActive) {
        this.wlanTOGOActive = wlanTOGOActive;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(final String firmware) {
        this.firmware = firmware;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(final String serial) {
        this.serial = serial;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    public List<SpeedportPhoneEntry> getPhoneEntries() {
        return phoneEntries;
    }

    public void setPhoneEntries(final List<SpeedportPhoneEntry> phoneEntries) {
        this.phoneEntries = phoneEntries;
    }
}
