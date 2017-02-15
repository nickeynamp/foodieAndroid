package com.example.nickp.foodieandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 ** Created by philippe on 2017-02-15.
 */

class InboxListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final String[] previews;
    private final String[] times;
    private final Integer[] images;

    InboxListAdapter(Activity context, String[] names, String[] previews, String[] times, Integer[] images) {
        super(context, R.layout.inboxlist, names);
        this.context = context;
        this.names = names;
        this.previews = previews;
        this.times = times;
        this.images = images;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.inboxlist, null, true);

        TextView nameText = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView previewText = (TextView) rowView.findViewById(R.id.previewTextView);
        TextView timeText = (TextView) rowView.findViewById(R.id.timeTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.avatarImageView);

        nameText.setText(names[position]);
        previewText.setText(previews[position]);
        timeText.setText(times[position]);
        imageView.setImageResource(images[position]);

        return rowView;
    }
}
