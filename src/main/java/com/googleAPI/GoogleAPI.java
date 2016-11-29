package com.googleAPI;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.social.connect.*;

import com.credera.Response;
import com.credera.User;

@Service
public class GoogleAPI extends OAuthAPI {

  private String apiUrl = "fb-dev-url";
  private User user;

  public GoogleAPI(){
  }

  public boolean authorize(String token){
    return false;
  }

  public boolean isAuthorized() {
    return false;
  }

  public User getUserInfo(){
    return null;
  }
}
