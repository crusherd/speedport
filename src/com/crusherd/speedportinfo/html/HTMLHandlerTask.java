package com.crusherd.speedportinfo.html;

import android.os.AsyncTask;

import com.crusherd.speedportinfo.model.SpeedportW724V;
import com.crusherd.speedportinfo.model.SpeedportW900V;

/**
 * Task to query Speedports.
 *
 * @author Robert Danczak
 */
public class HTMLHandlerTask extends AsyncTask<Void, Void, SpeedportContent> {

    @Override
    protected SpeedportContent doInBackground(final Void... notUsed) {
        SpeedportContent content;
        content = new SpeedportW724V().processAndCheckValidity();
        if ((content != null) && content.isValid()) {
            return content;
        }
        content = new SpeedportW900V().processAndCheckValidity();
        if ((content != null) && content.isValid()) {
            return content;
        }
        return new SpeedportContent();
    }

}
