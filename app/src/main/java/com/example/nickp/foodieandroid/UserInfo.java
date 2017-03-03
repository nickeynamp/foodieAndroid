package com.example.nickp.foodieandroid;

import java.util.List;

/**
 ** Created by philippe on 2017-03-02.
 */

public class UserInfo {
    public List<String> likedRestaurants;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public UserInfo(List<String> likedRestaurants) {
        this.likedRestaurants = likedRestaurants;
    }

    public void addRest(String rest) {
        likedRestaurants.add(rest);
    }
}
