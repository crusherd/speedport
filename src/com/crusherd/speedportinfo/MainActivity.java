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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.html.HTMLHandlerTask;
import com.crusherd.speedportinfo.html.SpeedportContent;

public class MainActivity extends ActionBarActivity {

    private final OnClickListener refreshContent = new OnClickListener() {

        @Override
        public void onClick(final View v) {
            try {
                Toast.makeText(getApplicationContext(), R.string.refreshing, Toast.LENGTH_SHORT).show();
                final SpeedportContent content = new HTMLHandlerTask().execute().get();
                updateUI(content);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            } catch (final ExecutionException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);
        final Button refreshButton = (Button) findViewById(R.id.buttonRefresh);
        refreshButton.setOnClickListener(refreshContent);

        try {
            if (!isWifiEnabled()) {
                enableWifi();
            } else {
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
     *
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
        Toast.makeText(getApplicationContext(), R.string.wifi_enabling, Toast.LENGTH_SHORT).show();
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        Toast.makeText(getApplicationContext(), R.string.wifi_enabled, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(final SpeedportContent content) {

        updateDeviceNameAndType(content);
        updateDate(content);
        updateDSLState(content);
        updateInternetState(content);
        updateTelephonyState(content);
        updateDownstream(content);
        updateUpStream(content);
        updateWlan24(content);
        updateWlan5(content);
        updateSSID(content);
        updateWPS(content);
        updateWlanToGo(content);
        updateFirmware(content);
        updateSerial(content);
    }

    private void updateDeviceNameAndType(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewDeviceNameValue);
        if (content.getDeviceType().isEmpty()) {
            textView.setText(content.getDeviceName());
        } else {
            textView.setText(content.getDeviceName() + " " + content.getDeviceType());
        }
    }

    private void updateDate(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewDateValue);
        textView.setText(content.getDate());
    }

    private void updateDSLState(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewDSLStateValue);
        textView.setText(content.getDslState());
    }

    private void updateInternetState(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewInternetStateValue);
        textView.setText(content.getInternetState());
    }

    private void updateTelephonyState(final SpeedportContent content) {
        // TODO: Add Telephony state
        final TextView textView = (TextView) findViewById(R.id.textViewTelephonyStateValue);
        disableRow(textView);
    }

    private void updateDownstream(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewDownStreamValue);
        textView.setText(content.getDownstream() + Constants.K_BIT_PER_SECOND);
    }

    private void updateUpStream(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewUpStreamValue);
        textView.setText(content.getUpstream() + Constants.K_BIT_PER_SECOND);
    }

    private void updateWlan24(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewWLAN24Value);
        textView.setText(content.isWlan24Active() ? R.string.active : R.string.deactivated);
    }

    private void updateWlan5(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewWLAN5Value);
        if (content.isWlan5Active()) {
            textView.setText(R.string.active);
        } else {
            disableRow(textView);
        }
    }

    private void updateSSID(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewSSIDValue);
        textView.setText(content.getSsid());
    }

    private void updateWPS(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewWPSStateValue);
        if (content.isWpsActive()) {
            textView.setText(R.string.active);
        } else {
            disableRow(textView);
        }
    }

    private void updateWlanToGo(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewWLANTOGOStateValue);
        if (content.isWlanToGoActive()) {
            textView.setText(R.string.active);
        } else {
            disableRow(textView);
        }
    }

    private void updateFirmware(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewFirmwareVersionValue);
        textView.setText(content.getFirmware());
    }

    private void updateSerial(final SpeedportContent content) {
        final TextView textView = (TextView) findViewById(R.id.textViewSerialValue);
        textView.setText(content.getSerial());
    }

    private void disableRow(final View view) {
        final ViewGroup viewGroup = (ViewGroup) view.getParent();
        final View viewKey = viewGroup.getChildAt(0);
        viewKey.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }
}
