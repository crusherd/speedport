package com.crusherd.speedportinfo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.crusherd.speedportinfo.html.SpeedportContent;

/**
 * @author Robert Danczak
 */
public class SpeedportW900V extends SpeedportHandler {

    /**
     * Default constructor sets address to Speedport W900V router.
     */
    public SpeedportW900V() {
        super("http://speedport.ip/cgi-bin/webcm?getpage=../html/top_newstatus.htm&var:prn=1");
        // super("speedport.ip/cgi-bin/webcm?getpage=../html/top_newstatus.htm");
    }

    /**
     * {@inheritDoc} Specific code for a Speedoprt W900V.
     */
    @Override
    protected SpeedportContent processDataInDevice() throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final SpeedportContent content = new SpeedportContent();

        String line = reader.readLine();
        while (line != null) {
            // String varID = extractKey(line);
            // String varValue = extractValue(line);
            // processExtractedData(content, varID, varValue);
            Log.d("--SpeedportInfo--", line);
            line = reader.readLine();
        }

        return content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validate(SpeedportContent content) {
        // TODO Auto-generated method stub
        return false;
    }

    private String extractValue(String line) {
        // TODO Auto-generated method stub
        return null;
    }

    private String extractKey(String line) {
        // TODO Auto-generated method stub
        return null;
    }

}
