package com.codepath.nytimes.network;

import com.codepath.nytimes.model.NYTimesSearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by manoj on 9/22/17.
 */

public interface NYTimesService {

    @GET("/svc/search/v2/articlesearch.json")
    Observable<NYTimesSearchResult> getArticleList(@Query("q") String query,
                                                   @Query("begin_date") String begin_date,
                                                   @Query("sort'") String sort,
                                                   @Query("fq") String category,
                                                   @Query("page") int page,
                                                   @Query("api-key") String api_key);
}
