package com.credera;

import com.podsurferAPI.PodsurferAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.facebookAPI.FacebookAPI;
import com.restfb.FacebookClient;

import com.elasticsearch.Elasticsearch;

@Controller
public class PodcastController {

    @Autowired
    private PodsurferAPI podsurferAPI;
    @Autowired
    private FacebookAPI fapi;
    @Autowired
    private Elasticsearch es;

    @ResponseBody
    @RequestMapping(value="/podcast", method=RequestMethod.POST)
    public String savePodcast(@RequestBody Podcast podcast, @RequestHeader("Authorization") String token,
                              @RequestHeader("Server") String server){

      String response;

      if(server.equals("podsurfer")) {
          Response authResponse = podsurferAPI.getUserInfo(token);
          if (authResponse.getSuccess()) {
              response = es.savePodcast(podcast);
          } else {
              response = "Authentication failed";
          }
      }
      else if(server.equals("facebook")){
        FacebookClient fb = fapi.getAuthorizedClient(token);
        if(fb != null){
          response = es.savePodcast(podcast);
        } else {
            response = "Authentication failed";
        }
      }
      else
          response = "invalid server";

      return response;
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}", method=RequestMethod.POST)
    public String updatePodcast(@PathVariable String podcastId, @RequestBody Podcast podcast, @RequestHeader("Authorization") String token,
                                @RequestHeader("Server") String server) {
        String response;
        if(server.equals("podsurfer")) {
            Response authResponse = podsurferAPI.getUserInfo(token);
            if (authResponse.getSuccess()) {
                response = es.updatePodcast(podcastId, podcast);
            } else {
                response = "Authentication failed";
            }
        }
        else if(server.equals("facebook")){
            FacebookClient fb = fapi.getAuthorizedClient(token);
            if(fb != null){
                response = es.savePodcast(podcast);
            } else {
                response = "Authentication Failed";
            }
        }
        else
            response = "invalid server";

        return response;
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}", method=RequestMethod.GET)
    public String getPodcast(@PathVariable String podcastId){
        return es.getPodcast(podcastId);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes", method=RequestMethod.POST)
    public String saveEpisode(@PathVariable String podcastId, @RequestBody Episode episode,  @RequestHeader("Authorization") String token,
                              @RequestHeader("Server") String server){
      String response;
      if(server.equals("podsurfer")) {
          Response authResponse = podsurferAPI.getUserInfo(token);
          if (authResponse.getSuccess()) {
              response = es.saveEpisode(podcastId, episode);
          } else {
              response = "Authentication failed";
          }
      }
      else if(server.equals("facebook")){
        FacebookClient fb = fapi.getAuthorizedClient(token);
        if(fb != null){
            response = es.saveEpisode(podcastId, episode);
        } else {
            response = "Authentication Failed";
        }
      }
      else
          response = "invalid server";
      return response;
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes/{episodeNumber}", method=RequestMethod.GET)
    public String getPodcastEpisodeByNumber(@PathVariable String podcastId, @PathVariable String episodeNumber){
        return es.getPodcastEpisodeByNumber(podcastId, episodeNumber);
    }

    @ResponseBody
    @RequestMapping(value="/episodes/{podcastId}/{episodeId}", method=RequestMethod.GET)
    public String getEpisodeById(@PathVariable String podcastId, @PathVariable String episodeId){
        return es.getEpisodeById(podcastId, episodeId);
    }

    @ResponseBody
    @RequestMapping(value="/episodes/{episodeId}/podcast", method=RequestMethod.GET)
    public String getPodcastByEpisodeId(@PathVariable String episodeId){
        return es.getPodcastByEpisodeId(episodeId);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes", method=RequestMethod.GET)
    public String getAllEpisodesForPodcast(@PathVariable String podcastId){
        return es.getAllEpisodesForPodcast(podcastId);
    }


    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/reviews", method=RequestMethod.POST)
    public String saveReview(@PathVariable String podcastId, @RequestBody Review review,  @RequestHeader("Authorization") String token,
                             @RequestHeader("Server") String server){
        String response;
        if(server.equals("podsurfer")) {
            Response authResponse = podsurferAPI.getUserInfo(token);
            if (authResponse.getSuccess()) {
                response = es.saveReview(podcastId, review);
            } else {
                response = "Authentication failed";
            }
        }
        else if(server.equals("facebook")){
            FacebookClient fb = fapi.getAuthorizedClient(token);
            if(fb != null){
                response = es.saveReview(podcastId, review);
            } else {
                response = "Authentication Failed";
            }
        }
        else
            response = "invalid server";
        return response;
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/reviews/{reviewId}", method=RequestMethod.GET)
    public String getPodcastReviewById(@PathVariable String podcastId, @PathVariable String reviewId){
        return es.getPodcastReviewById(podcastId, reviewId);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/reviews", method=RequestMethod.GET)
    public String getAllReviewsForPodcast(@PathVariable String podcastId){
        return es.getAllReviewsForPodcast(podcastId);
    }

    @ResponseBody @RequestMapping(value="/recommendations", method=RequestMethod.POST)
    public String getRecommendations(@RequestBody String userPreferencesString) {

        UserPreferences userPreferences = new UserPreferences(userPreferencesString);

        return es.getRecommendations(userPreferences);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/popular", method=RequestMethod.GET)
    public String getPopularPodcasts(){
        return es.getPopularPodcasts();
    }

}
