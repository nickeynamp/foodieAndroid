package com.example.nickp.foodieandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Inbox extends ActionBarHandler {

    ListView inboxList;
    String[] names = {
            "Cher",
            "Bert",
            "Kurt",
            "Sven",
            "Bobo",
            "Maria"
    };
    String[] previews = {
            "Wanna grab some fancy food? :)",
            "Wicked nice!",
            "Im so hungry man.",
            "I never find any cozy restaurants",
            "Why won't you answer me?",
            "I'm feeling tipsy, how about you? ;)"
    };
    String[] times = {
            "14:32",
            "13:37",
            "11:11",
            "21:06 Yesterday",
            "01/28/2017",
            "01/27/2017"
    };

    Integer[] images = {
            R.drawable.cher,
            R.drawable.bert,
            R.drawable.kurt,
            R.drawable.sven,
            R.drawable.bobo,
            R.drawable.maria
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_inbox);
        final Context inboxContext = this;
        InboxListAdapter adapter = new InboxListAdapter(this, names, previews, times, images);
        inboxList = (ListView) findViewById(R.id.inboxlist);
        inboxList.setAdapter(adapter);
        inboxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(inboxContext, Message.class);
                startActivity(intent);
            }
        });
    }
}
