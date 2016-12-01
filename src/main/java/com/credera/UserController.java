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

		if(email != null) {
			return "{\n" +
					"	\"success\": true,\n" +
					"	\"preferences\": " + es.getUserPreferences(email) +
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
			response.setSuccess(true);
			response.setMessage(es.updateUserPreferences(email, userPreferences));
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
