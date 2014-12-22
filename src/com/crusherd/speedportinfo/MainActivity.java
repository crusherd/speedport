package com.crusherd.speedportinfo;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.HTMLHandlerTask;
import com.crusherd.speedportinfo.html.SpeedportContent;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);

        try {
            if(!isWifiEnabled()) {
                enableWifi();
            }
            else {
            final SpeedportContent content = new HTMLHandlerTask().execute().get();
            updateUI(content);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if Wifi is enabled and we are connected.
     * @return True if enabled and connected, otherwise false.
     */
    private boolean isWifiEnabled() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            return false;
        }
        final boolean isWifiEnabled = (netInfo.getType() == ConnectivityManager.TYPE_WIFI);
        final boolean isConnected = (netInfo.getState() == NetworkInfo.State.CONNECTED);
        return (isWifiEnabled && isConnected);
    }


    private void enableWifi() {
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    private void updateUI(final SpeedportContent content) {
        TextView textView = (TextView) findViewById(R.id.textViewDeviceNameValue);
        textView.setText(content.getDeviceName());

        textView = (TextView) findViewById(R.id.textViewDateValue);
        textView.setText(content.getDate());

        textView = (TextView) findViewById(R.id.textViewDSLStateValue);
        textView.setText(content.getDslState());

        textView = (TextView) findViewById(R.id.textViewInternetStateValue);
        textView.setText(content.getInternetState());

        textView = (TextView) findViewById(R.id.textViewDownStreamValue);
        textView.setText(content.getDownstream() + Constants.kBitperSecond);

        textView = (TextView) findViewById(R.id.textViewUpStreamValue);
        textView.setText(content.getUpstream() + Constants.kBitperSecond);

        textView = (TextView) findViewById(R.id.textViewWLAN24Value);
        textView.setText(content.isWlan24Active() ? R.string.active : R.string.deactivated);

        textView = (TextView) findViewById(R.id.textViewWLAN5Value);
        textView.setText(content.isWlan5Active() ? R.string.active : R.string.deactivated);

        textView = (TextView) findViewById(R.id.textViewSSIDValue);
        textView.setText(content.getSsid());

        textView = (TextView) findViewById(R.id.textViewWPSStateValue);
        textView.setText(content.isWpsActive() ? R.string.active : R.string.deactivated);

        textView = (TextView) findViewById(R.id.textViewWLANTOGOStateValue);
        textView.setText(content.isWlanTOGOActive() ? R.string.active : R.string.deactivated);

        textView = (TextView) findViewById(R.id.textViewFirmwareVersionValue);
        textView.setText(content.getFirmware());

        textView = (TextView) findViewById(R.id.textViewSerialValue);
        textView.setText(content.getSerial());
    }
}
