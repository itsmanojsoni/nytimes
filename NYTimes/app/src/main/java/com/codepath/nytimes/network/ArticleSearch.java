package com.codepath.nytimes.network;

import android.database.Observable;

import com.codepath.nytimes.model.NYTimesArticle;
import com.codepath.nytimes.model.NYTimesResponse;
import com.codepath.nytimes.model.result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.apiKey;

/**
 * Created by manoj on 9/21/17.
 */

public interface ArticleSearch {

    @GET("svc/search/v2/{section}.json")
    Observable<result> searchStories(
            @Query("q") String search,
            @Query("api-key") String apiKey);

}
