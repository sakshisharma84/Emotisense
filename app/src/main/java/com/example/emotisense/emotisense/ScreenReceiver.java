package com.example.emotisense.emotisense;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by joseph on 3/29/15.
 */
public class ScreenReceiver extends BroadcastReceiver {
    private String TAG = "ScreenReceiver";
    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
        }
//        D("Screen is off " + screenOff);
        Intent i = new Intent(context, CameraService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }

    public void D(String log) {
        Log.d(TAG, log);
    }

}