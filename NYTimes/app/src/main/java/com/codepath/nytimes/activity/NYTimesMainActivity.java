package com.codepath.nytimes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.codepath.nytimes.R;
import com.codepath.nytimes.User;
import com.codepath.nytimes.UserActivity;
import com.codepath.nytimes.adapter.NYTimesListAdapter;
import com.codepath.nytimes.model.NYTimesResponse;
import com.codepath.nytimes.repository.NYTimesRepository;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NYTimesMainActivity extends AppCompatActivity {

    private Context context;
    private static final int COLUMN = 2;
    private NYTimesListAdapter nyTimesListAdapter;
    private Subscription subscription;
    private static final String TAG = "NYTimesMainActivity";
    @BindView(R.id.rvNYTimesArticleList)
    RecyclerView rvNYtimesArticleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytimes_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        Button button = (Button) findViewById(R.id.btnClick);

//        context = (Context) this;

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                User user = new User("1","Soni", 967);
//                Intent intent = new Intent(context,UserActivity.class);
//                intent.putExtra("user", Parcels.wrap(user));
//                startActivity(intent);
//
//            }
//        });

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        nyTimesListAdapter = new NYTimesListAdapter(this, new NYTimesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.d(TAG, "article clicked at position");

            }
        });

        rvNYtimesArticleList.setAdapter(nyTimesListAdapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNYtimesArticleList.setLayoutManager(layoutManager);

        String searchQuery = "Sports";
        getArticleList(searchQuery);

    }

    @Override
    protected void onPause() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getArticleList(String query) {

        subscription = NYTimesRepository.getInstance()
                .getArticles(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NYTimesResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override
                    public void onNext(NYTimesResponse nyTimesResponse) {
                        Log.d(TAG, "In onNext()");
//                        adapter.setGitHubRepos(gitHubRepos);
                    }
                });
    }
}
