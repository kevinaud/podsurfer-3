package com.FacebookAPI;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;

import com.credera.Response;

@Controller
public class FacebookAPI {

  private String apiUrl = "fb-dev-url";
  private Facebook fb;
  private ConnectionRepository connect;

  public FacebookAPI(){

  }

}
