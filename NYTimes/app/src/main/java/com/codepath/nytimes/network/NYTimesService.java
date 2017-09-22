package com.codepath.nytimes.network;

import android.support.annotation.NonNull;

import com.codepath.nytimes.model.NYTimesResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static android.R.attr.apiKey;
import static android.R.attr.key;

/**
 * Created by manoj on 9/22/17.
 */

public interface NYTimesService {

    @GET("/svc/search/v2/articlesearch.json")
    Observable<NYTimesResponse> getArticles(@Query("q") String query,@Query("api-key") String api_key);
}
