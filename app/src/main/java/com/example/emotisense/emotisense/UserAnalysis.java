package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;
/**
 * Created by Pragya on 3/5/15.
 *
 * Displays the user's mood over the day. And displays tabs for daily, weekly and advanced analysis.
 */
public class UserAnalysis extends Activity {

    final String daily_analysis = "Daily Analysis";
    final String weekly_analysis = "Weekly Analysis";
    final String advanced_analysis = "Advanced Analysis";
    String analysisList[] = {daily_analysis, weekly_analysis, advanced_analysis};
    static HashMap<String,Class> analysis_map;

    private void initialiseAnalysisMap() {
        analysis_map = new HashMap<String,Class>();
        analysis_map.put(daily_analysis,DailyAnalysis.class);
        analysis_map.put(weekly_analysis,WeeklyAnalysis.class);
        analysis_map.put(advanced_analysis,AdvancedAnalysis.class);
    }

    private void startAnalysisActivity(String analysisType, String userName) {
        Intent intent = new Intent(this, analysis_map.get(analysisType));
        System.out.println("New analysis *************");
        System.out.println(analysisType);
        Bundle bundle = new Bundle();
        bundle.putString("User",userName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void shareOnFBClicked() {
        System.out.println("Share on fb clicked *************");
        return;
    }


    public void shareonFB (String link) {



        Share.publishFeedDialog(this, Fbinfo.getInstance().mSelfName, "Emotisense", "Mood", link);

//Intent intent = new Intent(this,share.class);
//startActivity(intent);
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initialiseAnalysisMap();
        setContentView(R.layout.user_analysis_layout);
        final String userID = getIntent().getExtras().getString("User");
        //Integer happiness_quotient = DBHandler.getDailyHappinessQuotient(userName);

        String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/analysis.php?type=day&user=" + Uri.encode(userID);

        String ret = null;
        RequestQueue mReporter = null;
        //final String mRecid = "";
        if(mReporter == null)
            mReporter = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.user_analysis_layout_helper, analysisList);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //int happiness_quotient = Integer.parseInt(response.trim());
                        int happiness_quotient = 62;
                        int mood_value = happiness_quotient;
                        TextView textView = (TextView) findViewById(R.id.textView);
                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        String textmood = Fbinfo.getInstance().mood_happy;
                        if (happiness_quotient < 50) {
                            mood_value = (50 - happiness_quotient) * 2;
                            textmood = Fbinfo.getInstance().mood_sad;
                        }
                        final int final_mood = mood_value;
                        String userName = Fbinfo.getInstance().mSelfName.split(" ")[0].toString();
                        textView.setText(String.valueOf(String.valueOf(userName) + " is " + mood_value) + " % " + textmood + " today.");
                        imageView.setImageResource(Fbinfo.getInstance().mood_image_map.get(textmood));

                        ImageView shareOnFBImage = (ImageView) findViewById(R.id.shareOnFBButton);
                        shareOnFBImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String link = "http://stackoverflow.com/questions/10946085/how-can-i-post-link-on-facebook-from-android-app-using-fb-api";
                                //share.publishFeedDialog(this, "sakshi","abc", "def", link ,"pic_url");

                                //link = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/url.php?user=" + Fbinfo.getInstance().mSelfUID + "&mood=" + String.valueOf(final_mood);
                                link = "http://143.215.129.222/emotisense/apps/url.php?user=" + Fbinfo.getInstance().mSelfUID + "&mood=" + String.valueOf(final_mood);
                                //link = "http://images5.fanpop.com/image/photos/30800000/-Random-random-30843841-1920-1080.jpg";
                                shareonFB(link);
                            }
                        });
                        /*
                        Button shareButton = (Button) findViewById(R.id.shareOnFBButton);
                        shareButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View b) {
                                shareonFB();

                            }
                        });*/


                        ListView lv = (ListView) findViewById(R.id.listView);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                                String analysisType = (String) adapter.getItem(position);
                                startAnalysisActivity(analysisType, userID);
                            }
                        });

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
}
