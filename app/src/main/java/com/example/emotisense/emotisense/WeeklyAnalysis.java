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
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Pragya on 3/18/15.
 *
 * Shows moods of the user for the past seven days.
 */
public class WeeklyAnalysis extends Activity {

    HashMap<Integer, Integer> analysis_map = new HashMap<Integer,Integer>();
    public void generate_graph(String userID) {

       // HashMap<Integer, Integer> analysis_map = DBHandler.getWeeklyAnalysisStats(userName);
        String requestToServer = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/analysis.php?type=week&user=" + Uri.encode(userID);

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
                            analysis_map.put(0,0);
                            for (int i = 0; i < jArray.length(); i++) {
                                Integer hour = jArray.getJSONObject(i).getInt("date");
                                Integer mood = jArray.getJSONObject(i).getInt("val");
                                analysis_map.put(hour, mood);
                            }
                            /*
                            analysis_map.put(1,60);
                            analysis_map.put(2,58);
                            analysis_map.put(3,64);
                            analysis_map.put(4,62);
                            analysis_map.put(5,57);
                            analysis_map.put(6,60);
                            analysis_map.put(7,61);
*/
                            GraphView graph = (GraphView) findViewById(R.id.graph);
                            graph.getViewport().setMinX(0);
                            graph.getViewport().setMaxX(7);
                            graph.getViewport().setMinY(0);
                            graph.getViewport().setMaxY(100);
                            graph.getViewport().setXAxisBoundsManual(true);
                            graph.getViewport().setYAxisBoundsManual(true);
                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                            /*staticLabelsFormatter.setVerticalLabels(new String[]{Fbinfo.getInstance().mood_sad,
                                    Fbinfo.getInstance().mood_confused, Fbinfo.getInstance().mood_happy,
                                    Fbinfo.getInstance().mood_excited});*/
                            //staticLabelsFormatter.setVerticalLabels(Fbinfo.getInstance().mood_names);
                            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                            graph.getGridLabelRenderer().setNumHorizontalLabels(8);
                            graph.getGridLabelRenderer().setNumVerticalLabels(4);
                            graph.getGridLabelRenderer().setHighlightZeroLines(false);

                            Integer[] unsorted_values = new Integer[analysis_map.size()];
                            int j = 0;
                            for (Integer key : analysis_map.keySet()) {
                                unsorted_values[j] = key;
                                j = j + 1;
                            }
                            Arrays.sort(unsorted_values);

                            DataPoint[] dataPoints = new DataPoint[analysis_map.size()];
                            for (int i = 0;i<analysis_map.size();i++) {
                                int key = unsorted_values[i];
                                System.out.println("key: " + String.valueOf(key));
                                System.out.println("key: " + String.valueOf(analysis_map.get(key)));
                                DataPoint d = new DataPoint(key,analysis_map.get(key));
                                dataPoints[i] = d;
                                //i = i + 1;
                            }

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
                            graph.addSeries(series);
                            series.setColor(Color.RED);
                            series.setThickness(2);
                        } catch (Exception e) {
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
        setContentView(R.layout.weekly_analysis_layout);
        final String userName = getIntent().getExtras().getString("User");
        generate_graph(userName);
    }
}
