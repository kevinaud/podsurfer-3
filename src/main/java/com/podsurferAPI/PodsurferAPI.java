package com.podsurferAPI;

import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.credera.*;

@Service
public class PodsurferAPI {
	
	private String apiUrl = "https://podsurfer-3.herokuapp.com/api";
	
	public PodsurferAPI(){
		
	}

	public Response loginUser(User user) { return apiPostRequest("/login", user); }
	public Response signUpUser(User newUser) {
		return apiPostRequest("/user", newUser);
	}
	
	public Response apiPostRequest(String endpoint, Object payload) {
		
		Response response = new Response();
		String signUpEndpoint = apiUrl + endpoint;
		
		try {
			
			HttpResponse<JsonNode> jsonResponse = Unirest.post(signUpEndpoint)
				.header("accept", "application/json")
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
}

