package com.codepath.nytimes.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapter.NYTimesListAdapter;
import com.codepath.nytimes.model.NYTimesArticle;
import com.codepath.nytimes.model.SearchResult;
import com.codepath.nytimes.repository.NYTimesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.offset;

public class NYTimesMainActivity extends AppCompatActivity {

    private static final int COLUMN = 2;
    private static final String TAG = "NYTimesMainActivity";
    @BindView(R.id.rvNYTimesArticleList)
    RecyclerView rvNYtimesArticleList;
    private Context context;
    private NYTimesListAdapter nyTimesListAdapter;
    private Subscription subscription;
    private EndlessRecyclerViewScrollListener scrollListener;

    private List<NYTimesArticle> nyTimesArticleList = new ArrayList<>();
    private int curSize;

    private  Handler handler = new Handler();

    private String searchQuery = "Sports";

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
        nyTimesListAdapter = new NYTimesListAdapter(this,nyTimesArticleList, new NYTimesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.d(TAG, "article clicked at position");

            }
        });

        rvNYtimesArticleList.setAdapter(nyTimesListAdapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNYtimesArticleList.setLayoutManager(layoutManager);

//        String searchQuery = "Sports";
//        getArticleList(searchQuery, 1);
        loadNextDataFromApi(searchQuery,0,rvNYtimesArticleList);

        // Endless RecycleView Scroll Listener
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount,RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(searchQuery,page, view);

            }
        };
        // Adds the scroll listener to RecyclerView
        rvNYtimesArticleList.addOnScrollListener(scrollListener);


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

    private void loadNextDataFromApi (final  String query, final int offset, final RecyclerView view) {

        Log.d(TAG, "loadNextDataFromApi and offset is : "+offset);
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                getArticleList(query,offset,view);
            }
        };

        handler.postDelayed(runnableCode, 1000);
    }

    private void getArticleList(String query, int offset, final RecyclerView view) {

        Log.d(TAG, "getArticleList call");

        subscription = NYTimesRepository.getInstance()
                .getArticles(query,offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
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
                    public void onNext(SearchResult searchResult) {
                        Log.d(TAG, "In onNext() : " + searchResult);
                        List<NYTimesArticle> nyTimesArticles = searchResult.getNyTimesResponse().getNYTimesArticleList();
                        nyTimesArticleList.addAll(nyTimesArticles);
                        curSize = nyTimesListAdapter.getItemCount();
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                nyTimesListAdapter.notifyItemRangeInserted(curSize, nyTimesArticleList.size() - 1);
                            }
                        });
                    }
                });
    }
}
