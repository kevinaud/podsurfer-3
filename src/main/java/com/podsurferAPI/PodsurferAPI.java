package com.podsurferAPI;

import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.credera.*;

@Service
public class PodsurferAPI {
	
	private String apiUrl = "https://podsurfer-3.herokuapp.com";
	
	public PodsurferAPI(){
		
	}

	public Response loginUser(User user) {
		return apiPostRequest("/auth/local", user);
	}

	public Response signUpUser(User newUser) {
		return apiPostRequest("/api/user", newUser);
	}

	public Response getUserInfo(String token) {
        return apiGetRequest("/api/user/me", "Authorization", token);
    }

	public Response apiPostRequest(String endpoint, Object payload) {
		
		Response response = new Response();
		String url = apiUrl + endpoint;
		
		try {
			
			HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
				.header("Content-Type", "application/json")
				.body(payload)
				.asJson();

			response.setSuccess(true);
			response.setMessage(jsonResponse.getBody().toString());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	};

  public Response apiGetRequest(String endpoint, String headerKey, String headerValue) {

    Response response = new Response();
    String url = apiUrl + endpoint;

    try {

      HttpResponse<JsonNode> tokenResponse = Unirest.get(url)
        .header(headerKey, headerValue)
        .asJson();

      response.setSuccess(true);
      response.setMessage(tokenResponse.getBody().toString());
    } catch (UnirestException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return response;
  }
}

