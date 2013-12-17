package edu.rit.dekic.gamingnews;

import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Igor
 * Provides an interface for the user to enter a custom feed URL
 */
public class EnterUrl extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_feed);

		Button okButton = (Button) findViewById(R.id.feedUrlOK);
		
		okButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				EditText feedText = (EditText) findViewById(R.id.feedUrlView);
				String url = feedText.getText().toString();
				try 
				{
					//throws an exception if the string is invalid
					(new URL(url)).toURI();
					
					//if it's valid, start the new activity
					Intent i = new Intent(getApplicationContext(), DisplayNews.class);
			        Bundle bundle = new Bundle();
			        bundle.putString("url", url);
			        i.putExtras(bundle); 
			        startActivity(i);
				} 
				catch (Exception e) 
				{
					Toast.makeText(getApplicationContext(), "Please enter a valid URL.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
