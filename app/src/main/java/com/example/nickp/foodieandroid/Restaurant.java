package com.example.nickp.foodieandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Restaurant extends ActionBarHandler implements OnMapReadyCallback {
    private DatabaseReference mDatabase;
    private UserInfo userInfo;
    private FirebaseUser firebaseUser;

    private RestaurantInfo restaurantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setActionBar(R.layout.activity_restaurant);
        Intent intent = getIntent();
        restaurantInfo = (RestaurantInfo) intent.getExtras().getSerializable("Restaurant");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        /*restaurantInfo = new RestaurantInfo();
        restaurantInfo.setName("McDonald's");
        restaurantInfo.setPicUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Mcdonalds-90s-logo.svg/220px-Mcdonalds-90s-logo.svg.png");
        restaurantInfo.setRatingURL("http://4.bp.blogspot.com/-ehsTsJRarY0/UcijW6H2gsI/AAAAAAAADDI/8DPg8KJgxfI/s1600/3+Stars.jpg");
        restaurantInfo.setEaters(8);
        //restaurantInfo.setLatitude(-33.852);
        //restaurantInfo.setLongitude(151.211);
        restaurantInfo.setSnippetText("This Thai Island themed joint is unique. The inside takes you into a world totally foreign to the urban jungle of Stockholm. Koh Phangan has created a Thai Island secluded playaround");
        */

        if (restaurantInfo != null) {
            ((TextView) findViewById(R.id.restTitleTextView)).setText(restaurantInfo.getName());
            ((TextView) findViewById(R.id.restEatersTextView)).setText(restaurantInfo.getEaters()+" would like to go here");
            ((TextView) findViewById(R.id.restSnippetTextView)).setText(restaurantInfo.getSnippetText());
            Picasso
                    .with(this)
                    .load(restaurantInfo.getPicUrl())
                    .into((ImageView) findViewById(R.id.restHeaderImgImageView));

            Picasso
                    .with(this)
                    .load(restaurantInfo.getRatingURL())
                    .into((ImageView) findViewById(R.id.restRatingImgImageView));
        }

        // Retrieve the content view that renders the map.
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Log.e("LONGITUDE", ""+restaurantInfo.getLongitude());
        Log.e("LATITUDE", ""+restaurantInfo.getLatitude());
        LatLng position = new LatLng(restaurantInfo.getLatitude(), restaurantInfo.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(position)
                .title("Restaurant's Position"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    public void likeRestaurant(View view) {
        if (firebaseUser != null) {
            mDatabase.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    userInfo.addRest("Koh Phangan");
                    mDatabase.child(firebaseUser.getUid()).setValue(userInfo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Firebase database read failed.\nMessage: "+ databaseError.getMessage());
                    System.out.println("Details: " + databaseError.getDetails());
                }
            });
        }
    }
}
