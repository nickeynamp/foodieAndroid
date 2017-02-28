package com.example.nickp.foodieandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Browse extends ActionBarHandler {

    ListView browseList;
    String [] names = {
            "Kohphangan",
            "Happy Su",
            "Sushi Yama",
            "Mc Donald's"
    };
    String[] previews = {
            "34",
            "23",
            "88",
            "311"
    };
    String[] stars = {
            "3.5",
            "4",
            "3",
            "2.5"
    };
    Integer[] images = {
            R.drawable.spaghetti1,
            R.drawable.salmon,
            R.drawable.spaghetti1,
            R.drawable.chocopie
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_browse);
        final Context browseContext = this;
        BrowseListAdapter adapter = new BrowseListAdapter(this, names, previews, stars, images);
        browseList = (ListView) findViewById(R.id.browselist);
        browseList.setAdapter(adapter);
        browseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(browseContext, Restaurant.class);
                startActivity(intent);
            }
        });
    }
}
