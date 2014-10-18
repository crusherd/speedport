package com.crusherd.speedportinfo.model;

import com.crusherd.speedportinfo.html.SpeedportContent;

/**
 * @author Robert Danczak
 *
 */
public class SpeedportW900V extends SpeedportHandler {

    /**
     * @param url
     */
    public SpeedportW900V() {
        super("http://192.168.2.2/cgi-bin/webcm?getpage=../html/top_newstatus.htm");
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.crusherd.speedportinfo.model.SpeedportHandler#checkAndProcess()
     */
    @Override
    public SpeedportContent checkAndProcess() {
        // TODO Auto-generated method stub
        return null;
    }

}
