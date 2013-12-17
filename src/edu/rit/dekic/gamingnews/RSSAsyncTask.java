package edu.rit.dekic.gamingnews;

import java.util.ArrayList;

import android.os.AsyncTask;

/**
 * @author Igor
 * AsyncTask tasked with parsing individual RSS feeds
 */
public class RSSAsyncTask extends AsyncTask<String, Void, ArrayList<RSSItem>> 
{
	protected ArrayList<RSSItem> doInBackground(String... url) 
	{
		//create parser and return the parsed arraylist
		RSSParser parser = new RSSParser(url[0]);
	    return parser.parse();
	}
}
