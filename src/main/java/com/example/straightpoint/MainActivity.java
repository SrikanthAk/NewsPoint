package com.example.straightpoint;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<NewsItems>> {

    //URL for News articles data from NewsApi.org
    private static final String NEWSAPI_REQUEST_URL = "https://newsapi.org/v2/top-headlines?country=in";
    /** Adapter for the list of news items */
    private  NewsAdapter newsAdapter;
    /**
     * Constant value for the newsitems loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWSITEM_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity","MainActivity onCreate Method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsItemsListView = findViewById(R.id.listview_articles);
        newsAdapter = new NewsAdapter(this, new ArrayList<NewsItems>());
        newsItemsListView.setAdapter(newsAdapter);


        newsItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //find the current news item that was clicked on
                NewsItems currentNewsItem = newsAdapter.getItem(position);
                // Create a new intent to view the news item URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(currentNewsItem.getmUrl()));
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsItemsListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            //getSupportLoaderManager().initLoader(NEWSITEM_LOADER_ID, null,  this);

            androidx.loader.app.LoaderManager loaderManager = getSupportLoaderManager();
            Log.i("MainActivity","Calling initLoader");
            loaderManager.initLoader(NEWSITEM_LOADER_ID, null,  this);
        }else{
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public androidx.loader.content.Loader<List<NewsItems>> onCreateLoader(int id, Bundle args) {
        Log.i("MainActivity","MainActivity onCreateLoader Method");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String category = sharedPrefs.getString(getString(R.string.news_order_by_key), getString(R.string.news_order_by_default));
        Uri baseUri = Uri.parse(NEWSAPI_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("category", category);
        uriBuilder.appendQueryParameter("apiKey","4cd130d6b66e48d18ca5261cf3ca209d");
        return new NewsItemLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<NewsItems>> loader, List<NewsItems> data) {
        Log.i("MainActivity","MainActivity onLoadFinished Method");
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText(R.string.no_news);
        // Clear the adapter of previous newsitems data
        newsAdapter.clear();

        // If there is a valid list of {@link NewsItems}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<NewsItems>> loader) {
        Log.i("MainActivity","LoadManager onLoaderReset Method");
        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SelectPriority.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
