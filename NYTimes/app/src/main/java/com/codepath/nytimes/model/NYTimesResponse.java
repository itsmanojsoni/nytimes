package com.codepath.nytimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manoj on 9/21/17.
 */

public class NYTimesResponse {


    public List<NYTimesArticle> getNYTimesArticleList() {
        return nyTimesArticleList;
    }

    @SerializedName("docs")
    public List<NYTimesArticle> nyTimesArticleList;
}
