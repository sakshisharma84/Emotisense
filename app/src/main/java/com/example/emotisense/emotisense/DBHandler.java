package com.example.emotisense.emotisense;

import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Random;
import android.content.Context;
/**
 * Created by Pragya on 3/5/15.
 *
 * Handler that interacts with the DB.
 */
public class DBHandler {

    static HashMap<String, String[]> hardcodedFriendList;
    static String[] urls = new String[3];

    /**
     * Given the userName, fetch the list of friends.
     *
     * @param userName
     * @return
     */
    public static String[] fetchFriendsFromDB(String userName) {
            return hardcodedFriendList.get(userName);
    }



    /**
     * should return only unread urls.
     *
     * @param userID
     * @param friendID
     * @return
     */
    public static String[] fetchLatestData(String userID, String friendID) {

        return urls;
    }

    /**
     * Helper function for debugging purposes.
     *
     */
    public static void createHardcodedFriendList() {
        hardcodedFriendList = new HashMap<String, String[]>();
        String[] friends = {"Sakshi","Kapil","Joseph"};
        hardcodedFriendList.put("Pragya",friends);
        String[] friends1 = {"Pragya","Kapil","Joseph"};
        hardcodedFriendList.put("Sakshi",friends1);
        String[] friends2 = {"Sakshi","Pragya","Joseph"};
        hardcodedFriendList.put("Kapil",friends2);
        String[] friends3 = {"Sakshi","Kapil","Pragya"};
        hardcodedFriendList.put("Joseph",friends3);
    }

    /**
     * Helper function for debugging purposes.
     */
    public static void createTempFriendPictureDB() {
        urls[0] = "http://www.clarepeople.com/wordp/wp-content/uploads/2012/08/Julia-Roberts.jpg";
        urls[1] = "http://3.bp.blogspot.com/-Xe7hBLmUqIM/U51K1fp0BbI/AAAAAAAAQsw/--z3wxn3c3A/s1600/Jennifer+Aniston+Hd+Wallpaper+.+02.jpg";
        urls[2] = "http://www.therufus.com/wp-content/uploads/2013/11/Me-Jennifer-Aniston.jpg";
    }

    /**
     * sends the mood rated by the friend for the given picture(url).
     * @param url
     * @param friendName
     * @param mood
     */
    public static void sendMoodToDB(String url, String friendName, int mood) {
        return;
    }

    /**
     * Should return in % how happy the user is.
     * TODO: Discuss with Kapil how to calculate this.
     * @param userName
     * @return
     */
    public static Integer getDailyHappinessQuotient(String userName) {
        return 80;
    }

    /**
     * Some friend has marked a picture as inappropriate. DB should delete that picture.
     * This incident can be reported to the user before deleting the picture from DB,
     * and only if the user approves it, it should be deleted.
     * @param url
     */
    public static void sendReportToDB(String url) {
        return;
    }

    /**
     * Given the username, the DB should return a list(max size-24) which tells about the
     * mood of the user for that day on a per-hour basis.
     * TODO: pragya - need to change this from hashmap to list.
     * @param username
     * @return
     */
    public static HashMap<Integer,Integer> getDailyAnalysisStats(String username) {
        HashMap<Integer,Integer> analysis_map = new HashMap<Integer, Integer>();
        Random rn = new Random();
        for(int i=0;i<24;i++) {
            analysis_map.put(i,rn.nextInt(4) + 1);
        }
        return analysis_map;
    }

    /**
     * Given the username, the DB should return a list(max size-7) which tells about the
     * mood of the user for the last 7 days, or we can always make the week start from monday, instead of printing the last 7 days.
     * TODO: pragya - need to change this from hashmap to list. Discuss about implementation.
     * @param username
     * @return
     */
    public static HashMap<Integer,Integer> getWeeklyAnalysisStats(String username) {
        HashMap<Integer,Integer> analysis_map = new HashMap<Integer, Integer>();
        Random rn = new Random();
        for(int i=0;i<7;i++) {
            analysis_map.put(i,rn.nextInt(4) + 1);
        }
        return analysis_map;
    }

    public static boolean addUsertoDB(String name, String emailid, String[] emailid_of_friends_using_this_app) {
           return true;
    }

}
