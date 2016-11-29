package com.credera;


import java.io.IOException;

import com.elasticsearch.Elasticsearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.podsurferAPI.PodsurferAPI;

@Controller
public class UserController {
	@Autowired
	private Elasticsearch es;

	@Autowired
	private PodsurferAPI api;
	
	@ResponseBody @RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public Response signUpUser(@RequestBody User newUser){
		String emptyPreferencesObject = "{}";
		es.updateUserPreferences(newUser.getEmail(), emptyPreferencesObject);
		return api.signUpUser(newUser);
	}

	@ResponseBody @RequestMapping(value="/login", method=RequestMethod.POST)
	public Response loginUser(@RequestBody User user) {
		return api.loginUser(user);
	}

  	@ResponseBody @RequestMapping(value="/user", method=RequestMethod.GET)
	public Response getUserInfo(@RequestHeader("Authorization") String token) {
		return api.getUserInfo(token);
  	}

	@ResponseBody @RequestMapping(value="/user/preferences", method=RequestMethod.GET)
	public String getUserPreferences(@RequestHeader("Authorization") String token) {
		String email = api.getUserEmail(token);

		if(email != null) {
			return es.getUserPreferences(email);
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
		String email = api.getUserEmail(token);

		if(email == null) {
			response.setMessage("user not found");
		} else {
			response.setSuccess(true);
			response.setMessage(es.updateUserPreferences(email, userPreferences));
		}

		return response;
	}

}
