package com.elasticsearch;

import com.credera.*;
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

    private String elasticsearchUrl = "https://search-podsurfer-qvfiylziz3nlsnsfewlzggrnf4.us-east-1.es.amazonaws.com";

    public Elasticsearch(){

    }

    public String savePodcast(Podcast podcast) {
        return esPostObject("/podcasts/podcast", podcast);
    }
    
    // NEW
    public String getFavorites(String encodedEmail)
    {
    	return esGetRequest("/users/user/" + encodedEmail + "/_source");
    }
    
    // NEW
    public String editFavorites(String encodedEmail, Favorites favoritesList)
    {
    	return esPostObject("/users/user/" + encodedEmail, favoritesList);
    }

    public String updatePodcast(String podcastId, Podcast podcast) {
        return esPostObject("/podcasts/podcast/" + podcastId, podcast);
    }

    public String getPodcast(String podcastId) {
        return esGetRequest("/podcasts/podcast/" + podcastId);
    }

    public String saveEpisode(String podcastId, Episode episode) {
        return esPostObject("/podcasts/episode/?parent=" + podcastId, episode);
    }

    public String getPodcastEpisodeByNumber(String podcastId, String episodeId) {

        String query =  "{\n" +
                        "    \"query\": {\n" +
                        "        \"has_parent\": {\n" +
                        "            \"type\": \"podcast\",\n" +
                        "            \"query\": {\n" +
                        "                \"terms\": {\n" +
                        "                    \"_uid\": [ \"podcast#" + podcastId + "\" ]  \n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"filter\": {\n" +
                        "       \"term\": {\n" +
                        "           \"number\": " + episodeId + "\n" +
                        "       }\n" +
                        "    }\n" +
                        "}";

        return esPostString("/podcasts/episode/_search", query);

    }

    public String getPodcastByEpisodeId(String episodeId) {
        String query =  "{\n" +
                        "    \"query\": {\n" +
                        "        \"has_child\": {\n" +
                        "           \"type\": \"episode\",\n" +
                        "           \"query\": {\n" +
                        "               \"match\": {\n" +
                        "                   \"_id\": \"" + episodeId + "\"\n" +
                        "               }\n" +
                        "           }\n" +
                        "       }\n" +
                        "     }\n" +
                        "}\n";

        System.out.println(query);

        return esPostString("/podcasts/podcast/_search", query);

    }

    public String getEpisodeById(String podcastId, String episodeId) {
        return esGetRequest("/podcasts/episode/" + episodeId + "?parent=" + podcastId);
    }

    public String getAllEpisodesForPodcast(String podcastId) {

        String query =  "{\n" +
                        "    \"query\": {\n" +
                        "        \"has_parent\": {\n" +
                        "            \"type\": \"podcast\",\n" +
                        "            \"query\": {\n" +
                        "                \"terms\": {\n" +
                        "                    \"_uid\": [ \"podcast#" + podcastId + "\" ]  \n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";

        System.out.print(query);

        return esPostString("/podcasts/episode/_search", query);

    }

    public String saveReview(String podcastId, Review review) {
        return esPostObject("/podcasts/review/?parent=" + podcastId, review);
    }

    public String getPodcastReviewById(String podcastId, String reviewId) {

        return esGetRequest("/podcasts/review/" + reviewId + "?parent=" + podcastId);

    }

    public String getPopularPodcasts() {
     
        String query =  "{\n" +
                        "   \"sort\" : {\n" +
                        "       \"views\": {\n" +
                        "           \"order\" : \"desc\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}";

        return esPostString("/podcasts/podcast/_search", query);
    }

    public String getAllReviewsForPodcast(String podcastId) {

        String query =  "{\n" +
                        "    \"query\": {\n" +
                        "        \"has_parent\": {\n" +
                        "            \"type\": \"podcast\",\n" +
                        "            \"query\": {\n" +
                        "                \"terms\": {\n" +
                        "                    \"_uid\": [ \"podcast#" + podcastId + "\" ]  \n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"aggs\": {\n" +
                        "       \"avg_rating\": {\n" +
                        "           \"avg\": { \"field\": \"rating\"}\n" +
                        "       }\n" +
                        "    }\n" +
                        "}";

        System.out.print(query);

        return esPostString("/podcasts/review/_search", query);

    }

    public String searchAll() {
        return esGetRequest("/podcasts/_search");
    }

    public String searchAll(SearchQuery searchQuery) {

        String query =  "{\n" +
                        "   \"query\" : {\n" +
                        "       \"match\": {\n" +
                        "           \"_all\" : {\n" +
                        "               \"query\": \"" + searchQuery.getQuery() + "\"\n" +
                        "            }\n" +
                        "       }\n" +
                        "   },\n" +
                        "   \"highlight\": {\n" +
                        "       \"fields\": {\n" +
                        "           \"name\": {},\n" +
                        "           \"description\": {}\n" +
                        "       }\n" +
                        "   }\n" +
                        "}";

        System.out.println(query);

        return esPostString("/podcasts/_search", query);
        
    }

    public String searchPodcast() {
        return esGetRequest("/podcasts/podcast/_search");
    }

    public String searchPodcast(SearchQuery searchQuery) {

        String query =  "{\n" +
                "   \"query\" : {\n" +
                "       \"term\": {\n" +
                "           \"_all\" : \"" + searchQuery.getQuery() + "\"\n" +
                "       }\n" +
                "   }\n" +
                "}";

        return esPostString("/podcasts/podcast/_search", query);

    }

    public String searchEpisode() {
        return esGetRequest("/podcasts/episode/_search");
    }

    public String searchEpisode(SearchQuery searchQuery) {

        String query =  "{\n" +
                        "   \"query\" : {\n" +
                        "       \"term\": {\n" +
                        "           \"_all\" : \"" + searchQuery.getQuery() + "\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}";

        return esPostString("/podcasts/episode/_search", query);

    }

    public String getRecommendations(UserPreferences userPreferences) {

        String query =  "{\n" +
                        "   \"query\" : {\n" +
                        "       \"bool\": {\n" +
                        "           \"should\" : " + userPreferences.toTermsArray() +
                        "       }\n" +
                        "   }\n" +
                        "}";

        System.out.println(query);

        return esPostString("/podcasts/podcast/_search", query);

    }

    public String getUserPreferences(String email) {
        return esGetRequest("/users/preferences/" + email + "/_source");
    }

    public String updateUserPreferences(String email, String userPreferences) {

        String endpoint = "/users/preferences/" + email + "/_update";
        String query = "{\n" +
                        "   \"doc\" : " + userPreferences + ",\n" +
                        "   \"doc_as_upsert\": true\n" +
                        "}";

        return esPostString(endpoint, query);

    }

    public String getHealth() {

        return esGetRequest("/_cluster/health?pretty=true");

    }

    public String esGetRequest(String endpoint) {

        Response response = new Response();
        String requestUrl = elasticsearchUrl + endpoint;

        try {

            HttpResponse<JsonNode> jsonResponse = Unirest.get(requestUrl)
                    .asJson();

            return jsonResponse.getBody().toString();

            /*response.setSuccess(true);
            response.setMessage(jsonResponse.getBody().toString());*/
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            String errorResponse = "{ \"Error\": \"An Unexpected error occured\" }";
            return errorResponse;
        }

        // return response;
    };

    public String esPostObject(String endpoint, Object payload) {

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

            return jsonResponse.getBody().toString();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            String errorResponse = "{ \"Error\": \"An Unexpected error occured\" }";
            return errorResponse;
        }

        //return response;
    };

    public String esPostString(String endpoint, String payload) {

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

            return jsonResponse.getBody().toString();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            String errorResponse = "{ \"Error\": \"An Unexpected error occured\" }";
            return errorResponse;
        }

        //return response;
    };

}


