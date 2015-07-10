package com.example.emotisense.emotisense;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;

/**
 * Created by sakshi on 4/7/2015.
 */
public class Share {


    public static void publishFeedDialog(final Activity current, final String title,
                                         final String caption, final String description, final String link
    ) {
        // start Facebook Login
        Session.openActiveSession(current, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state,
                             Exception exception) {
                if (session.isOpened()) {
                    Bundle params = new Bundle();
                    params.putString("name", title);
                    params.putString("caption", caption);
                    params.putString("description", description);
                    params.putString("link", link);
                    params.putString("picture", "http://ilovehdwallpapers.com/thumbs/colorful-hands-t2.jpg");
                    //params.putString("picture", pictureUrl);

                    WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
                            current, Session.getActiveSession(), params))
                            .setOnCompleteListener(new WebDialog.OnCompleteListener() {

                                @Override
                                public void onComplete(Bundle values,
                                                       FacebookException error) {
                                    if (error == null) {
                                        // When the story is posted, echo the
                                        // success
                                        // and the post Id.
                                        final String postId = values
                                                .getString("post_id");
                                        if (postId != null) {
                                            System.out.println("Posted");
                                        } else {
                                            // User clicked the Cancel button
                                            System.out.println("Publish cancelled");
                                        }
                                    } else if (error instanceof FacebookOperationCanceledException) {
                                        // User clicked the "x" button
                                        System.out.println("Publish cancelled");
                                    } else {
                                        // Generic, ex: network error
                                        System.out.println("Error posting story");
                                    }
                                }

                            }).build();
                    feedDialog.show();
                }
            }
        });


    }

}
