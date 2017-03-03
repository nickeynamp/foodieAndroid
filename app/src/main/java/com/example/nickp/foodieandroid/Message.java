package com.example.nickp.foodieandroid;

import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class Message extends ActionBarHandler {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }
        super.onCreate(savedInstanceState, R.layout.activity_message);
    }


}
