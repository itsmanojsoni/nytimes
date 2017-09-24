package com.codepath.nytimes.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by manoj on 9/21/17.
 */
@Parcel
public class NYTimesArticle {
    String snippet;
    String source;
    String pub_date;
    String new_desk;
    String web_url;
    List<Multimedia> multimedia;

    NYTimesArticle () {

    }

    public String getHeadlines() {
        return headline.getMain();
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    Headline headline;


    public String getWeb_url() {
        return web_url;
    }

    public List<Multimedia> getMultimediaList() {
        return multimedia;
    }


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

    @Parcel
    static class Headline {
        String main;
        String print_headline;

        Headline() {

        }

        public String getMain() {
            return main;
        }

        public String getPrint_headline() {
            return print_headline;
        }
    }
}
