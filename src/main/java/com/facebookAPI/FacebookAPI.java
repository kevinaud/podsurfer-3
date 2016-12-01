package com.facebookAPI;

import com.restfb.exception.FacebookOAuthException;
import org.springframework.stereotype.Service;

import com.restfb.*;
import com.restfb.json.JsonObject;

@Service
public class FacebookAPI{

  private String[] fields = { "email","name" };

  public FacebookAPI(){

  }

  public FacebookClient getAuthorizedClient(String token){
    try {
      FacebookClient fb = new DefaultFacebookClient(token, Version.VERSION_2_8);
      return fb;
    }catch(FacebookOAuthException e){
      e.printStackTrace();
      return null;
    }
  }

  public com.credera.User getUserInfo(FacebookClient fb){

    com.restfb.types.User fbuser = fb.fetchObject("me", com.restfb.types.User.class);
    com.credera.User user = new com.credera.User();
    user.setName(fbuser.getName());
    user.setId(fbuser.getId());
    return user;
  }

  public String getUserInfoJson(FacebookClient fb){
    com.restfb.types.User fbuser = fb.fetchObject("me", com.restfb.types.User.class);
    JsonObject json = new JsonObject();
    json.put("name",fbuser.getName());
    json.put("email",fbuser.getId());
    System.out.println(json.toString());
    return json.toString();
  }

}
