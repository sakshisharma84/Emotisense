package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.Session;

import java.util.HashMap;
import java.util.Map;
/**
 * TODO: - should call the login class, which calls the friendList class.
 */
public class MainActivity extends Activity{
    private BroadcastReceiver mBroadcastReceiver;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // TODO: change after login starts working.
       // UserProfile.setUserName("Pragya");
        //DBHandler.createHardcodedFriendList();
        //DBHandler.createTempFriendPictureDB();

        // TODO: Pragya - hardcoded stuff for testing.
        /*Fbinfo inst = Fbinfo.getInstance();
        inst.setSelfInfo("Pragya Agarwal","10206242692930240");
        Map friend_info = new HashMap();
        friend_info.put("10152831267363095","Sakshi Sharma");
        inst.setFriendInfo(friend_info);
        inst.mSelfUID="10206242692930240";
        inst.mSelfName="Pragya Agarwal";
        Intent intent = new Intent(this, FriendList.class);*/

        //Intent intent = new Intent(this, LogIn.class);
        /*Intent intent = new Intent(this, FriendHappinessQuotient.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("friend","10152831267363095");
        intent.putExtras(bundle1);
        */
        //Session.setActiveSession(null);
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);

        // JOSEPH
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    // Added by Joseph
    // To register listener for screen detection
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mBroadcastReceiver = new ScreenReceiver();
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}

