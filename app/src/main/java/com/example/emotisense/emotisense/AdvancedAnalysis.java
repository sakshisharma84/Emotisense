package com.example.emotisense.emotisense;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Pragya on 3/18/15.
 *
 * Given the username, the class displays user's photos(most recent one on top) along with each friend's rating on each photo.
 * TODO: left as future work.
 *
 */
public class AdvancedAnalysis extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final String userName = getIntent().getExtras().getString("User");
    }
}
