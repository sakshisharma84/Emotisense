package com.example.emotisense.emotisense;


import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.Facebook;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class LogIn extends Activity {

    private String TAG = "LogIn";
    //private TextView lblEmail;
    //private String appID = "1566794853579903";
    public String UserID;
    public String Name;
    RequestQueue mReporter = null;
    String mRecid = "";

    Session s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LoginButton authButton = (LoginButton) findViewById(R.id.activity_login_facebook_btn_login);

        /*Session session1 = Session.getActiveSession();
        session1.closeAndClearTokenInformation();
        Session.setActiveSession(null);
        */

        System.out.println("1");
        authButton.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "Error " + error.getMessage());
            }
        });
        // set permission list, Don't forget to add email
        System.out.println("2");
        authButton.setReadPermissions(Arrays.asList("public_profile","email","user_friends"));
        // session state call back event

        /*Session.OpenRequest openRequest = new Session.OpenRequest(this);
        openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
        ParseFacebookUtils.initialize("fbID");
        */

        authButton.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                s1 = session;
                System.out.println("4");

                if (session.isOpened()) {
                    Log.i(TAG, "Access Token" + session.getAccessToken() + ", " + session.toString());
                    com.facebook.Request.newMeRequest(session,
                            new com.facebook.Request.GraphUserCallback() {
                                @Override
                                public void onCompleted(GraphUser user, com.facebook.Response response) {
                                    Log.i(TAG, "Response: " + response.toString());
                                    if (user != null) {
                                        Log.i(TAG, "User ID " + user.getId());
                                        Log.i(TAG, "Email " + user.asMap().get("email"));
                                        //lblEmail.setText(user.asMap().get("email").toString());
                                        UserID = user.getId();
                                        Log.i(TAG, "UserID is" + UserID);
                                        Name = user.getName();
                                        Log.i(TAG, "Name " + Name);
                                        Fbinfo.getInstance().setSelfInfo(Name, UserID);

                                        if (Session.getActiveSession() == null)
                                            Log.d(TAG, "Session is null... ");

                                        new com.facebook.Request(
                                                Session.getActiveSession(),
                                                "/me/friends",
                                                null,
                                                HttpMethod.GET,
                                                new com.facebook.Request.Callback() {
                                                    public void onCompleted(com.facebook.Response response) {
                                                        Log.d(TAG, response.toString());
                                                        GraphObject graphObject = response.getGraphObject();
                                                        if (graphObject != null) {
                                                            Log.d(TAG, graphObject.toString());
                                                            try {
                                                                JSONObject jsonObject = graphObject.getInnerJSONObject();
                                                                JSONArray data = jsonObject.getJSONArray("data");
                                                                Log.i(TAG, data.toString());

                                                                int i,length;
                                                                length = data.length();
                                                                Log.i(TAG, "Data length: " + length);
                                                                String[] frnd_names = new String[length];
                                                                String[] friend_id = new String[length];

                                                                for (i = 0; i < data.length(); i++) {
                                                                    final String name = data.getJSONObject(i).getString("name");
                                                                    Log.i(TAG, "name: " + name);
                                                                    frnd_names[i] = name;

                                                                    final String id = data.getJSONObject(i).getString("id");
                                                                    Log.i(TAG, "ID: " + id);
                                                                    friend_id[i] = id;
                                                                    //final String email = data.getJSONObject(i).getString("email");
                                                                    //Log.i(TAG, "email: " + email);
                                                                }
                                                                System.out.println("6");

                                                                for (i = 0; i < length; i++) {

                                                                    Log.i(TAG, "Name: Index[" + i + "] = " + frnd_names[i]);
                                                                    Log.i(TAG, "ID: Index[" + i + "] = " + friend_id[i]);
                                                                }

                                                                Map friend_info = new HashMap();
                                                                // Add "id" and "names"
                                                                for (i=0; i<length; i++) {
                                                                    friend_info.put(friend_id[i], frnd_names[i]);
                                                                }
                                                                Fbinfo.getInstance().setFriendInfo(friend_info);

                                                                System.out.println("LOGIN STUFF &&&&");
                                                                Fbinfo.getInstance().printInfo();

                                                                System.out.println("Total friend_info: " + friend_info.size());
                                                                // Iterate over all info map, using the keySet method.
                                                                Set keyset = friend_info.keySet();
                                                                System.out.println("Key set values are: " + keyset);

                                                                //String searchKey = friend_id[0];
                                                                //if(friend_info.containsKey(searchKey))
                                                                //System.out.println("Found total " + friend_info.get(searchKey) + " " + searchKey + " friends!\n");

                                                                Fbinfo.getInstance().printInfo();
                                                                String messages = "";
                                                                for (i = 0; i<length; i++) {

                                                                    messages = messages.concat("&friend[]=");
                                                                    messages = messages.concat(String.valueOf(friend_id[i]));

                                                                }

                                                                Log.i(TAG, "messages:"  + messages);


                                                                //Send to the server
                                                                //String murl = "http://143.215.129.150:8080/report?frnd_names"+ Uri.encode("frnd_names=" + messages + "UserID=" + UserID);
                                                                String murl = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/adduser.php?user=" +
                                                                        Uri.encode(Fbinfo.getInstance().mSelfUID) + "&name=" +
                                                                        Uri.encode(Fbinfo.getInstance().mSelfName)+ messages;

                                                                report_server(murl);
                                                                if(true)
                                                                {
                                                                    // String rurl = "http://130.207.5.67:8080/recording?recid="+Uri.encode(mRecid);
                                                                    // FIXME: send_recording(murl) using
                                                                    //send_recording(rurl);
                                                                }


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                }
                                        ).executeAsync();


                                        //Fbinfo.getInstance().setFbSession(s1);
                                    }
                                }
                            }
                    ).executeAsync();
                }

                //Fbinfo.getInstance().printInfo();
                System.out.println("5");

                //Sakshi
                Log.i(TAG, "Access Token" + session.getAccessToken());



            }
        });
    }
//
//    @Override
//    protected void onResume() {
//        s1 = Session.getActiveSession();
//        Log.d(TAG, "Null? " + s1.to)
//        if (s1 != null && s1.isOpened()) {
//            Log.d(TAG, "Session: " + s1.toString());
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void report_server(String murl) {
        String ret = null;

        if(mReporter == null)
            mReporter = Volley.newRequestQueue(this);

        Log.i(TAG,"ReportingServer URL: "+murl);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, murl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(TAG, "Get Response is: " + response.toString());
                        // set recid here
                        mRecid = response.toString();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Get didn't work - " + error);
            }
        });
        // Add the request to the RequestQueue.
        mReporter.add(stringRequest);
    }

}




