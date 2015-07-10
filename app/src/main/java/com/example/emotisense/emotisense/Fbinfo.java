package com.example.emotisense.emotisense;


        import java.util.HashMap;
        import java.util.Map;
        import java.util.Set;
        import java.lang.Object.*;
        import com.facebook.Session;

/**
 * Created by sakshi on 4/1/2015.
 */
public class Fbinfo {

    private static Fbinfo mInstance= null;

    Map mFriendInfo = null;
    String[] mFriendNames;
    String[] mFriendId;
    public String mSelfUID;
    public String mSelfName;
    //public Session fbSession;
    public HashMap<String,Integer> friendImages = new HashMap<String,Integer>();
    {
        friendImages.put("Sakshi Sharma",R.drawable.sakshi);
        friendImages.put("Pragya Agarwal",R.drawable.pragya);
        friendImages.put("Kapil Agarwal",R.drawable.kapil);
        friendImages.put("Joseph Lee",R.drawable.joseph);
    }
    public final String mood_excited = "excited";
    public final String mood_happy = "happy";
    public final String mood_confused = "confused";
    public final String mood_sad = "sad";
    public String mood_names[] = {mood_excited, mood_happy, mood_confused, mood_sad};
    public Integer image_id[] = {R.drawable.excited1, R.drawable.happy, R.drawable.confused1, R.drawable.sad2};
    public HashMap<String, Integer> mood_image_map = new HashMap<String,Integer>();
    {
        mood_image_map.put(mood_excited,R.drawable.excited1);
        mood_image_map.put(mood_happy,R.drawable.happy);
        mood_image_map.put(mood_confused,R.drawable.confused1);
        mood_image_map.put(mood_sad,R.drawable.sad2);
    }
    public HashMap<String, Integer> mood_map = mood_map = new HashMap<String,Integer>();
    {
        mood_map.put(mood_excited,2);
        mood_map.put(mood_happy,1);
        mood_map.put(mood_confused,-1);
        mood_map.put(mood_sad,-2);
    }



    protected Fbinfo(){}

    public static synchronized Fbinfo getInstance(){
        if(null == mInstance){
            mInstance = new Fbinfo();
        }
        return mInstance;
    }

   /* public void setFbSession(Session session) {
        fbSession = session;
    }*/

    public void setFriendInfo(Map fMap) {

        mFriendInfo = fMap;
    }
    public Map getFriendinfo (){

        return mFriendInfo;
    }
    public void setSelfInfo (String name, String UID) {
        mSelfUID = UID;
        mSelfName = name;

    }
    public String getSelfName () {

        return mSelfName;

    }
    public String getSelfId () {

        return mSelfUID;
    }
    public void printInfo (){
        System.out.println("Self Name: " + mSelfName);
        System.out.println("Self ID: " + mSelfUID);
        if(mFriendInfo != null) {
            System.out.println("mFriendInfo: " + mFriendInfo.size());
            Set keyset = mFriendInfo.keySet();
            System.out.println("Key set values are: " + keyset);
        }

    }

}
