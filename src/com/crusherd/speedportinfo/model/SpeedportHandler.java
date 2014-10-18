package com.crusherd.speedportinfo.model;

import com.crusherd.speedportinfo.html.SpeedportContent;

public abstract class SpeedportHandler {

    public SpeedportHandler(final String url) {
        urlString = url;
    }

    /**
     * URL to the Speedport router.
     */
    protected String urlString;

    /**
     * Children need to implement product specific checks and process them.
     * @return A {@link SpeedportContent} which contains all collected data.
     */
    public abstract SpeedportContent checkAndProcess();
}
