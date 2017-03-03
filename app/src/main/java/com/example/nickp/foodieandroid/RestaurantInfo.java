package com.example.nickp.foodieandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by nickp on 2017-03-03.
 */

public class RestaurantInfo {
    private String name;
    private int eaters;
    private String mainUrl;
    private String picUrl;
    private String ratingImg;
    private String type;
    List<String> userList;
    private double latitude;
    private double longitude;
    private String snippet;

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

    public String getRatingImg() {
        return ratingImg;
    }

    public void setRatingImg(String ratingImg) {
        this.ratingImg = ratingImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
