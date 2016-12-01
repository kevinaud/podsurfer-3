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

import com.restfb.FacebookClient;
import com.podsurferAPI.PodsurferAPI;
import com.facebookAPI.FacebookAPI;

@Controller
public class UserController {
	@Autowired
	private Elasticsearch es;
	@Autowired
	private PodsurferAPI papi;
	@Autowired
	private FacebookAPI fapi;

	@ResponseBody @RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public Response signUpUser(@RequestBody User newUser){
		String emptyPreferencesObject = "{}";
		es.updateUserPreferences(newUser.getEmail(), emptyPreferencesObject);
		return papi.signUpUser(newUser);
	}
	
	// NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite", method=RequestMethod.GET)
	public String getFavorites(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server){
		String response;

    if(server.equals("podsurfer")) {
			Response authResponse = getUserInfo(token, server);
			if (authResponse.getSuccess()) {
				String userInfoAsJsonString = getUserInfo(token, server).getMessage();
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
			} else {
				response = "Authentication failed.";
			}
		}
		else if(server.equals("facebook")){
			FacebookClient fb = fapi.getAuthorizedClient(token);
			if(fb != null){
				response = es.getFavorites(fapi.getUserInfo(fb).getId());
			}
			else{
				response = "Authentication failed";
			}
		}
		else{
			response = "invalid server";
		}
		return response;
	}
	

	// NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite/{podcastId}", method=RequestMethod.POST)
	public String addFavorite(@PathVariable String podcastId, @RequestHeader("Authorization") String token,
														@RequestHeader("Server") String server){
		String response;
    ArrayList<String> favorites;
		if(server.equals("podsurfer")) {

			Response authResponse = getUserInfo(token, server);
			if (authResponse.getSuccess()) {
				String userInfoAsJsonString = getUserInfo(token, server).getMessage();
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
				favorites = new ArrayList();
				if (!favoritesAsJsonString.equals("{}")) {
					JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
					JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
					for (int i = 0; i < favoritesJsonArray.length(); i++) {
						favorites.add(favoritesJsonArray.getString(i));
						System.out.println(favorites.get(i));
					}
				}

				if (!favorites.contains(podcastId)) {
					favorites.add(podcastId);

					es.editFavorites(encodedEmail, new Favorites(favorites));
					response = es.getFavorites(encodedEmail);
				} else {
					response = "Favorites already contains " + podcastId + ".";
				}
			} else {
				response = "Adding favorite failed.";
			}
		}
		else if(server.equals("facebook")){
			FacebookClient fb = fapi.getAuthorizedClient(token);
			if(fb != null){
				String id = fapi.getUserInfo(fb).getId();

				String favoritesAsJsonString = es.getFavorites(id);
				favorites = new ArrayList();
				if (!favoritesAsJsonString.equals("{}")) {
					JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
					JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
					for (int i = 0; i < favoritesJsonArray.length(); i++) {
						favorites.add(favoritesJsonArray.getString(i));
						System.out.println(favorites.get(i));
					}
				}

				if (!favorites.contains(podcastId)) {
					favorites.add(podcastId);
					es.editFavorites(id, new Favorites(favorites));
					response = es.getFavorites(id);
				}
				else{
					response = "Favorites already contains " + podcastId + ".";
				}
			}
			else{
				response = "Adding favorite failed.";
			}
		}
		else{
			response = "invalid server";
		}
		return response;
	}
	
	//NEW
	@ResponseBody
	@RequestMapping(value="/user/favorite/{podcastId}", method=RequestMethod.DELETE)
	public String removeFavorite(@PathVariable String podcastId, @RequestHeader("Authorization") String token,
															 @RequestHeader("Server") String server){
		String response;
		if(server.equals("podsurfer")) {
			Response authResponse = getUserInfo(token, server);
			if (authResponse.getSuccess()) {
				String userInfoAsJsonString = getUserInfo(token, server).getMessage();
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
				if (!favoritesAsJsonString.equals("{}")) {
					JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
					JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
					for (int i = 0; i < favoritesJsonArray.length(); i++) {
						favorites.add(favoritesJsonArray.getString(i));
						System.out.println(favorites.get(i));
					}
				}


				if (favorites.contains(podcastId)) {
					favorites.remove(podcastId);
					es.editFavorites(encodedEmail, new Favorites(favorites));
					response = es.getFavorites(encodedEmail);
				} else {
					response = "Favorites does not contain " + podcastId + ".";
				}
			} else {
				response = "Deleting favorite failed.";
			}
		}
		else if(server.equals("facebook")){
			FacebookClient fb = fapi.getAuthorizedClient(token);
			if(fb != null){
				String id = fapi.getUserInfo(fb).getId();
				String favoritesAsJsonString = es.getFavorites(id);
				ArrayList<String> favorites = new ArrayList();
				if (!favoritesAsJsonString.equals("{}")) {
					JSONObject favoritesAsJson = new JSONObject(favoritesAsJsonString);
					JSONArray favoritesJsonArray = favoritesAsJson.getJSONArray("favorites");
					for (int i = 0; i < favoritesJsonArray.length(); i++) {
						favorites.add(favoritesJsonArray.getString(i));
						System.out.println(favorites.get(i));
					}
				}

				if (favorites.contains(podcastId)) {
					favorites.remove(podcastId);
					es.editFavorites(id, new Favorites(favorites));
					response = es.getFavorites(id);
				} else {
					response = "Favorites does not contain " + podcastId + ".";
				}
			} else {
				response = "Deleting favorite failed.";
			}
		}
		else{
			response = "invalid server";
		}
		return response;
	}

	@ResponseBody @RequestMapping(value="/login", method=RequestMethod.POST)
	public Response loginUser(@RequestBody User user) {
		return papi.loginUser(user);
	}

	@ResponseBody @RequestMapping(value="/user/preferences", method=RequestMethod.GET)
	public String getUserPreferences(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server) {
		if(server.equals("podsurfer")) {
			String email = papi.getUserEmail(token);
			System.out.println("test");

			if (email != null) {

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
		else if(server.equals("facebook")){
      FacebookClient fb = fapi.getAuthorizedClient(token);
			if(fb != null) {
				return "{\n" +
					"	\"success\": true,\n" +
					"	\"preferences\": " + es.getUserPreferences(fapi.getUserInfo(fb).getId()) +
					"}";
			}
			else {
				return "{\n" +
					"	\"success\": false,\n" +
					"	\"message\": \"user not found\"\n" +
					"}";
			}
		}
		else
			return "invalid server";
	}

	@ResponseBody @RequestMapping(value="/user/preferences", method=RequestMethod.POST)
	public Response updateUserPreferences(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server,
																				@RequestBody String userPreferences) {

		Response response = new Response();
    if(server.equals("podsurfer")) {
			String email = papi.getUserEmail(token);

			if (email == null) {
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
		}
		else if(server.equals("facebook")){
			FacebookClient fb = fapi.getAuthorizedClient(token);
			if(fb != null) {
				String id = fapi.getUserInfo(fb).getId();
				response.setSuccess(true);
				response.setMessage(es.updateUserPreferences(id, userPreferences));
			}
		}
		else{
			response.setSuccess(false);
			response.setMessage("invalid server");
		}
		return response;
	}

    //Generic login endpoint
    @ResponseBody @RequestMapping(value="/oauth-user", method=RequestMethod.POST)
    public Response getUserInfo(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server){

      if(server.equals("podsurfer")){
        return papi.getUserInfo(token);
      }
      else if(server.equals("facebook")) {
        Response r = new Response();
        FacebookClient fb = fapi.getAuthorizedClient(token);
				r.setSuccess(fb != null);
				if(fb == null)
					r.setMessage("token invalid");
				else
					r.setMessage(fapi.getUserInfoJson(fb));

				return r;
      }
      else{
        Response r = new Response();
        r.setSuccess(false);
        r.setMessage(server + " is invalid");
        return r;
      }
  }
}
