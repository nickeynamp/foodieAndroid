package com.example.nickp.foodieandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nickp.foodieandroid.MainActivity;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
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
    OkHttpClient client;
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
        client = new OkHttpClient();
        super.onCreate(savedInstanceState);
        super.setActionBar(R.layout.activity_browse);
        final Context browseContext = this;
        MainActivity main = new MainActivity();



//        new askYelp().execute("0");
//        main.waitForRestaurant(false);

//        System.out.print("Has it got here ??" + names);
        BrowseListAdapter adapter = new BrowseListAdapter(this, names, previews, stars, images);
        browseList = (ListView) findViewById(R.id.browselist);
        browseList.setAdapter(adapter);
        browseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(browseContext, Restaurant.class);
//                intent.putExtra("Restaurant",restaurants.get(i));
                startActivity(intent);
            }
        });
    }

    public void expand(String[] OrigArray) {
        String[] newArray = new String[OrigArray.length + 1];
        System.arraycopy(OrigArray, 0, newArray, 0, OrigArray.length);
        OrigArray = newArray;
    }

//    class askYelp extends AsyncTask<String,RestaurantInfo,String>{
//
//        protected void onProgressUpdate(RestaurantInfo... values){
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            yelpAPI = apiFactory.createAPI();
//            Map<String,String> criteria = new HashMap<>();
//
//            // general params
//            criteria.put("limit", "3");
//            criteria.put("sort", "2");
//            criteria.put("category_filter",getIntent().getExtras().getString("Category"));
//            System.out.println("Intent received is" + getIntent().getExtras().getString("Category"));
//
//            Call<SearchResponse> call = yelpAPI.search("Kista", criteria);
//            Response<SearchResponse> response = null;
//            try {
//                response = call.execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(response!=null){
//                List<Business> businessList = response.body().businesses();
//                RestaurantInfo r;
//                int i =0;
//                for(Business b:businessList){
//                    r = new RestaurantInfo(b.name(),b.url());
//                    names.add(b.name());
////                    previews.add()
//                    stars.add(b.ratingImgUrlLarge());
//                    images.add(ResParser.getPictureURL(r.getPicUrl()));
//                    System.out.println("It gets here");
//                    r.setRatingURL(b.ratingImgUrlLarge());
//                    r.setLocation(b.location());
//                    r.setSnippetText(b.snippetText());
//                    restaurants.add(r);
//                    //loadPicture(r,i);
//                    i++;
//                }
//            }
//            return null;
//        }

//        private void loadPicture(final RestaurantInfo r, final int pos){
//
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(r.getPicUrl())
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(okhttp3.Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                    String picture = ResParser.getPictureURL(response.body().string());
//                    restaurants.get(pos).setPicUrl(picture);
//                    publishProgress(restaurants.get(pos));
//                }
//            });
//        }

//    }



}
