package com.codepath.nytimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class NYTimesMainActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytimes_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button button = (Button) findViewById(R.id.btnClick);

        context = (Context) this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User("1","Soni", 967);
                Intent intent = new Intent(context,UserActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


}
