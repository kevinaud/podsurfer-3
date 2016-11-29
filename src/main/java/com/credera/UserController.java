package com.credera;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.podsurferAPI.PodsurferAPI;
import com.facebookAPI.FacebookAPI;
import com.googleAPI.GoogleAPI;

@Controller
public class UserController {
	@Autowired
	private PodsurferAPI podApi;
  /*
  @Autowired
  private FacebookAPI fbApi;
  @Autowired
  private GoogleAPI gApi;
  */

  //Podsurfer login endpoints
	@ResponseBody @RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public Response signUpUser(@RequestBody User newUser){
		return podApi.signUpUser(newUser);
	}

	@ResponseBody @RequestMapping(value="/login", method=RequestMethod.POST)
	public Response loginUser(@RequestBody User user) {
    return podApi.loginUser(user);
	}

  @ResponseBody @RequestMapping(value="/user", method=RequestMethod.GET)
	public Response getUserInfo(@RequestHeader("Authorization") String token) {
    return podApi.getUserInfo(token);
  }

  //Facebook login endpoints
  @ResponseBody @RequestMapping(value="/oauth-login", method=RequestMethod.POST)
  public Response getUserInfo(@RequestHeader("Authorization") String token, @RequestHeader("Server") String server){
    if(server == "podsurfer")
      return podApi.getUserInfo(token);
    /*
    else if(server == "facebook") {
      Response r = new Response();
      r.setSuccess(fbApi.authorize(token));
      r.setMessage(r.getSuccess() ? "success" : "failed");
      return r;
    }
    else if(server == "google"){
      Response r = new Response();
      r.setSuccess(gApi.authorize(token));
      r.setMessage(r.getSuccess() ? "success" : "failed");
      return r;

    }
    */
    else
      return null;
  }

}
