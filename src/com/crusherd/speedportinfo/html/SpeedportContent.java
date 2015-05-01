package com.crusherd.speedportinfo.html;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all data, which a Speedport delivers.
 *
 * @author Robert Danczak
 */
public class SpeedportContent {

    private String deviceName = "";
    private String deviceType = "";
    private String date = "";
    private String dslState = "";
    private String internetState = "";
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
    private List<SpeedportPhoneEntry> phoneEntries = new ArrayList<SpeedportPhoneEntry>();

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(final String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getDslState() {
        return this.dslState;
    }

    public void setDslState(final String dslState) {
        this.dslState = dslState;
    }

    public String getInternetState() {
        return this.internetState;
    }

    public void setInternetState(final String internetState) {
        this.internetState = internetState;
    }

    public String getDownstream() {
        return this.downstream;
    }

    public void setDownstream(final String downstream) {
        this.downstream = downstream;
    }

    public String getUpstream() {
        return this.upstream;
    }

    public void setUpstream(final String upstream) {
        this.upstream = upstream;
    }

    public boolean isWlan24Active() {
        return this.wlan24Active;
    }

    public void setWlan24Active(final boolean wlan24Active) {
        this.wlan24Active = wlan24Active;
    }

    public boolean isWlan5Active() {
        return this.wlan5Active;
    }

    public void setWlan5Active(final boolean wlan5Active) {
        this.wlan5Active = wlan5Active;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(final String ssid) {
        this.ssid = ssid;
    }

    public boolean isWpsActive() {
        return this.wpsActive;
    }

    public void setWpsActive(final boolean wpsActive) {
        this.wpsActive = wpsActive;
    }

    public boolean isWlanToGoActive() {
        return this.wlanTOGOActive;
    }

    public void setWlanTOGOActive(final boolean wlanTOGOActive) {
        this.wlanTOGOActive = wlanTOGOActive;
    }

    public String getFirmware() {
        return this.firmware;
    }

    public void setFirmware(final String firmware) {
        this.firmware = firmware;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(final String serial) {
        this.serial = serial;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(final boolean valid) {
        this.valid = valid;
    }

    public List<SpeedportPhoneEntry> getPhoneEntries() {
        return this.phoneEntries;
    }

    public void setPhoneEntries(final List<SpeedportPhoneEntry> phoneEntries) {
        this.phoneEntries = phoneEntries;
    }
}
