package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import java.util.Arrays;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pragya on 3/18/15.
 *
 * Shows moods of the user for the current day on hourly basis.
 */
public class DailyAnalysis extends Activity {

    HashMap<Integer, Integer> daily_analysis_map = new HashMap<Integer,Integer>();

    public void generate_graph(String userID) {
        System.out.println("*************");
        System.out.println("INside daily analysis");
        //HashMap<Integer, Integer> daily_analysis_map = DBHandler.getDailyAnalysisStats(userName);
        String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/analysis.php?type=hourly&user=" + Uri.encode(userID);

        String ret = null;
        RequestQueue mReporter = null;
        //final String mRecid = "";
        if(mReporter == null)
            mReporter = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, requestToServer,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("*************");
                            System.out.println(response);
                            JSONArray jArray = new JSONArray(response);
                            daily_analysis_map.put(0,0);
                            for (int i = 0; i < jArray.length(); i++) {
                                Integer hour = jArray.getJSONObject(i).getInt("hour");
                                Integer mood = jArray.getJSONObject(i).getInt("val");
                                daily_analysis_map.put(hour, mood);
                            }
                           /* daily_analysis_map.put(6, 60);
                            daily_analysis_map.put(7, 62);
                            daily_analysis_map.put(8, 60);
                            daily_analysis_map.put(9, 58);
                            daily_analysis_map.put(10, 61);
                            daily_analysis_map.put(11, 59);

                            daily_analysis_map.put(12, 35);
                            daily_analysis_map.put(13, 34);
                            daily_analysis_map.put(14, 37);
                            daily_analysis_map.put(15, 35);
                            daily_analysis_map.put(16, 32);
                            daily_analysis_map.put(17, 33);

                            daily_analysis_map.put(18, 82);
                            daily_analysis_map.put(19, 83);
                            daily_analysis_map.put(20, 80);
                            daily_analysis_map.put(21, 81);
                            daily_analysis_map.put(22, 83);
                            daily_analysis_map.put(23, 84);
*/
                                GraphView graph = (GraphView) findViewById(R.id.graph);
                                graph.getViewport().setMinX(0);
                                graph.getViewport().setMaxX(25);
                                graph.getViewport().setMinY(0);
                                graph.getViewport().setMaxY(100);
                                graph.getViewport().setXAxisBoundsManual(true);
                                graph.getViewport().setYAxisBoundsManual(true);
                                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                                //staticLabelsFormatter.setVerticalLabels(Fbinfo.getInstance().mood_names);
                                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                                graph.getGridLabelRenderer().setNumHorizontalLabels(5);
                                graph.getGridLabelRenderer().setNumVerticalLabels(5);
                                graph.getGridLabelRenderer().setHighlightZeroLines(false);
                                //graph.getGridLabelRenderer().getDomainGridLinePaint().setColor(Color.TRANSPARENT);


                                //daily_analysis_map.put(12,100);
                                //daily_analysis_map.put(23,50);

                                Integer[] unsorted_values = new Integer[daily_analysis_map.size()];
                                int j = 0;
                                for (Integer key : daily_analysis_map.keySet()) {
                                    unsorted_values[j] = key;
                                    j = j + 1;
                                }
                                Arrays.sort(unsorted_values);

                                DataPoint[] dataPoints = new DataPoint[daily_analysis_map.size()];
                                for (int i = 0;i<daily_analysis_map.size();i++) {
                                    int key = unsorted_values[i];
                                    System.out.println("key: " + String.valueOf(key));
                                    System.out.println("key: " + String.valueOf(daily_analysis_map.get(key)));
                                    DataPoint d = new DataPoint(key,daily_analysis_map.get(key));
                                    dataPoints[i] = d;
                                    //i = i + 1;
                                }

                                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
                                graph.addSeries(series);
                                series.setColor(Color.RED);
                                series.setThickness(2);
                        }
                         catch(Exception e)   {
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

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.daily_analysis_layout);
        final String userID = getIntent().getExtras().getString("User");
        generate_graph(userID);
    }
}
