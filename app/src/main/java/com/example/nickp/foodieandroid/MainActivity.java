package com.example.nickp.foodieandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.res.Configuration;
import android.view.MenuItem;

public class MainActivity extends ActionBarHandler {
    private DrawerLayout jDrawer;
    private ListView jList;
    private String[] items;
    public ActionBarDrawerToggle jToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        jDrawer = (DrawerLayout) findViewById(R.id.drawer);
        jList = (ListView) findViewById(R.id.navItems);
        items = getResources().getStringArray(R.array.navItems);

        //set the adapter for the list view
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.drawer_list_item,items);
        jList.setAdapter(adapter);

        //set the list click's listener
        jList.setOnItemClickListener(new DrawerItemClickListener());

        jToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                jDrawer,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        jDrawer.addDrawerListener(jToggle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent,View view, int position, long id){
            jumpToPage(view, position);
        }
    }

    private void jumpToPage(View view, int position){

        //Using Intent to open New Activity
        switch (position){
            case 0:
                goToBrowse(view);
                break;
            case 1:
                goToInbox(view);
                break;
            case 2:
                goToLike(view);
                break;
            case 3:
                goToMessage(view);
                break;
            case 4:
                goToRestaurant(view);
                break;
            case 5:
                goToUser(view);
                break;
            default:
                System.out.print("ERROR");
                break;
        }

        jList.setItemChecked(position, true);
        jDrawer.closeDrawer(jList);
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


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        jToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        jToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (jToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

            return super.onOptionsItemSelected(item);
        }


}


