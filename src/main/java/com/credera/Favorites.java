package com.credera;

import java.util.ArrayList;

// NEW
public class Favorites {
	private String[] favorites;
	
	public Favorites()
	{
		
	}
	
	public Favorites(ArrayList<String> fav)
	{
		favorites = new String[fav.size()];
		for(int i = 0; i < fav.size(); i++)
		{
			favorites[i] = fav.get(i);
		}
	}
	
	public String[] getFavorites()
	{
		return favorites;
	}
	
	public void setFavorites(String[] fav)
	{
		favorites = fav;
	}

}
