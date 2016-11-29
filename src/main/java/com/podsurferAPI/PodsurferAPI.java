package com.podsurferAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.credera.*;

import java.io.IOException;

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

	public String getUserEmail(String token) {
		com.fasterxml.jackson.databind.JsonNode response = apiGetRequestJson("/api/user/me", "Authorization", token);

		if (response != null && response.has("email")) {
			return response.get("email").asText();
		} else {
			return null;
		}

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

	public com.fasterxml.jackson.databind.JsonNode apiGetRequestJson(String endpoint, String headerKey, String headerValue) {

		String url = apiUrl + endpoint;
		com.fasterxml.jackson.databind.JsonNode responseObj;

		try {

			HttpResponse<JsonNode> tokenResponse = Unirest.get(url)
					.header(headerKey, headerValue)
					.asJson();

			ObjectMapper mapper = new ObjectMapper();

			try {

				responseObj = mapper.readTree(tokenResponse.getBody().toString());

			} catch(IOException error) {
				System.out.println(error.getMessage());
				System.out.println(error.getStackTrace());
				return null;
			}

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return responseObj;

	}
}

