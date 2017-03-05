package com.example.nickp.foodieandroid;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Coordinate;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.io.Serializable;
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
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 200 ;
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
    boolean waiting = false;
    CoordinateOptions mCoordinate;
    private double mLatitude,mLongitude;


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
        restaurants = new ArrayList<>();

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

//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
//            return;
//        }else{
//            initLocation();
//            waitForRestaurant(false);
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        new query().execute("0");
        waitForRestaurant(false);
    }

    public void initLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLatitude = 59.32;
            mLongitude = 18.07;
            new query().execute("0");

        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                mLongitude = location.getLongitude();
                mLatitude = location.getLatitude();
                new query().execute("0");
            } else {
                Toast.makeText(this, "Getting location...", Toast.LENGTH_SHORT).show();
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setPowerRequirement(Criteria.POWER_HIGH);
                lm.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mCoordinate = CoordinateOptions.builder()
                                .latitude(location.getLatitude())
                                .longitude(location.getLongitude()).build();
                        new query().execute("0");
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        Toast.makeText(MainActivity.this, "GPS needed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        Toast.makeText(MainActivity.this, "GPS needed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Toast.makeText(MainActivity.this, "GPS needed", Toast.LENGTH_SHORT).show();
                    }
                }, null);
            }
        }
        mCoordinate = CoordinateOptions.builder()
                .latitude(mLatitude)
                .longitude(mLongitude).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Setting Stockholm as default location", Toast.LENGTH_SHORT).show();
                }
                initLocation();
                waitForRestaurant(false);
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
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
        switch (view.getId()){
            case (R.id.catAsian):
                intent.putExtra("Category", "Asian");
                break;
            case (R.id.catItalian):
                intent.putExtra("Category", "Italian");
                break;
            case (R.id.catIndian):
                intent.putExtra("Category", "Indian");
                break;
            case (R.id.catMexican):
                intent.putExtra("Category", "Mexican");
                break;
            case (R.id.catGreek):
                intent.putExtra("Category", "Greek");
                break;
            case (R.id.catFrench):
                intent.putExtra("Category", "French");
                break;
        }

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
        switch (view.getId()){
            case (R.id.topImage1):
                intent.putExtra("Restaurant",restaurants.get(0));
                break;
            case (R.id.topImage2):
                intent.putExtra("Restaurant",restaurants.get(1));
                break;
            case (R.id.topImage3):
                intent.putExtra("Restaurant",restaurants.get(2));
                break;
        }

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
                    Picasso
                            .with(this)
                            .load(r.getPicUrl())
                            .transform(new BlurTransformation(this))
                            .into(topImage1);

                    topText1.setText(r.getName());

    }

    public void displayRestaurant2(RestaurantInfo r){
        Picasso
                .with(this)
                .load(r.getPicUrl())
                .transform(new BlurTransformation(this))
                .into(topImage2);

        topText2.setText(r.getName());

    }

    public void displayRestaurant3(RestaurantInfo r){
        Picasso
                .with(this)
                .load(r.getPicUrl())
                .transform(new BlurTransformation(this))
                .into(topImage3);

        topText3.setText(r.getName());

    }

    synchronized public void waitForRestaurant(boolean client) {
        if (client) {
            if (restaurants.size() >= 3 ) {
                displayRestaurant1(restaurants.get(0));
                displayRestaurant2(restaurants.get(1));
                displayRestaurant3(restaurants.get(2));
            } else {
                waiting = true;
            }
        } else {
            if (waiting) {
                waiting = false;
                displayRestaurant1(restaurants.get(0));
                displayRestaurant2(restaurants.get(1));
                displayRestaurant3(restaurants.get(2));
            }
        }
    }

    class query extends AsyncTask<String,RestaurantInfo,String> {

        protected void onProgressUpdate(RestaurantInfo... values){
            super.onProgressUpdate(values);
            displayRestaurant1(restaurants.get(0));
            displayRestaurant2(restaurants.get(1));
            displayRestaurant3(restaurants.get(2));
        }
        @Override
        protected String doInBackground(String... strings) {
            mCoordinate = CoordinateOptions.builder()
                    .latitude(59.32)
                    .longitude(18.07).build();
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
                List<Business> businessList = response.body().businesses();
                RestaurantInfo r;
                int i =0;
                for(Business b:businessList){
                    r = new RestaurantInfo(b.name(),b.url());
                    r.setRatingURL(b.ratingImgUrlLarge());
                    r.setLocation(b.location());
                    r.setSnippetText(b.snippetText());
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


