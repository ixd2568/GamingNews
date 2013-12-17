package edu.rit.dekic.gamingnews;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Igor
 * Creates and maintains a database of RSSItems
 */
public class DatabaseHandler extends SQLiteOpenHelper 
{
	public DatabaseHandler(Context context) 
	{
        super(context, "favoritesDB", null, 1);
    }

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		//create database
		database.execSQL("CREATE TABLE favorites (id INTEGER PRIMARY KEY, title TEXT, link TEXT, description TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) 
	{
		//drop table if it already exists, recreate it
		database.execSQL("DROP TABLE IF EXISTS favorites");
        this.onCreate(database);
	}
	
	void addItem(RSSItem item) 
	{
        SQLiteDatabase database = this.getWritableDatabase();
        
        //set attributes
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.getTitle());
        contentValues.put("link", item.getLink());
        contentValues.put("description", item.getDescription());
 
        //insert item, close connection
        database.insert("favorites", null, contentValues);
        database.close();
    }
	
	public ArrayList<RSSItem> getFavorites() 
	{
        ArrayList<RSSItem> favorites = new ArrayList<RSSItem>();
 
        SQLiteDatabase database = this.getWritableDatabase();
        //get cursor
        Cursor cursor = database.rawQuery("SELECT * FROM favorites", null);
        
        //loop through the table, populate arraylist
        if (cursor.moveToFirst()) 
        {
            do 
            {
                favorites.add(new RSSItem(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } 
            while (cursor.moveToNext());
        }
        return favorites;
    }
	
	public void deleteItem (RSSItem item) 
	{
		//drop all items that have the same title as the provided item
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("favorites", "title = ?", new String[] { String.valueOf(item.getTitle()) });
        database.close();
    }
 
}
