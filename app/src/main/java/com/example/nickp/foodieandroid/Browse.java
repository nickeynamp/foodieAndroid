package com.example.nickp.foodieandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.nickp.foodieandroid.MainActivity.apiFactory;
import static com.example.nickp.foodieandroid.MainActivity.yelpAPI;


public class Browse extends ActionBarHandler {

    ListView browseList;
    List<RestaurantInfo> results;
    OkHttpClient client;
    boolean waiting = false;
    String [] names = {};
    String[] previews = {};
    String[] stars = {};
    String[] images = {
            "https://s3-media1.fl.yelpcdn.com/bphoto/yG7tUZdstWgmiuEFJPBgRw/o.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = new OkHttpClient();
        results = new ArrayList<>();
        super.onCreate(savedInstanceState);
        super.setActionBar(R.layout.activity_browse);

        new askYelp().execute("0");
        waitForLoading(false);

    }

    public void updateInfo(){
        final Context browseContext = this;
        BrowseListAdapter adapter = new BrowseListAdapter(this, names, previews, stars, images);
        browseList = (ListView) findViewById(R.id.browselist);
        browseList.setAdapter(adapter);
        browseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(browseContext, Restaurant.class);
                intent.putExtra("Restaurant",results.get(i));
                startActivity(intent);
            }
        });
    }

    synchronized public void waitForLoading(boolean client) {
        if (client) {
            if (results.size() >= 5 ) {
                updateInfo();
            } else {
                waiting = true;
            }
        } else {
            if (waiting) {
                waiting = false;
                updateInfo();
            }
        }
    }

    public String[] expand(String[] OrigArray, String newElem) {
        String[] newArray = new String[OrigArray.length + 1];
        System.arraycopy(OrigArray, 0, newArray, 0, OrigArray.length);
        newArray[newArray.length-1] = newElem;
        return newArray;

    }

    class askYelp extends AsyncTask<String,String,String>{

        protected void onProgressUpdate(String... values){

            super.onProgressUpdate(values);
            updateInfo();
        }

        @Override
        protected String doInBackground(String... params) {
            yelpAPI = apiFactory.createAPI();
            Map<String,String> criteria = new HashMap<>();

            // general params
            criteria.put("limit", "5");
            criteria.put("sort", "2");
            if(getIntent().hasExtra("Category"))
              criteria.put("category_filter",getIntent().getExtras().getString("Category"));

            Call<SearchResponse> call = yelpAPI.search("Kista", criteria);
            Response<SearchResponse> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("EXECUTED RIGHT NOW");
            if(response!=null){
                List<Business> businessList = response.body().businesses();
                RestaurantInfo r;
                int i =0;
                for(Business b:businessList){
                    r = new RestaurantInfo(b.name(),b.url());
                    names = expand(names,b.name());
                    System.out.println("Names are" + Arrays.toString(names));
//                    previews.add()
                    stars = expand(stars,b.ratingImgUrlLarge());
//                    String properURL = ResParser.getPictureURL(r.getPicUrl());
//                    System.out.println("Proper URL is" + properURL);
//                    images = expand(images,properURL);
                    r.setRatingURL(b.ratingImgUrlLarge());
                    r.setLongitude(b.location().coordinate().longitude());
                    r.setLatitude(b.location().coordinate().latitude());
                    r.setSnippetText(b.snippetText());
                    results.add(r);
                    loadPicture(r,i);
                    i++;
                }
                System.out.println("What inside Images are" + Arrays.toString(images));
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
                    System.out.println("IS CALLED" + picture);
                    results.get(pos).setPicUrl(picture);
                    images = expand(images,picture);
                    publishProgress(picture);
                }
            });
        }

    }



}
