package com.crusherd.speedportinfo.model;

import java.io.IOException;

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
        //http://speedport.ip/cgi-bin/webcm?getpage=../html/top_newstatus.htm&var:prn=1
        super("speedport.ip/cgi-bin/webcm?getpage=../html/top_newstatus.htm");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected SpeedportContent processDataInDevice() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
