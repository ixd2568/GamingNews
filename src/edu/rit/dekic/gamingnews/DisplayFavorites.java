package edu.rit.dekic.gamingnews;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * @author Igor
 * @see DisplayNews
 * Like DisplayNews, populates ListView with RSSItems, except in this case, they come from the favorites list.
 */
public class DisplayFavorites extends ListActivity
{
	ArrayList<RSSItem> favorites;
	DatabaseHandler database;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);

		database = new DatabaseHandler(this);
		favorites = database.getFavorites(); 
		
		ArrayList<String> listOfHeaders = new ArrayList<String>();

		for(RSSItem item : favorites)
		{
			listOfHeaders.add(item.getTitle());
		}

		//create ListView, populate it using the adapter
		ListView lv = (ListView) findViewById(android.R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHeaders);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		//go through all the news items in the feed
		for(RSSItem item : favorites)
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

		for (RSSItem item : favorites)
		{
			if (item.getTitle() == title)
			{
				link = item.getLink();
				description = item.getDescription();
			}
		}

		if (title != "" && link != "" && description != "")
		{
			//remove from favorites, refresh
			database.deleteItem(new RSSItem(title, link, description));
			favorites = database.getFavorites(); 
			ArrayList<String> listOfHeaders = new ArrayList<String>();

			for(RSSItem item : favorites)
			{
				listOfHeaders.add(item.getTitle());
			}

			//create ListView, populate it using the adapter
			ListView lv = (ListView) findViewById(android.R.id.list);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHeaders);
			lv.setAdapter(adapter);
			registerForContextMenu(lv);
		}

	}
}
