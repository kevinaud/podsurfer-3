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
public class FacebookAPI {

  private Facebook fb;
  private FacebookAdapter fbAdapter;
  private Connection<Facebook> connection;
  private ConnectionRepository connectionRepo;

  public FacebookAPI(){
    fbAdapter = new FacebookAdapter();
  }

  public boolean authorize(String token){
    fb = new FacebookTemplate(token);
    return fb.isAuthorized();
  }

  public boolean isAuthorized() {
    return fb.isAuthorized();
  }

  public User getUserInfo(){
    if(!fb.isAuthorized())
      return null;

    UserProfile u = fbAdapter.fetchUserProfile(fb);
    User user = new User();

    user.setAuthServ("facebook");
    user.setName(u.getName());
    user.setEmail(u.getEmail());

    return user;
  }

}
