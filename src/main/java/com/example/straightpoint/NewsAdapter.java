package com.example.straightpoint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * {@link NewsAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link NewsItems} objects.
 */
public class NewsAdapter extends ArrayAdapter<NewsItems> {
    private String mPublishedDate = "";
    /**
     * Constructs a {@link NewsAdapter} objects
     * @param context context of the app
     * @param newsItems is the list of newsitems, which is a data source of an Adapter
     */
    public NewsAdapter(Activity context, ArrayList<NewsItems> newsItems) {
        super(context, 0, newsItems);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        //check if there is an existing view, otherwise create a new view
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //Get the {@link NewsItems} object located at this position
        NewsItems currentNewsItem = getItem(position);

        //Find the TextView in the list_item.xml layout with the ID news_website
        TextView websiteNameTextView = (TextView) listItemView.findViewById(R.id.news_website);
        // Get the website name from the current NewsItems object and
        // set this text on the website TextView
        websiteNameTextView.setText(currentNewsItem.getmWebsiteName());

        //Find the TextView in the list_item.xml layout with the ID article_title
        TextView titleNameTextView = (TextView) listItemView.findViewById(R.id.article_title);
        // Get the title of an article from the current NewsItems object and
        // set this text on the title TextView
        titleNameTextView.setText(currentNewsItem.getmArticleTitle());





        //Find the TextView in the list_item.xml layout with the ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        //adding word "on" before the published date
        mPublishedDate = "On: " + currentNewsItem.getPublishedDate();
        // set the date value in dateToDisplay on the date TextView
        dateTextView.setText(mPublishedDate);

        return listItemView;
    }
}
