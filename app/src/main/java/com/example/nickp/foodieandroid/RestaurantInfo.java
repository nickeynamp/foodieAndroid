package com.example.nickp.foodieandroid;

import java.util.List;

/**
 ** Created by philippe on 2017-03-02.
 */

public class RestaurantInfo {
   public List<String> userList;

    public RestaurantInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public RestaurantInfo(List<String> userList) {
        this.userList = userList;
    }

    public void addUser(String user) {
        userList.add(user);
    }
}
