package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Pragya on 4/2/15.
 */
public class FriendHappinessQuotient extends Activity {

    String friendID;

    public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.friend_happiness_quotient);
        friendID = getIntent().getExtras().getString("friend");
    final String userID = getIntent().getExtras().getString("User");
    //Integer happiness_quotient = DBHandler.getDailyHappinessQuotient(userName);

    String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/analysis.php?type=day&user=" + Uri.encode(friendID);

    String ret = null;
    RequestQueue mReporter = null;
    //final String mRecid = "";
    if(mReporter == null)
    mReporter = Volley.newRequestQueue(this);
        //TextView textView1 = (TextView) findViewById(R.id.textView1);
        final String friendName = Fbinfo.getInstance().getFriendinfo().get(friendID).toString().split(" ")[0];
        System.out.println("******************");
        System.out.println("friendName = " + friendName);
        //textView1.setText( String.valueOf(friendName) + " is");
    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
            new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int happiness_quotient = Integer.parseInt(response.trim());
                    //int happiness_quotient = 10;
                    int mood_value = happiness_quotient;
                    TextView textView = (TextView) findViewById(R.id.textView);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    String textmood = Fbinfo.getInstance().mood_happy;
                    if (happiness_quotient < 50) {
                        mood_value = (50 - happiness_quotient) * 2;
                        textmood = Fbinfo.getInstance().mood_sad;
                    }
                    String userName = Fbinfo.getInstance().mSelfName.split(" ")[0].toString();
                    textView.setText(String.valueOf(String.valueOf(friendName) + " is " + mood_value) + " % " + textmood + " today.");
                    imageView.setImageResource(Fbinfo.getInstance().mood_image_map.get(textmood));
                }
            }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("ERROR!!");
        }
    });
    // Add the request to the RequestQueue.
    mReporter.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }
}
