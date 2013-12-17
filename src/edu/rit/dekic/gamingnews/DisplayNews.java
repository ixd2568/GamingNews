package edu.rit.dekic.gamingnews;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Igor
 * Populates a ListView with RSSItems from the provided URL
 */
public class DisplayNews extends ListActivity 
{
	private String url;
	private ArrayList<RSSItem> feed;
	DatabaseHandler database;

	public DisplayNews()
	{
		this.url = "";
		this.feed = new ArrayList<RSSItem>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);

		//get arguments
		Bundle bundle = getIntent().getExtras();
		this.url = bundle.getString("url");

		//parse the RSS feed for the provided url
		feed = new ArrayList<RSSItem>();
		RSSAsyncTask thread = new RSSAsyncTask();
		try 
		{
			feed = thread.execute(url).get();
		}
		catch (InterruptedException ie) 
		{
			ie.printStackTrace();
		}
		catch (ExecutionException ee) 
		{
			ee.printStackTrace();
		}

		ArrayList<String> listOfHeaders = new ArrayList<String>();

		for(RSSItem item : feed)
		{
			listOfHeaders.add(item.getTitle());
		}

		//create ListView, populate it using the adapter
		ListView lv = (ListView) findViewById(android.R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHeaders);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);

		database = new DatabaseHandler(this);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		//go through all the news items in the feed
		for(RSSItem item : feed)
		{
			//find the one that was clicked on
			if (item.getTitle() == ((TextView) v).getText())
			{
				//display it in a new activity
				Intent i = new Intent(getApplicationContext(), DisplayNewsItem.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", item.getTitle());
				bundle.putString("link", item.getLink());
				bundle.putString("description", item.getDescription());
				i.putExtras(bundle); 
				startActivity(i);
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) 
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		//title of selected item
		String title = ((TextView)info.targetView).getText().toString();
		String link = "";
		String description = "";

		for (RSSItem item : feed)
		{
			if (item.getTitle() == title)
			{
				link = item.getLink();
				description = item.getDescription();
			}
		}

		if (title != "" && link != "" && description != "")
			//add to favorites
			database.addItem(new RSSItem(title, link, description));
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
		menu.add(R.string.fav).setIcon(android.R.drawable. btn_star_big_on).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent i = new Intent(getApplicationContext(), DisplayFavorites.class);
        startActivity(i);
        return true;
	}
}
