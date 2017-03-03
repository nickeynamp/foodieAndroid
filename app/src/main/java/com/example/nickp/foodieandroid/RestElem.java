package com.example.nickp.foodieandroid;

/**
 * Created by nickp on 2017-03-03.
 */

public class RestElem {
    private String name;
    private int eaters;
    private String mainUrl;
    private String picUrl;
    private double rating;
    private String type;

    public RestElem(String name, String mainUrl) {
        this.name = name;
        setMainUrl(mainUrl);
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
        this.picUrl += "&tab=food";
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
