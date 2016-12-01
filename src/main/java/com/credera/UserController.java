package com.credera;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.elasticsearch.Elasticsearch;
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

	@ResponseBody
	@RequestMapping(value="/user/favorite/{podcastId}", method=RequestMethod.GET)
	public String addFavorite(@PathVariable String podcastId, @RequestHeader("Authorization") String token){
		String response = new String();
		Response authResponse = getUserInfo(token);
		if(authResponse.getSuccess())
		{
			String userInfoAsJsonString = getUserInfo(token).getMessage();
			JSONObject userInfoAsJson = new JSONObject(userInfoAsJsonString);
			String userEmail = userInfoAsJson.getString("email");
			System.out.println(userEmail);
			response = userEmail;
		}
		else
		{
			response = "Adding favorite failed.";
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
