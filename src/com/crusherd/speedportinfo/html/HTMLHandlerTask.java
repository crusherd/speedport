package com.crusherd.speedportinfo.html;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.crusherd.speedportinfo.R;
import com.crusherd.speedportinfo.constants.Constants;
import com.crusherd.speedportinfo.model.SpeedportW724V;
import com.crusherd.speedportinfo.model.SpeedportW900V;

public class HTMLHandlerTask extends AsyncTask<Activity, Void, Void> {

    private Activity activity;

    @Override
    protected Void doInBackground(final Activity... activities) {
        activity = activities[0];
        SpeedportContent content;
        content = new SpeedportW724V().checkAndProcess();
        if ((content != null) && content.isValid()) {
            updateUI(content);
            return null;
        }
        content = new SpeedportW900V().checkAndProcess();
        if ((content != null) && content.isValid()) {
            updateUI(content);
            return null;
        }
        return null;
    }

    private void updateUI(final SpeedportContent content) {
        TextView textView = (TextView) activity.findViewById(R.id.textViewDeviceNameValue);
        textView.setText(content.getDeviceName());

        textView = (TextView) activity.findViewById(R.id.textViewDateValue);
        textView.setText(content.getDate());

        textView = (TextView) activity.findViewById(R.id.textViewDSLStateValue);
        textView.setText(content.getDslState());

        textView = (TextView) activity.findViewById(R.id.textViewInternetStateValue);
        textView.setText(content.getInternetState());

        textView = (TextView) activity.findViewById(R.id.textViewDownStreamValue);
        textView.setText(content.getDownstream() + Constants.kBitperSecond);

        textView = (TextView) activity.findViewById(R.id.textViewUpStreamValue);
        textView.setText(content.getUpstream() + Constants.kBitperSecond);

        textView = (TextView) activity.findViewById(R.id.textViewWLAN24Value);
        textView.setText(content.isWlan24Active() ? R.string.active : R.string.deactivated);

        textView = (TextView) activity.findViewById(R.id.textViewWLAN5Value);
        textView.setText(content.isWlan5Active() ? R.string.active : R.string.deactivated);

        textView = (TextView) activity.findViewById(R.id.textViewSSIDValue);
        textView.setText(content.getSsid());

        textView = (TextView) activity.findViewById(R.id.textViewWPSStateValue);
        textView.setText(content.isWpsActive() ? R.string.active : R.string.deactivated);

        textView = (TextView) activity.findViewById(R.id.textViewWLANTOGOStateValue);
        textView.setText(content.isWlanTOGOActive() ? R.string.active : R.string.deactivated);

        textView = (TextView) activity.findViewById(R.id.textViewFirmwareVersionValue);
        textView.setText(content.getFirmware());

        textView = (TextView) activity.findViewById(R.id.textViewSerialValue);
        textView.setText(content.getSerial());
    }

}
