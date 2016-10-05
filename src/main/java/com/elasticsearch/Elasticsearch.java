package com.elasticsearch;

import com.credera.Response;
import com.credera.Podcast;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

/**
 * Created by kevinaud on 10/4/16.
 */
@Service
public class Elasticsearch {

    private String elasticsearchUrl = "https://iysmoiwf:5bbh9mfewqtnqei3@myrtle-1552752.us-east-1.bonsai.io";

    public Elasticsearch(){

    }

    public Response addPodcast(Podcast podcast) {

        return elasticsearchPostRequest("/podsurfer/podcast", podcast);

    }

    public Response getHealth() {

        return elasticsearchGetRequest("/_cluster/health?pretty=true");

    }

    public Response elasticsearchGetRequest(String endpoint) {

        Response response = new Response();
        String requestUrl = elasticsearchUrl + endpoint;

        try {

            HttpResponse<JsonNode> jsonResponse = Unirest.get(requestUrl)
                    .asJson();

            response.setSuccess(true);
            response.setMessage(jsonResponse.getBody().toString());
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    };

    public Response elasticsearchPostRequest(String endpoint, Object payload) {

        Response response = new Response();
        String requestUrl = elasticsearchUrl + endpoint;

        try {

            HttpResponse<JsonNode> jsonResponse = Unirest.post(requestUrl)
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


