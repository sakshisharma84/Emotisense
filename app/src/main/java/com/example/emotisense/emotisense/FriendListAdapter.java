package com.example.emotisense.emotisense;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pragya on 4/2/15.
 */
public class FriendListAdapter extends ArrayAdapter {
    String[] friend_names;
    Integer image_id;
    Context context;

    public FriendListAdapter(Activity context, Integer image_id, String[] text) {
        super(context, R.layout.friend_profile_layout_helper, text);
        this.friend_names = text;
        this.image_id = image_id;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.friend_list_layout_helper, null, true);
        TextView textView = (TextView) single_row.findViewById(R.id.textView);
        ImageView imageView = (ImageView) single_row.findViewById(R.id.imageView);
        textView.setText(friend_names[position]);
        if( Fbinfo.getInstance().friendImages.containsKey(friend_names[position])) {
            imageView.setImageResource(Fbinfo.getInstance().friendImages.get(friend_names[position]));
        }
        else
            imageView.setImageResource(R.drawable.account);
        return single_row;
    }
}
