package com.crusherd.speedportinfo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.SpeedportContent;
import com.crusherd.speedportinfo.html.SpeedportPhoneEntry;

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
    protected final SpeedportContent processDataInDevice() throws IOException {
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
    protected boolean validate(final SpeedportContent content) {
        if (!content.getDeviceName().isEmpty()
                && !content.getDslState().isEmpty()
                && !content.getDownstream().isEmpty()
                && !content.getFirmware().isEmpty()
                && !content.getInternetState().isEmpty()
                && !content.getSerial().isEmpty()
                && !content.getSsid().isEmpty()
                && !content.getUpstream().isEmpty()
                && !content.getDate().isEmpty()
                && !content.getDeviceType().isEmpty()) {
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
        final BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        final SpeedportContent content = new SpeedportContent();

        String line = reader.readLine();
        while (line != null) {
            if (line.contains("varid")) {
                final String varID = extractKey(line);
                line = reader.readLine();
                final String varValue = extractValue(line);
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
        final JsonReader reader = new JsonReader(new InputStreamReader(this.connection.getInputStream()));
        final SpeedportContent content = new SpeedportContent();
        reader.beginArray();
        while (reader.hasNext() && (reader.peek() != JsonToken.END_ARRAY)) {
            reader.beginObject();
            reader.skipValue();
            reader.skipValue();
            reader.skipValue();
            final String varID = reader.nextString();
            reader.skipValue();
            String varValue = "";
            if (Constants.ADD_PHONE_NUMBER.equals(varID)) {
                processSubArray(reader, content.getPhoneEntries());
            } else {
                varValue = reader.nextString();
            }
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
    private String extractKey(final String stringWithKey) {
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
    private String extractValue(final String stringWithValue) {
        String[] splitted = stringWithValue.split("\"varvalue\":\"");
        splitted = splitted[1].split("\"");
        return splitted[0];
    }

    /**
     * Processes a subarray with a JSON Reader.
     *
     * @param reader
     * @throws IOException
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void processSubArray(final JsonReader reader, final List<SpeedportPhoneEntry> phoneEntries) throws IOException {
        reader.beginArray();
        while (reader.hasNext() && (reader.peek() != JsonToken.END_ARRAY)) {
            phoneEntries.add(extractPhoneEntry(reader));
        }

        reader.endArray();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SpeedportPhoneEntry extractPhoneEntry(final JsonReader reader) throws IOException {
        final int toSkip = 5;
        reader.beginObject();
        for (int i = 0; i < toSkip; i++) {
            reader.skipValue();
        }
        final String varIDValue = reader.nextString();
        reader.endObject();
        reader.beginObject();
        for (int i = 0; i < toSkip; i++) {
            reader.skipValue();
        }
        final String varPhoneNumberValue = reader.nextString();
        reader.endObject();
        reader.beginObject();
        for (int i = 0; i < toSkip; i++) {
            reader.skipValue();
        }
        final String varFailReasonValue = reader.nextString();
        reader.endObject();
        reader.beginObject();
        for (int i = 0; i < toSkip; i++) {
            reader.skipValue();
        }
        final String varStatusValue = reader.nextString();
        reader.endObject();
        reader.beginObject();
        for (int i = 0; i < toSkip; i++) {
            reader.skipValue();
        }
        final String varVOIPErrorValue = reader.nextString();
        reader.endObject();

        return new SpeedportPhoneEntry(Integer.parseInt(varIDValue), varPhoneNumberValue,
                Integer.parseInt(varFailReasonValue), varStatusValue, varVOIPErrorValue);
    }
}
