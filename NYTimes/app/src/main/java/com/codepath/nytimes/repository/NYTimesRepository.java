package com.codepath.nytimes.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codepath.nytimes.model.SearchResult;
import com.codepath.nytimes.network.NYTimesService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static android.content.ContentValues.TAG;

/**
 * Created by manoj on 9/22/17.
 */

public class NYTimesRepository {

    private static final String GITHUB_BASE_URL = "http://api.nytimes.com/";
    private static final String API_KEY = "96fc048ae36d495cba184a6d94440a0f";

    private static NYTimesRepository instance;
    private NYTimesService nyTimesService;

    private NYTimesRepository() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        nyTimesService = retrofit.create(NYTimesService.class);
    }

    public static NYTimesRepository getInstance() {
        if (instance == null) {
            instance = new NYTimesRepository();
        }
        return instance;
    }

    public Observable<SearchResult> getArticles(@NonNull String query, @NonNull int page) {
        return nyTimesService.getArticles(query,page,API_KEY);
    }

    public Observable<SearchResult> getFilteredArticle(String date,String sort, String param1, String param2, String param3, int page) {

        String newdata = "news_desk:("+param1 + " " + param2 + " " + param3 + ")";

        Log.d(TAG, "New Data is : "+newdata);

        return nyTimesService.getFilteredArticle(date,sort,newdata,page,API_KEY);
    }


}
