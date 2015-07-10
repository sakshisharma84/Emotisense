package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import android.view.Menu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
/**
 * Created by Pragya on 3/5/15.
 *
 * Displays most recent unread photos of the user's friend.
 */
public class FriendProfile extends Activity {


    int current_url_index = 0;
    String[] urls;
    Integer[] photoids;
    String friendID;
    RequestQueue mReporter = null;

    private void displayNoMoreImagesMessage() {
        Intent intent = new Intent(this, NoMoreImagesPage.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void displayFriendHappinessQuotient() {
        Intent intent = new Intent(this, FriendHappinessQuotient.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("friend",friendID);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.friend_profile_layout);
        friendID = getIntent().getExtras().getString("friend");
        String userID = Fbinfo.getInstance().mSelfUID;
        //urls = DBHandler.fetchLatestData(UserProfile.getUserName(), friendID);
        //urls = DBHandler.fetchLatestData(Fbinfo.getInstance().mSelfUID, friendID);

        final long start = System.currentTimeMillis();
        Log.d ("PERF", "Start: " + start);
        //System.out.println("Start: " + String.valueOf(start));

        String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/fetchphoto.php?user=" + Uri.encode(userID) + "&friend=" + Uri.encode(friendID);
        String ret = null;
        if(mReporter == null)
            mReporter = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                                long end = System.currentTimeMillis();
                                 Log.d ("PERF", "End: " + end);
                            Log.d ("PERF", "Load Url diff: " + String.valueOf(end - start));

                            JSONArray jArray = new JSONArray(response);
                            urls = new String[jArray.length()];
                            photoids = new Integer[jArray.length()];
                            for (int i = 0; i < jArray.length(); i++) {
                                Integer photoid = Integer.valueOf(jArray.getJSONObject(i).getString("photoid").trim());
                                String url1 = jArray.getJSONObject(i).getString("url");
                                photoids[i] = photoid;
                                urls[i] = "http://trinity.cc.gt.atl.ga.us/emotisense/" + url1;
                            }
                            if (urls.length == 0) {
                                //displayNoMoreImagesMessage();
                                System.out.println("No more images!");
                                 displayFriendHappinessQuotient();

                                return;
                            }
                            setNextImage(urls[current_url_index],photoids[current_url_index]);
                        }
                        catch(Exception e) {
                        }
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

    /*
        TODO: uncomment after integration
     */
    private void set_current_url_index() {
        if ((current_url_index+1) == urls.length) {
            //urls = DBHandler.fetchLatestData(UserProfile.getUserName(), friendID);
            String userID = Fbinfo.getInstance().mSelfUID;

            String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/fetchphoto.php?user=" + Uri.encode(userID) + "&friend=" + Uri.encode(friendID);
            String ret = null;
            if(mReporter == null)
                mReporter = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.

            final long start = System.currentTimeMillis();
           // Log.d ("PERF", "Start: " + start);
            //System.out.println("Start: " + String.valueOf(start));

            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                long end = System.currentTimeMillis();
                                //Log.d("PERF", "End: " + end);
                                //System.out.println("End: " + String.valueOf(end));
                                long  diff = end - start;
                                Log.d ("PERF", "End: " + end);
                                Log.d ("PERF", "set_current_URL Load Url diff : " + String.valueOf(end - start));


                                    /*File statText = new File("/Users/Pragya/Documents/6675-AIC/emotisense-final-gatech/emotisense-final/pic_1.txt");
                                    FileOutputStream is = new FileOutputStream(statText);
                                    OutputStreamWriter osw = new OutputStreamWriter(is);
                                    Writer w = new BufferedWriter(osw);
                                    w.write(String.valueOf(diff));
                                    w.close();*/

                                JSONArray jArray = new JSONArray(response);
                                urls = new String[jArray.length()];
                                photoids = new Integer[jArray.length()];
                                for (int i = 0; i < jArray.length(); i++) {
                                    Integer photoid = Integer.valueOf(jArray.getJSONObject(i).getString("photoid").trim());
                                    String url1 = jArray.getJSONObject(i).getString("url");
                                    photoids[i] = photoid;
                                    urls[i] = "http://trinity.cc.gt.atl.ga.us/emotisense/" + url1;
                                }
                                if(urls.length == 0) {
                                    //displayNoMoreImagesMessage();
                                    System.out.println("No more images!");
                                    displayFriendHappinessQuotient();
                                    return;
                                }
                                else
                                    current_url_index = -1;

                                current_url_index = current_url_index + 1;
                                setNextImage(urls[current_url_index],photoids[current_url_index]);
                            }
                            catch(Exception e) {
                            }
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
        else {
            current_url_index = current_url_index + 1;
            setNextImage(urls[current_url_index], photoids[current_url_index]);
        }
    }

    private void setNextImage(String url, final Integer photoid) {
        System.out.println("*****************");
        System.out.println("Setting next image");
        System.out.println(url);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setTag(url);


        new DownloadImagesTask().execute(imageView);
        final FriendProfileListAdapter adapter = new FriendProfileListAdapter(this, Fbinfo.getInstance().image_id, Fbinfo.getInstance().mood_names);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        if(mReporter == null)
            mReporter = Volley.newRequestQueue(this);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                String moodName = (String) adapter.getItem(position);
                //DBHandler.sendMoodToDB(urls[current_url_index], friendID, mood_map.get(moodName));
                String userID = Fbinfo.getInstance().mSelfUID;
                String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/addmood.php?photo=" + Uri.encode(String.valueOf(photoid)) + "&friend=" + Uri.encode(userID) + "&mood=" + Uri.encode(String.valueOf(Fbinfo.getInstance().mood_map.get(moodName)));
                String ret = null;
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println("Response: ***************** : " + response);
                                   // System.out.println(response);
                                    // TODO: take action according to the response
                                    System.out.println("Inside response. calling set_current_url_index() ");
                                    set_current_url_index();
                                }
                                catch(Exception e) {

                                }
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
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHandler.sendReportToDB(urls[current_url_index]);
                set_current_url_index();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;
        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            final long start = System.currentTimeMillis();
            Log.d ("PERF", "Picture Load Start: " + start);

            this.imageView = imageViews[0];
            Bitmap b = download_Image((String)imageView.getTag());

            final long end = System.currentTimeMillis();
            Log.d ("PERF", "Picture Load End: " + end);
            Log.d ("PERF", "Picture Load diff: " + String.valueOf(end - start));
            return b;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        private Bitmap download_Image(String url) {
            Bitmap bmp =null;
            try{
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;
            }catch(Exception e){}
            return bmp;
        }
    }
}
