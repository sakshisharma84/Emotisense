package com.example.emotisense.emotisense;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceView;

import com.example.emotisense.emotisense.cameraLibrary.CameraClass;
import com.example.emotisense.emotisense.cameraLibrary.TakePicture;

/**
 * Created by joseph on 3/29/15.
 */
public class CameraService extends Service {
    private String TAG = "CameraService";
    private final IBinder mBinder = new LocalBinder();
    private long last = -1;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // REGISTER RECEIVER THAT HANDLES SCREEN ON AND SCREEN OFF LOGIC
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean screenOff = true;
        if (intent != null)
            screenOff = intent.getBooleanExtra("screen_state", false);
//        D("Is screen off ? " + screenOff);
        long timeNow = System.currentTimeMillis();

        if (!screenOff && (last < 0 || (timeNow - last) > 1000)) {
            last = timeNow;
            Intent camera = new Intent(this, TakePicture.class);
            camera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            D("Start camera!");
            startActivity(camera);
        }
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        CameraService getService() {
            return CameraService.this;
        }
    }

    private void D(String log) {
        Log.d(TAG, log);
    }
}