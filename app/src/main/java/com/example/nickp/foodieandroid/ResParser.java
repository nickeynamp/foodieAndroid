package com.example.nickp.foodieandroid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickp on 2017-03-03.
 */

public class ResParser {
    public static String getPictureURL(String html){
        Document doc = Jsoup.parse(html);
        Elements images = doc.select("div.photo-box > img");
        int random = (int )(Math.random() * (double)images.size() + 1);
        String url;
        url = images.get(random).attr("url");

        int i;
        for (i = url.length() - 1; i > 0; i--) {
            if (url.charAt(i) == '/') {
                break;
            }
        }
        return url.substring(0,i+1) + "o.jpg";
    }
}
