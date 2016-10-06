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
    @RequestMapping(value="/podcast/{podcastId}", method=RequestMethod.GET)
    public String getPodcast(@PathVariable String podcastId){
        return es.getPodcast(podcastId);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes", method=RequestMethod.POST)
    public String saveEpisode(@RequestBody Episode episode){
        return es.saveEpisode(episode);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes/{episodeNumber}", method=RequestMethod.GET)
    public String getPodcastEpisodeByNumber(@PathVariable String podcastId, @PathVariable String episodeNumber){
        return es.getPodcastEpisodeByNumber(podcastId, episodeNumber);
    }

    @ResponseBody
    @RequestMapping(value="/podcast/{podcastId}/episodes", method=RequestMethod.GET)
    public String getAllEpisodesForPodcast(@PathVariable String podcastId){
        return es.getAllEpisodesForPodcast(podcastId);
    }

}
