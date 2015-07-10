package com.example.emotisense.emotisense;

/**
 * Created by Pragya on 3/5/15.
 *
 * Helper class which stores the users information.
 *
 * Shared Preference maybe.
 */
public class UserProfile {

    public static String emailId="10206242692930240";
    public static String getUserName() {
        return emailId;
    }

    public static void setUserName(String user) {
        emailId = user;
    }
}
