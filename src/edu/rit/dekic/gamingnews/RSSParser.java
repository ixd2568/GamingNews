package edu.rit.dekic.gamingnews;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author Igor
 * Parses RSS feeds located at the provided URL
 */
public class RSSParser 
{
	private URL url;
	
	//convert the string into an actual URL
	public RSSParser(String url)
	{
		try 
		{
			this.url = new URL(url);
		}
		catch (MalformedURLException mue) 
		{
			this.url = null;
		}
	}

	public ArrayList<RSSItem> parse()
	{
		ArrayList<RSSItem> listOfItems = new ArrayList<RSSItem>();

		try 
		{
			//create parser, point it to the url provided
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(getInputStream(url), "UTF_8");

			//instantiate a new data object
			RSSItem item = new RSSItem("", "", "");

			int eventType=parser.getEventType();

			//while not EOF
			while(eventType!=XmlPullParser.END_DOCUMENT)
			{
				//find open tag
				if(eventType==XmlPullParser.START_TAG)
				{
					//if it's an item tag
					if (parser.getName().equalsIgnoreCase("item"))
					{
						//if it wasn't the first item, add the previous item
						if ((item.getTitle()!="") && (item.getDescription()!="") && (item.getLink()!=""))
							listOfItems.add(item);
						//then reset item
						item = new RSSItem("", "", "");
					}
					
					//when relevant properties are encountered, set the item's properties to those values
					if(parser.getName().equalsIgnoreCase("title"))
						item.setTitle(parser.nextText());
					else if (parser.getName().equalsIgnoreCase("description"))
						item.setDescription(parser.nextText());
					else if(parser.getName().equalsIgnoreCase("link"))
						item.setLink(parser.nextText());
				}
				//keep parsing
				eventType=parser.next();
			}
		}
		catch (XmlPullParserException xppe) 
		{
			// TODO Auto-generated catch block
			xppe.printStackTrace();
		}
		catch (IOException ioe) 
		{
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}

		return listOfItems;
	}
	
	//gets input stream
	public InputStream getInputStream(URL url) 
	{
		try 
		{
			return url.openConnection().getInputStream();
		}
		catch (IOException ioe) 
		{
			return null;
		}
	}
}
