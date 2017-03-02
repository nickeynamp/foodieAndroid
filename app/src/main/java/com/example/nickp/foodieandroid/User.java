package com.example.nickp.foodieandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class User extends ActionBarHandler {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 555;

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
        super.onCreate(savedInstanceState, R.layout.activity_user);
        if (auth != null) {
            TextView username = (TextView) findViewById(R.id.usernameTextView);
            username.setText(auth.getCurrentUser().getDisplayName());
        }
    }

    public void signOut(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(User.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
