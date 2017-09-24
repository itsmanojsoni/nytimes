package com.codepath.nytimes.repository;

import com.codepath.nytimes.model.NYTimesSearchResult;
import com.codepath.nytimes.network.NYTimesService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by manoj on 9/22/17.
 */

public class NYTimesRepository {

    private static final String NYTIMES_BASE_URL = "http://api.nytimes.com/";
    private static final String API_KEY = "96fc048ae36d495cba184a6d94440a0f";

    private static NYTimesRepository instance;
    private NYTimesService nyTimesService;

    private NYTimesRepository() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(NYTIMES_BASE_URL)
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

    public Observable<NYTimesSearchResult> getArticleList(String query, String date, String sort, String newDeskString, int page) {
        return nyTimesService.getArticleList(query,date,sort,newDeskString,page,API_KEY);
    }
}
