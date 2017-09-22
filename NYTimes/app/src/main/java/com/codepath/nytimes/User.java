package com.codepath.nytimes;

import org.parceler.Parcel;

/**
 * Created by manoj on 9/20/17.
 */

@Parcel
public class User {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRollNumber() {
        return rollNumber;
    }

    public User() {

    }

    public User (String id, String name, Integer rollNumber) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
    }

    String id;
    String name;
    Integer rollNumber;
}
