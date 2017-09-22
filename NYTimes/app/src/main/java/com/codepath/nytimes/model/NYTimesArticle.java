package com.codepath.nytimes.model;

import java.util.List;

/**
 * Created by manoj on 9/21/17.
 */

public class NYTimesArticle {
    String snippet;
    String source;
    String pub_date;
    String new_desk;
    String web_url;

    public String getWeb_url() {
        return web_url;
    }

    public List<Multimedia> getMultimediaList() {
        return multimediaList;
    }

    List<Multimedia> multimediaList;

    public String getSnippet() {
        return snippet;
    }

    public String getSource() {
        return source;
    }

    public String getPub_date() {
        return pub_date;
    }

    public String getNew_desk() {
        return new_desk;
    }
}
