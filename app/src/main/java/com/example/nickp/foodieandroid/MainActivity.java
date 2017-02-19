package com.example.nickp.foodieandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ActionBarHandler {

    private DrawerLayout jDrawer;
    private ListView jList;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        jDrawer = (DrawerLayout) findViewById(R.id.drawer);
        //jList = (ListView) findViewById(R.id.navItems);

        //set shadow to mask main content when drawer opens if there exist PNGs to mask
        //jDrawer.setDrawerShadow(R.drawable.dr);

        //set the adapter for the list view
        //jList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, items));

        //set the list click's listener
        //jList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void goToBrowse(View view) {
        Intent intent = new Intent(this, Browse.class);
        startActivity(intent);
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(this, Inbox.class);
        startActivity(intent);
    }

    public void goToLike(View view) {
        Intent intent = new Intent(this, Like.class);
        startActivity(intent);
    }

    public void goToMessage(View view) {
        Intent intent = new Intent(this, Message.class);
        startActivity(intent);
    }

    public void goToRestaurant(View view) {
        Intent intent = new Intent(this, Restaurant.class);
        startActivity(intent);
    }

    public void goToUser(View view) {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

}
