package edu.rit.dekic.gamingnews;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Igor
 * Main activity
 * Allows the user to choose a feed or input their own
 */
public class MainActivity extends Activity 
{
	//preset feed URLs
	public static String JOYSTIQ = "http://www.joystiq.com/rss.xml";
	public static String EUROGAMER = "http://www.eurogamer.net/?format=rss&type=news";
	public static String RPS = "http://feeds.feedburner.com/RockPaperShotgun?format=xml";
	public static String KOTAKU = "http://kotaku.com/vip.xml";
	
	private Button eurogamerButton, joystiqButton, kotakuButton, rpsButton, otherButton;
	private Context context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        eurogamerButton = (Button) findViewById(R.id.eurogamerButton);
        joystiqButton = (Button) findViewById(R.id.joystiqButton);
        kotakuButton = (Button) findViewById(R.id.kotakuButton);
        rpsButton = (Button) findViewById(R.id.rpsButton);
        otherButton = (Button) findViewById(R.id.otherButton);
        
        context = this;
        
        //set click listeners for the buttons
        //on click, instantiate new DisplayNews activity with the appropriate URL as a parameter
        eurogamerButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(context, DisplayNews.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("url", EUROGAMER);
		        i.putExtras(bundle); 
		        startActivity(i);
			}
		});
        
        joystiqButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(context, DisplayNews.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("url", JOYSTIQ);
		        i.putExtras(bundle); 
		        startActivity(i);
			}
		});
        
        kotakuButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(context, DisplayNews.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("url", KOTAKU);
		        i.putExtras(bundle); 
		        startActivity(i);
			}
		});
        
        rpsButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(context, DisplayNews.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("url", RPS);
		        i.putExtras(bundle); 
		        startActivity(i);
			}
		});
        
        otherButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(context, EnterUrl.class);
		        startActivity(i);
			}
		});
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
		//options menu has only one item - it opens up the favorites
		Intent i = new Intent(context, DisplayFavorites.class);
        startActivity(i);
        return true;
	}
    
}
