package com.codepath.nytimes.network;

import android.database.Observable;

import com.codepath.nytimes.model.SearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manoj on 9/21/17.
 */

public interface ArticleSearch {

    @GET("svc/search/v2/{section}.json")
    Observable<SearchResult> searchStories(
            @Query("q") String search,
            @Query("api-key") String apiKey);

}
