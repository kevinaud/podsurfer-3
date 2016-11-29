package com.facebookAPI;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.connect.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.connect.*;

import com.credera.Response;
import com.credera.User;

@Service
public class FacebookAPI extends OAuthAPI {

  private FacebookAdapter fbAdapter;

  public FacebookAPI(){
    fbAdapter = new FacebookAdapter();
  }

  public Facebook getAuthorizedClient(String token){
    Facebook fb = new FacebookTemplate(token);

    if(fb.isAuthorized())
        return fb;
    else
        return null;
  }

  public User getUserInfo(Facebook fb){
    if(!fb.isAuthorized())
      return null;

    UserProfile profile = fbAdapter.fetchUserProfile(fb);
    User user = new User();

    user.setAuthServ("facebook");
    user.setName(profile.getName());
    user.setEmail(profile.getEmail());

    return user;
  }
}
