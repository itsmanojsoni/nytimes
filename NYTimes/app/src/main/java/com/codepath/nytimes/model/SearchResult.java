package com.codepath.nytimes.model;

import com.codepath.nytimes.repository.NYTimesRepository;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manoj on 9/22/17.
 */

public class SearchResult {

    public NYTimesResponse getNyTimesResponse() {
        return nyTimesResponse;
    }

    @SerializedName("response")
    private NYTimesResponse nyTimesResponse;
}
