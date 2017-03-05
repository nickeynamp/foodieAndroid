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
    private String ratingURL;
    private String snippetText;

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

    public String getSnippetText() {
        return snippetText;
    }

    public void setSnippetText(String snippetText) {
        this.snippetText = snippetText;
    }

    public String getRatingURL() {
        return ratingURL;
    }

    public void setRatingURL(String ratingURL) {
        this.ratingURL = ratingURL;
    }

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

}
