package com.codepath.nytimes.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapter.NYTimesListAdapter;
import com.codepath.nytimes.fragment.SearchFilter;
import com.codepath.nytimes.model.NYTimesArticle;
import com.codepath.nytimes.model.NYTimesSearchResult;
import com.codepath.nytimes.repository.NYTimesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.Toast;

import org.parceler.Parcels;

public class NYTimesMainActivity extends AppCompatActivity {

    private static final int COLUMN = 2;
    private static final String TAG = "NYTimesMainActivity";
    @BindView(R.id.rvNYTimesArticleList) RecyclerView rvNYtimesArticleList;
    private NYTimesListAdapter nyTimesListAdapter;
    private Subscription subscription;
    private EndlessRecyclerViewScrollListener scrollListener;

    private List<NYTimesArticle> nyTimesArticleList = new ArrayList<>();
    private int curSize;

    private  Handler handler = new Handler();

    private int TIME_OUT = 2000; // TIME in MS

    private String mDate;
    private String mSort;
    private String mCategory1, mCategory2,mCategory3;
    private String query;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytimes_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final GridLayoutManager layoutManager = new GridLayoutManager(this, COLUMN);
        nyTimesListAdapter = new NYTimesListAdapter(this,nyTimesArticleList, new NYTimesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                NYTimesArticle nyTimesArticle = nyTimesArticleList.get(position);
                intent.putExtra("article", Parcels.wrap(nyTimesArticle));
                startActivity(intent);
            }
        });

        rvNYtimesArticleList.setAdapter(nyTimesListAdapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNYtimesArticleList.setLayoutManager(layoutManager);
        rvNYtimesArticleList.setHasFixedSize(true);

        // Endless RecycleView Scroll Listener
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount,RecyclerView view) {
                loadMoreData(page, view);
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
        MenuItem searchItem = menu.findItem(R.id.action_search);

        MenuItem filterItem = menu.findItem(R.id.itemFilter);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                searchView.clearFocus();
                nyTimesArticleList.clear();
                scrollListener.resetState();
                nyTimesListAdapter.notifyDataSetChanged();
                setQuertyText(query);
                getArticleList(query,mDate,mSort,mCategory1,mCategory2,mCategory3,0,rvNYtimesArticleList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                showSearchFilterDialog();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setQuertyText(String query) {
        this.query = query;
    }


    private void loadMoreData(final int offset, final RecyclerView view) {

        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                getArticleList(query,mDate,mSort,mCategory1,mCategory2,mCategory3,offset,view);
            }
        };

        handler.postDelayed(runnableCode,TIME_OUT);

    }

    private void getArticleList(final  String query, final  String date, final String sort, final String param1, final String param2, final String param3, final int offset, final RecyclerView view) {

        String newDeskString = getNewDeskString(param1, param2, param3);

        Log.d(TAG, "Filtered Search Query =" +
                " "+query+ " date = "+date+ " sort = "+sort+ " newDesk = "+newDeskString+ " page = "+offset);


        subscription = NYTimesRepository.getInstance()
                .getArticleList(query,date,sort,newDeskString, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NYTimesSearchResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), (String)getErrorMessage(e),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(NYTimesSearchResult searchResult) {
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

    private void showSearchFilterDialog( ) {

        FragmentManager fm = getSupportFragmentManager();
        SearchFilter searchFilter = SearchFilter.newInstance("Some Title");
        searchFilter.setSearchFilterDialogueListener(new SearchFilter.SearchFilterDialogueListener() {
            @Override
            public void onSaveSearchFilterDone(String date, String sort, String param1, String param2, String param3) {
                searchView.clearFocus();
                nyTimesArticleList.clear();
                scrollListener.resetState();
                nyTimesListAdapter.notifyDataSetChanged();

                saveSearchData(date,sort,param1,param2,param3);
                getArticleList(query,date, sort, param1, param2, param3, 0,rvNYtimesArticleList);

            }
        });
        searchFilter.show(fm, "fragment_edit_name");

    }

    private void saveSearchData(String date, String sort, String param1, String param2, String param3) {
        this.mDate = date;
        this.mSort = sort;
        this.mCategory1 = param1;
        this.mCategory2 = param2;
        this.mCategory3 = param3;
    }

    private String getNewDeskString(String param1, String param2, String param3) {

        if (param1 == null && param2 == null && param3 == null) {
            return null;
        }

        StringBuilder newDeskString = new StringBuilder();

        newDeskString.append("news_desk:(");

        if (param1!= null && !param1.isEmpty()) {
            newDeskString.append(param1);
            newDeskString.append(" ");
        }

        if (param2!= null && !param2.isEmpty()) {
            newDeskString.append(param2);
            newDeskString.append(" ");
        }

        if (param3!= null && !param3.isEmpty()) {
            newDeskString.append(param3);
        }
        newDeskString.append(")");
        return newDeskString.toString();
    }

    private String getErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
