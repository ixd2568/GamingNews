package edu.rit.dekic.gamingnews;

/**
 * @author Igor
 * Basic data object
 * Contains RSS item's title (header), url (currently unused), and description (actual news)
 */
public class RSSItem 
{
	private String title, link, description;
	
	public RSSItem(String title, String link, String description)
	{
		this.title = title;
		this.link = link;
		this.description = description;
	}
	
	public RSSItem()
	{
		this.title = "";
		this.link = "";
		this.description = "";
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getLink()
	{
		return this.link;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
