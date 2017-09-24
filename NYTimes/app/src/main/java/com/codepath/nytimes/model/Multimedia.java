package com.codepath.nytimes.model;

import org.parceler.Parcel;

/**
 * Created by manoj on 9/22/17.
 */

@Parcel
public class Multimedia {

    String type;
    String subtype;
    int width;
    int height;
    String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    Multimedia() {

    }

}
