package com.codepath.nytimes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.parceler.Parcels;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

        Log.d(TAG, "User id is : "+user.getId());
        Log.d(TAG, "User id is : "+user.getName());
        Log.d(TAG, "User id is : "+user.getRollNumber());
    }
}
