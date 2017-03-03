package com.example.nickp.foodieandroid;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends ActionBarHandler {
    private DatabaseReference mDatabase;
    private UserInfo userInfo;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_restaurant);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        List<String> userList = new ArrayList<>();
        userList.size();
        userList.add("Ulrik");
        userList.add("Ida");
        userList.add("Falk");
        RestaurantInfo restaurantInfo = new RestaurantInfo(userList);
        mDatabase.child("Koh Phangan").setValue(restaurantInfo);

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
