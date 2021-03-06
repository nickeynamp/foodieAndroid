package com.example.nickp.foodieandroid;

import android.app.Activity;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 ** Created by philippe on 2017-02-15.
 */

class BrowseListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final String[] previews;
    private final String[] stars;
    private final String[] images;

    BrowseListAdapter(Activity context, String[] names, String[] previews, String[] stars, String[] images) {
        super(context, R.layout.inbox_item, names);
        this.context = context;
        this.names = names;
        this.previews = previews;
        this.stars = stars;
        this.images = images;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.browse_item, null, true);

        TextView nameText = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView previewText = (TextView) rowView.findViewById(R.id.previewTextView);
        ImageView starsImage = (ImageView) rowView.findViewById(R.id.starsImageView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.avatarImageView);

        nameText.setText(names[position]);
//        previewText.setText(previews[position]);

        Picasso
                .with(this.context)
                .load(stars[position])
                .into(starsImage);

        Picasso
                .with(this.context)
                .load(images[position])
                .into(imageView);
        return rowView;
    }
}
