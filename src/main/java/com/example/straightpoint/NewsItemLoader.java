package com.example.straightpoint;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

/**
 * Loads a list of news items by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsItemLoader extends AsyncTaskLoader<List<NewsItems>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsItemLoader.class.getName();
    /**
     * Query URL
     */
    private String mUrl;


    /**
     * Constructs a new {@link NewsItemLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsItemLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("NewsItemLoader","NewsItemLoader onStartLoading Method");
        forceLoad();
    }

    @Override
    /**
     * This is on a background thread.
     */
    public List<NewsItems> loadInBackground() {
        Log.i("NewsItemLoader","NewsItemLoader loadInBackground Method");

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of newsitems.
        List<NewsItems> newsItems = QueryUtils.fetchNewsItemsData(mUrl);
        return newsItems;
    }
}
