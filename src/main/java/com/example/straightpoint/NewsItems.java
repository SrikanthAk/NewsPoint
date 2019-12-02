package com.example.straightpoint;

import android.util.Log;

/**
 * {@link NewsItems} represents a single Android platform release.
 * Each object has 6 properties: news website, title, content, author name, published date, url.
 */
public class NewsItems {

    //website name where news article sourced
    private String mWebsiteName;

    //news article title
    private String mArticleTitle;


    //url of  an Image of an article
    private String mImageURL;

    //published date of article
    private String publishedDate;

    //Website url of news article
    private String mUrl;

    /**
     * Constructs a new {@link NewsItems} object
     * @param websiteName is website name of news article
     * @param articleTitle is title or headline of the article
     * @param imageURL is an Image of an article
     * @param time is published date of article
     * @param url is url of that specific article in that website.
     */
    public NewsItems(String websiteName, String articleTitle, String imageURL, String time, String url) {
        mWebsiteName = websiteName;
        mArticleTitle = articleTitle;
        mImageURL = imageURL;
        publishedDate = time;
        mUrl = url;
    }

    public NewsItems(){

    }


    /**
     *
     * @return website name in String
     */
    public String getmWebsiteName() {
        return mWebsiteName;
    }

    /**
     * @return article title
     */

    public String getmArticleTitle() {
        return mArticleTitle;
    }

    /**
     *
     * @return url of an Article Image
     */
    public String getmImageURL() {
        return mImageURL;
    }

    /**
     * @return article published date
     */
    public String getPublishedDate() {
        return publishedDate;
    }
    /**
     * @return article url
     */
    public String getmUrl() {
        return mUrl;
    }
}
