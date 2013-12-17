package edu.rit.dekic.gamingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;


/**
 * @author Igor
 * Displays a single news item from the parsed RSS feed
 */
public class DisplayNewsItem extends Activity 
{
	WebView htmlView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_item);
		
		//get arguments
		Bundle bundle = getIntent().getExtras();
		
		htmlView = (WebView) findViewById(R.id.htmlView);
		//disable horizontal scrolling
		htmlView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		htmlView.setPadding(0, 0, 0, 0);
		//format html, add header, append to view
		String html = "<h2 style='text-align: center; padding: 5px'>" + bundle.getString("title") + "</h1>" + bundle.getString("description").replace("<p", "<p style='padding: 5px'");
        htmlView.loadDataWithBaseURL (null, html, "text/html", "UTF-8", null);
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
