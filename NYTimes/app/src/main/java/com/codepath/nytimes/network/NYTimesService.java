package com.codepath.nytimes.network;

import com.codepath.nytimes.model.SearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by manoj on 9/22/17.
 */

public interface NYTimesService {

    @GET("/svc/search/v2/articlesearch.json")
    Observable<SearchResult> getArticles(@Query("q") String query, @Query("page") int page, @Query("api-key") String api_key);
}
