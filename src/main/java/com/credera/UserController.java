package com.credera;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.elasticsearch.Elasticsearch;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.social.facebook.api.*;

import com.podsurferAPI.PodsurferAPI;
import com.facebookAPI.FacebookAPI;
import com.googleAPI.GoogleAPI;

@Controller
public class UserController {
	@Autowired
	private Elasticsearch es;
	@Autowired
	private PodsurferAPI papi;
	@Autowired
	private FacebookAPI fapi;
	@Autowired
	private GoogleAPI gapi;

	@ResponseBody @RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public Response signUpUser(@RequestBody User newUser){
		String emptyPreferencesObject = "{}";
		es.updateUserPreferences(newUser.getEmail(), emptyPreferencesObject);
		return papi.signUpUser(newUser);
	}
	
	// NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite", method=RequestMethod.GET)
	public String getFavorites(@RequestHeader("Authorization") String token){
		Response authResponse = getUserInfo(token);
		String response = "";
		if(authResponse.getSuccess())
		{
			String userInfoAsJsonString = getUserInfo(token).getMessage();
			JSONObject userInfoAsJson = new JSONObject(userInfoAsJsonString);
			String userEmail = userInfoAsJson.getString("email");
			String encodedEmail = "";
			try {
				encodedEmail = encodedEmail = URLEncoder.encode(userEmail, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = es.getFavorites(encodedEmail);
		}
		else
		{
			response = "Authentication failed.";
		}
		return response;	
	}
	

	// NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite/{podcastId}", method=RequestMethod.POST)
	public String addFavorite(@PathVariable String podcastId, @RequestHeader("Authorization") String token){
		String response = new String();
		Response authResponse = getUserInfo(token);
		if(authResponse.getSuccess())
		{
			String userInfoAsJsonString = getUserInfo(token).getMessage();
			JSONObject userInfoAsJson = new JSONObject(userInfoAsJsonString);
			String userEmail = userInfoAsJson.getString("email");
			String encodedEmail = "";
			try {
				encodedEmail = encodedEmail = URLEncoder.encode(userEmail, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String favoritesAsJsonString = es.getFavorites(encodedEmail);
			ArrayList<String> favorites = new ArrayList();
			if(!favoritesAsJsonString.equals("{}"))
			{
				JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
				JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
				for(int i = 0; i < favoritesJsonArray.length(); i++)
				{
					favorites.add(favoritesJsonArray.getString(i));
					System.out.println(favorites.get(i));
				}
			}
			
			if(!favorites.contains(podcastId))
			{
				favorites.add(podcastId);
				
				es.editFavorites(encodedEmail, new Favorites(favorites));
				response = es.getFavorites(encodedEmail);
			}
			else
			{
				response = "{ \"success\": false, \"message\": \"Favorites already contains " + podcastId + ".\" }";
			}
		}
		else
		{
			response = "{ \"success\": false, \"message\": \"Adding favorites failed\" }";
		}
		return response;
	}
	
	//NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite/{podcastId}", method=RequestMethod.DELETE)
	public String removeFavorite(@PathVariable String podcastId, @RequestHeader("Authorization") String token){
		String response = new String();
		Response authResponse = getUserInfo(token);
		if(authResponse.getSuccess())
		{
			String userInfoAsJsonString = getUserInfo(token).getMessage();
			JSONObject userInfoAsJson = new JSONObject(userInfoAsJsonString);
			String userEmail = userInfoAsJson.getString("email");
			String encodedEmail = "";
			try {
				encodedEmail = encodedEmail = URLEncoder.encode(userEmail, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String favoritesAsJsonString = es.getFavorites(encodedEmail);
			ArrayList<String> favorites = new ArrayList();
			if(!favoritesAsJsonString.equals("{}"))
			{
				JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
				JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
				for(int i = 0; i < favoritesJsonArray.length(); i++)
				{
					favorites.add(favoritesJsonArray.getString(i));
					System.out.println(favorites.get(i));
				}
			}
			
			
			if(favorites.contains(podcastId))
			{
				favorites.remove(podcastId);
				es.editFavorites(encodedEmail, new Favorites(favorites));
				response = es.getFavorites(encodedEmail);
			}
			else
			{
				response = "{ \"message\": \"Favorites does not contain " + podcastId + ".\"}";
			}
		}
		else
		{
			response = "{ \"message\": \"Deleting favorite failed.\" }";
		}
		return response;
	}

	@ResponseBody @RequestMapping(value="/login", method=RequestMethod.POST)
	public Response loginUser(@RequestBody User user) {
		return papi.loginUser(user);
	}

	@ResponseBody @RequestMapping(value="/user", method=RequestMethod.GET)
	public Response getUserInfo(@RequestHeader("Authorization") String token) {
		return papi.getUserInfo(token);
	}

	@ResponseBody @RequestMapping(value="/user/preferences", method=RequestMethod.GET)
	public String getUserPreferences(@RequestHeader("Authorization") String token) {
		String email = papi.getUserEmail(token);
		System.out.println("test");

		if(email != null) {

			String encodedEmail = "";
			try {
				encodedEmail = URLEncoder.encode(email, "UTF-8");
				System.out.println(encodedEmail);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return "{\n" +
					"	\"success\": true,\n" +
					"	\"preferences\": " + es.getUserPreferences(encodedEmail) +
					"}";
		} else {
			return "{\n" +
					"	\"success\": false,\n" +
					"	\"message\": \"user not found\"\n" +
					"}";
		}

	}

	@ResponseBody @RequestMapping(value="/user/preferences", method=RequestMethod.POST)
	public Response updateUserPreferences(@RequestHeader("Authorization") String token, @RequestBody String userPreferences) {

		Response response = new Response();
		String email = papi.getUserEmail(token);

		if(email == null) {
			response.setMessage("user not found");
		} else {

			String encodedEmail = "";
			try {
				encodedEmail = URLEncoder.encode(email, "UTF-8");
				System.out.println(encodedEmail);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			response.setSuccess(true);
			response.setMessage(es.updateUserPreferences(encodedEmail, userPreferences));
		}

		return response;
	}

    //Generic login endpoint
    @ResponseBody @RequestMapping(value="/oauth-user", method=RequestMethod.POST)
    public Response getUserInfo(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server){
        if(server == "podsurfer"){
          return papi.getUserInfo(token);
        }
        else if(server == "facebook") {
          Response r = new Response();
          Facebook fb = fapi.getAuthorizedClient(token);
          r.setSuccess(fb.isAuthorized());
          r.setMessage(fapi.getUserInfo(fb).toJSON());
          return r;
        }
        else{
          return null;
        }
  	}
}
