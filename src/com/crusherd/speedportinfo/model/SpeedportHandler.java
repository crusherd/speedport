package com.crusherd.speedportinfo.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Build;
import android.util.Log;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.SpeedportContent;

/**
 * Parent class for each Speedport model. Children should extend this class.
 *
 * @author Robert Danczak
 */
public abstract class SpeedportHandler {

    /**
     * URL to the Speedport router.
     */
    protected String urlString;

    /**
     * The connection object to the Speedport model.
     */
    protected HttpURLConnection connection;

    /**
     * Creates a new Handler with the given Speedport URL to connecto to.
     *
     * @param url
     *            - The URL to connect to.
     */
    public SpeedportHandler(final String url) {
        urlString = url;
    }

    /**
     * Children need to implement product specific logic to process the data.
     *
     * @return A {@link SpeedportContent} which contains all collected data.
     * @throws IOException
     */
    protected abstract SpeedportContent processDataInDevice() throws IOException;

    /**
     * Children need to implement product specifc validation.
     *
     * @param content
     *            - {@link SpeedportContent} to validate
     * @return True if the content for the specific model is complete filled,
     *         otherwise false.
     */
    protected abstract boolean validate(SpeedportContent content);

    /**
     * Processes the given Strings {@code varID} and {@code varValue} and
     * assings it to the correct {@link SpeedportContent} value.
     *
     * @param content
     *            - SpeedportContent to save data to.
     * @param varID
     *            - Any ID will be processed if it matches in {@link Constants}.
     * @param varValue
     *            - The value to the assigned ID.
     */
    protected void processExtractedData(final SpeedportContent content, final String varID, final String varValue) {
        if (varID.equals(Constants.DEVICE_NAME)) {
            content.setDeviceName(varValue);
        } else if (varID.equals(Constants.DEVICE_TYPE)) {
            content.setDeviceType(varValue);
        } else if (varID.equals(Constants.DATETIME)) {
            content.setDate(varValue);
        } else if (varID.equals(Constants.DSL_LINK_STATUS)) {
            content.setDslState(varValue);
        } else if (varID.equals(Constants.ONLINE_STATUS)) {
            content.setInternetState(varValue);
        } else if (varID.equals(Constants.DSL_DOWNSTREAM)) {
            content.setDownstream(varValue);
        } else if (varID.equals(Constants.DSL_UPSTREAM)) {
            content.setUpstream(varValue);
        } else if (varID.equals(Constants.USE_WLAN)) {
            if (Constants.ZERO.equals(varValue)) {
                content.setWlan24Active(false);
            } else {
                content.setWlan24Active(true);
            }
        } else if (varID.equals(Constants.USE_WLAN_5GHZ)) {
            if (Constants.ZERO.equals(varValue)) {
                content.setWlan5Active(false);
            } else {
                content.setWlan5Active(true);
            }
        } else if (varID.equals(Constants.WLAN_SSID)) {
            content.setSsid(varValue);
        } else if (varID.contains(Constants.USE_WPS)) {
            if (Constants.ZERO.equals(varValue)) {
                content.setWpsActive(false);
            } else {
                content.setWpsActive(true);
            }
        } else if (varID.equals(Constants.HSFON_STATUS)) {
            if ("0".equals(varValue)) {
                content.setWlanTOGOActive(false);
            } else {
                content.setWlanTOGOActive(true);
            }
        } else if (varID.equals(Constants.FIRMWARE_VERSION)) {
            content.setFirmware(varValue);
        } else if (varID.equals(Constants.SERIAL_NUMBER)) {
            content.setSerial(varValue);
        }
    }

    /**
     * @return A filled and validated {@link SpeedportContent}.
     */
    public SpeedportContent processAndCheckValidity() {
        SpeedportContent content = new SpeedportContent();
        try {
            disableConnectionReuseIfNecessary();
            establishConnection();
            content = processDataInDevice();
            content.setValid(validate(content));
        } catch (final MalformedURLException e) {
            Log.e("<SpeedportInfo>::MalformedURLException", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (final IOException e) {
            Log.e("<SpeedportInfo>::IOException", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return content;
    }

    private void establishConnection() throws IOException {
        final URL url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(1000);
        connection.setUseCaches(false);
    }

    private void disconnect() {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
}
