package com.crusherd.speedportinfo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.SpeedportContent;

/**
 * Class to collect information from a Speedport W724V router.
 *
 * @author Robert Danczak
 */
public class SpeedportW724V extends SpeedportHandler {

    /**
     * Default constructor sets address to Speedport W724V router.
     */
    public SpeedportW724V() {
        super("http://speedport.ip/data/Status.json");
    }

    /**
     * {@inheritDoc} Specific code for a Speedoprt W724V.
     */
    @Override
    protected SpeedportContent processDataInDevice() throws IOException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return processWithoutReader();
        } else {
            return processWithReader();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validate(SpeedportContent content) {
        if (!content.getDeviceName().equals(Constants.EMPTY_STRING)
                && !content.getDslState().equals(Constants.EMPTY_STRING)
                && !content.getDownstream().equals(Constants.EMPTY_STRING)
                && !content.getFirmware().equals(Constants.EMPTY_STRING)
                && !content.getInternetState().equals(Constants.EMPTY_STRING)
                && !content.getSerial().equals(Constants.EMPTY_STRING)
                && !content.getSsid().equals(Constants.EMPTY_STRING)
                && !content.getUpstream().equals(Constants.EMPTY_STRING)
                && !content.getDate().equals(Constants.EMPTY_STRING)) {
            return true;
        }
        return false;
    }

    /**
     * Processes the given JSON-Data with a parser for API < 11.
     *
     * @return A valid and filled {@link SpeedportContent}
     * @throws IOException
     */
    private SpeedportContent processWithoutReader() throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final SpeedportContent content = new SpeedportContent();

        String line = reader.readLine();
        while (line != null) {
            if (line.contains("varid")) {
                String varID = extractKey(line);
                line = reader.readLine();
                String varValue = extractValue(line);
                processExtractedData(content, varID, varValue);
            }
            line = reader.readLine();
        }

        return content;
    }

    /**
     * Processes the given JSON-Data with a JSON-Reader for API >= 11.
     *
     * @return A valid and filled {@link SpeedportContent}
     * @throws IOException
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SpeedportContent processWithReader() throws IOException {
        final JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream()));
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
            processExtractedData(content, varID, varValue);
        }
        reader.endArray();
        reader.close();
        return content;
    }

    /**
     * Returns the key for the JSON element. <br/>
     * Format: <br/>
     * <code>"varid":"KEY"</code>
     *
     * @param stringWithKey
     * @return - The key for the JSON element
     */
    private String extractKey(String stringWithKey) {
        String[] splitted = stringWithKey.split("\"varid\":\"");
        splitted = splitted[1].split("\"");
        return splitted[0];
    }

    /**
     * Returns the value for the JSON element. <br/>
     * Format: <br/>
     * <code>"varvalue":"VALUE"</code>
     *
     * @param stringWithValue
     * @return - The value for the JSON element
     */
    private String extractValue(String stringWithValue) {
        String[] splitted = stringWithValue.split("\"varvalue\":\"");
        splitted = splitted[1].split("\"");
        return splitted[0];
    }
}
