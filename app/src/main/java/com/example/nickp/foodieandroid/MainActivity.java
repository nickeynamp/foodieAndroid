package com.example.nickp.foodieandroid;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends ActionBarHandler {
    protected static String stage;
    private DrawerLayout jDrawer;
    private ListView jList;
    private String[] items;
    public ActionBarDrawerToggle jToggle;
    private static final float BLUR_RADIUS = 25f;
    YelpAPIFactory apiFactory;
    YelpAPI yelpAPI;
    Map<String, String> params;
    TextView topText1,topText2,topText3,topSub1, topSub2, topSub3;
    ImageView topImage1,topImage2, topImage3;
    OkHttpClient client;
    List<RestaurantInfo> restaurants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        jDrawer = (DrawerLayout) findViewById(R.id.drawer);
        jList = (ListView) findViewById(R.id.navItems);
        items = getResources().getStringArray(R.array.navItems);
        topImage1 = (ImageView) findViewById(R.id.topImage1);
        topImage2 = (ImageView) findViewById(R.id.topImage2);
        topImage3 = (ImageView) findViewById(R.id.topImage3);
        topText1 = (TextView) findViewById(R.id.topText1);
        topText2 = (TextView) findViewById(R.id.topText2);
        topText3 = (TextView) findViewById(R.id.topText3);
        topSub1 = (TextView) findViewById(R.id.topSub1);
        topSub2 = (TextView) findViewById(R.id.topSub2);
        topSub3 = (TextView) findViewById(R.id.topSub3);
        client = new OkHttpClient();


        Picasso
                .with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .transform(new BlurTransformation(this))
                .into(topImage1);


        //set the adapter for the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, items);
        jList.setAdapter(adapter);

        //set the list click's listener
        jList.setOnItemClickListener(new DrawerItemClickListener());

        jToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                jDrawer,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        jDrawer.addDrawerListener(jToggle);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android.location.Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        displayRestaurant1(restaurants.get(1));
        new fetchPictures().execute();
        displayRestaurant1(restaurants.get(1));
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            jumpToPage(view, position);
        }
    }

    private void jumpToPage(View view, int position) {

        //Using Intent to open New Activity
        switch (position) {
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

    public void displayRestaurant1(RestaurantInfo r){
        Log.v("MESSAGE",r.getPicUrl());
        Picasso
                .with(this)
                .load(r.getPicUrl())
                .transform(new BlurTransformation(this))
                .into(topImage1);
        topText1.setText(r.getName());
        topSub1.setText(r.getEaters());
    }

    class fetchPictures extends AsyncTask<String,RestaurantInfo,String> {

        protected void onProgressUpdate(RestaurantInfo... values){
            super.onProgressUpdate(values);
        }
        @Override
        protected String doInBackground(String... strings) {
            CoordinateOptions coordinate = CoordinateOptions.builder()
                    .latitude(37.7577)
                    .longitude(-122.4376).build();

            apiFactory = new YelpAPIFactory(
                    getString(R.string.consumerKey),
                    getString(R.string.consumerSecret),
                    getString(R.string.token),
                    getString(R.string.tokenSecret));
            yelpAPI = apiFactory.createAPI();
            params = new HashMap<>();

            // general params
            params.put("limit", "3");
            params.put("sort", "2");
            params.put("radius_filter", "500");

            Call<SearchResponse> call = yelpAPI.search("Stockholm", params);
            Response<SearchResponse> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response!=null){
                Log.v("Business", response.body().businesses().toString());
                List<Business> businessList = response.body().businesses();
                restaurants = new ArrayList<>();
                RestaurantInfo r;
                int i =0;
                for(Business b:businessList){
                    r = new RestaurantInfo(b.name(),b.url());
                    r.setRating(b.rating());
                    r.setType(b.categories().toString());
                    restaurants.add(r);
                    loadPicture(r,i);
                    i++;

                }
            }
            return null;
        }
        private void loadPicture(final RestaurantInfo r, final int pos){

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(r.getPicUrl())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        String picture = ResParser.getPictureURL(response.body().string());
                        restaurants.get(pos).setPicUrl(picture);
                        publishProgress(restaurants.get(pos));
                    }
                });
        }
    }


}


