package com.example.emotisense.emotisense;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
/**
 * Created by Pragya on 3/18/15.
 *
 * Displays text once all photos of the user's friend are read.
 */
public class NoMoreImagesPage extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.no_more_images_layout);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("No more images to show.");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FriendList.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
