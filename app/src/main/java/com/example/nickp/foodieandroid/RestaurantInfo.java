package com.example.nickp.foodieandroid;

import com.yelp.clientlib.entities.Location;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nickp on 2017-03-03.
 */

public class RestaurantInfo implements Serializable{
    private String name;
    private int eaters;
    private String mainUrl;
    private String picUrl;
    private double rating;
    private String type;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    List<String> userList;

    public RestaurantInfo(){

    }

    public RestaurantInfo(String name, String mainUrl) {
        this.name = name;
        setMainUrl(mainUrl);
    }

    public void addUser(String user) {
        userList.add(user);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEaters() {
        return eaters;
    }

    public void setEaters(int eaters) {
        this.eaters = eaters;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
        this.picUrl = mainUrl.replace("/biz/", "/biz_photos/");
        this.picUrl += "?tab=food";
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
