package com.crusherd.speedportinfo.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.SpeedportContent;

/**
 * Class to collect information from a Speedport W724V router.
 * @author Robert Danczak
 *
 */
public class SpeedportW724V extends SpeedportHandler {

    /**
     * Default constructor sets address to Speedport router.
     */
    public SpeedportW724V() {
        super("http://speedport.ip/data/Status.json");
    }

    /*
     * (non-Javadoc)
     * @see com.crusherd.speedportinfo.model.SpeedportHandler#checkAndProcess()
     */
    @Override
    public SpeedportContent checkAndProcess() {
        HttpURLConnection con = null;
        try {
            final URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                return processWithoutReader(con);
            }
            else {
                return processWithReader(con);
            }
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return new SpeedportContent();
    }

    private SpeedportContent processWithoutReader(final HttpURLConnection con) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SpeedportContent processWithReader(final HttpURLConnection con) throws IOException {
        final JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));
        final SpeedportContent content = new SpeedportContent();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            reader.skipValue();
            reader.skipValue();
            reader.skipValue();
            final String varID = reader.nextString();
            reader.skipValue();
            final String varValue = reader.nextString();
            reader.endObject();

            if (varID.equals(Constants.DEVICE_NAME)) {
                content.setDeviceName(varValue);
            }
            else if (varID.equals(Constants.DATETIME)) {
                content.setDate(varValue);
            }
            else if (varID.equals(Constants.DSL_LINK_STATUS)) {
                content.setDslState(varValue);
            }
            else if (varID.equals(Constants.ONLINE_STATUS)) {
                content.setInternetState(varValue);
            }
            else if (varID.equals(Constants.DSL_DOWNSTREAM)) {
                content.setDownstream(varValue);
            }

            else if (varID.equals(Constants.DSL_UPSTREAM)) {
                content.setUpstream(varValue);
            }
            else if (varID.equals(Constants.USE_WLAN)) {
                if ("0".equals(varValue)) {
                    content.setWlan24Active(false);
                }
                else {
                    content.setWlan24Active(true);
                }
            }
            else if (varID.equals(Constants.USE_WLAN_5GHZ)) {
                if ("0".equals(varValue)) {
                    content.setWlan5Active(false);
                }
                else {
                    content.setWlan5Active(true);
                }
            }
            else if (varID.equals(Constants.WLAN_SSID)) {
                content.setSsid(varValue);
            }
            else if (varID.contains(Constants.USE_WPS)) {
                if ("0".equals(varValue)) {
                    content.setWpsActive(false);
                }
                else {
                    content.setWpsActive(true);
                };
            }
            else if (varID.equals(Constants.HSFON_STATUS)) {
                if ("0".equals(varValue)) {
                    content.setWlanTOGOActive(false);
                }
                else {
                    content.setWlanTOGOActive(true);
                };
            }
            else if (varID.equals(Constants.FIRMWARE_VERSION)) {
                content.setFirmware(varValue);
            }
            else if (varID.equals(Constants.SERIAL_NUMBER)) {
                content.setSerial(varValue);
            }
        }
        reader.endArray();
        reader.close();
        content.setValid(true);
        return content;
    }
}
