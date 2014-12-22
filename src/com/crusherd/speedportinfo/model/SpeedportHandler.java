package com.crusherd.speedportinfo.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Build;

import com.crusherd.speedportinfo.html.SpeedportContent;

public abstract class SpeedportHandler {

    /**
     * URL to the Speedport router.
     */
    protected String urlString;

    protected HttpURLConnection connection;

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

    public SpeedportContent checkAndProcess() {
        SpeedportContent content = new SpeedportContent();
        try {
            disableConnectionReuseIfNecessary();
            establishConnection();
            content = processDataInDevice();
            content.setValid(validate(content));
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
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
