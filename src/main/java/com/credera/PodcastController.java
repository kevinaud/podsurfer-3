package com.credera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.elasticsearch.Elasticsearch;

/**
 * Created by kevinaud on 10/5/16.
 */
@Controller
public class PodcastController {

    @Autowired
    private Elasticsearch es;

    @ResponseBody
    @RequestMapping(value="/podcast", method=RequestMethod.POST)
    public String savePodcast(@RequestBody Podcast podcast){
        return es.savePodcast(podcast);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}", method=RequestMethod.POST)
    public String updatePodcast(@PathVariable String podcastId, @RequestBody Podcast podcast) {
        return es.updatePodcast(podcastId, podcast);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}", method=RequestMethod.GET)
    public String getPodcast(@PathVariable String podcastId){
        return es.getPodcast(podcastId);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes", method=RequestMethod.POST)
    public String saveEpisode(@PathVariable String podcastId, @RequestBody Episode episode){
        return es.saveEpisode(podcastId, episode);
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
    public String saveReview(@PathVariable String podcastId, @RequestBody Review review){
        return es.saveReview(podcastId, review);
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

}
