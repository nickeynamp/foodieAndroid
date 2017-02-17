package com.example.nickp.foodieandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 ** Created by philippe on 2017-02-17.
 */

class ActionBarHandler extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState, int contentView) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbuttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle action bar activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                Intent userIntent = new Intent(this, User.class);
                startActivity(userIntent);
                return true;

            case R.id.action_inbox:
                Intent inboxIntent = new Intent(this, Inbox.class);
                startActivity(inboxIntent);
                return true;

            case R.id.action_like:
                Intent likeIntent = new Intent(this, Like.class);
                startActivity(likeIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
