package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import com.facebook.Session;

import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.net.Uri;
import android.media.MediaRecorder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;
import ch.boye.httpclientandroidlib.entity.mime.content.StringBody;



import java.util.Map;
import org.json.*;
import java.util.Collection;

import java.util.HashMap;

/**
 * Created by Pragya on 3/5/15.
 *
 * Displays friend list given the user name.
 */
public class FriendList extends Activity {

    //String friend_names[];
    Integer image_id = R.drawable.account;

    private void startFriendProfileActivity(String friendID) {
        Intent intent = new Intent(this, FriendProfile.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("friend", friendID);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    private void startAccountActivity(String userID) {
        Intent intent = new Intent(this, UserAnalysis.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("User",userID);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.friend_list_layout);
        final String userID = Fbinfo.getInstance().mSelfUID;
        System.out.println("*******************");
        System.out.println("userID: " + userID);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(Fbinfo.getInstance().mSelfName);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if( Fbinfo.getInstance().friendImages.containsKey(Fbinfo.getInstance().mSelfName)) {
            imageView.setImageResource(Fbinfo.getInstance().friendImages.get(Fbinfo.getInstance().mSelfName));
        }
        else
            imageView.setImageResource(R.drawable.account);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAccountActivity(userID);
            }
        });

        Fbinfo.getInstance().printInfo();
        Map friendInfo = Fbinfo.getInstance().mFriendInfo;
        System.out.println("FRIEND LIST API &&&&&&");
        System.out.println(friendInfo);
        Fbinfo.getInstance().printInfo();
        if(friendInfo == null || friendInfo.size() == 0) {
            System.out.println("FRIEND INFO NULL!");

            Intent intent1 = new Intent(this, LogIn.class);
            startActivity(intent1);
            return;
        }
        String[] friendList = null;
        try {
            friendList = new String[friendInfo.size()];
        }
        catch(Exception e) {
            System.out.println("Going back to Login class! *** ");

            Intent intent1 = new Intent(this, LogIn.class);
            startActivity(intent1);
            return;
        }
        final HashMap<Integer, String> mapPositionWithId = new HashMap<Integer, String>();
        int i = 0;
        System.out.println("USER ID: " + Fbinfo.getInstance().mSelfUID);
        for (Object entry : friendInfo.entrySet()) {
             friendList[i] = (String)((Map.Entry)entry).getValue();

             mapPositionWithId.put(i,(String)((Map.Entry)entry).getKey());
             System.out.println("FRIEND ID: " + (String)((Map.Entry)entry).getKey());
             i++;
        }

        final FriendListAdapter adapter = new FriendListAdapter(this, image_id,friendList);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                //String friendName = (String) adapter.getItem(position);
                String friendID = mapPositionWithId.get(position);
                System.out.println("Position: " + String.valueOf(position));
                System.out.println("FriendID: " + friendID);
                startFriendProfileActivity(friendID);
            }
        });


    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        return;
    }
}
