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

import java.util.ArrayList;
import java.util.List;

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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(restaurantInfo.getName())) {
                    RestaurantInfo FirebaseRestaurantInfo = dataSnapshot.child(restaurantInfo.getName()).getValue(RestaurantInfo.class);
                    ((TextView) findViewById(R.id.restEatersTextView)).setText(FirebaseRestaurantInfo.getEaters()+" would like to go here");
                    ((TextView) findViewById(R.id.restUserListTextView)).setText(FirebaseRestaurantInfo.getUserList().toString());
                } else {
                    List<String> list = new ArrayList<>();
                    list.add("Max");
                    restaurantInfo.setUserList(list);
                    mDatabase.child(restaurantInfo.getName()).setValue(restaurantInfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Firebase database read failed.\nMessage: "+ databaseError.getMessage());
                System.out.println("Details: " + databaseError.getDetails());
            }
        });

        if (restaurantInfo != null) {
            ((TextView) findViewById(R.id.restTitleTextView)).setText(restaurantInfo.getName());
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
        googleMap.setMinZoomPreference(15);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    public void likeRestaurant(View view) {
        if (firebaseUser != null) {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(firebaseUser.getUid()) && dataSnapshot.hasChild(restaurantInfo.getName())) {
                        Log.e("User found", "uid: "+firebaseUser.getUid());
                        Log.e("Rest found", "uid: "+restaurantInfo.getName());
                        UserInfo userInfo = dataSnapshot.child(firebaseUser.getUid()).getValue(UserInfo.class);
                        userInfo.addRest(restaurantInfo.getName());
                        mDatabase.child(firebaseUser.getUid()).setValue(userInfo);
                        RestaurantInfo rest = dataSnapshot.child(restaurantInfo.getName()).getValue(RestaurantInfo.class);
                        rest.addUser(firebaseUser.getDisplayName());
                        mDatabase.child(restaurantInfo.getName()).setValue(rest);
                    }
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
