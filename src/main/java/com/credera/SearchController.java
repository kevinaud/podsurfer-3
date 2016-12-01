package com.credera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elasticsearch.Elasticsearch;

/**
 * Created by kevinaud on 10/4/16.
 */
@Controller
public class SearchController {
    @Autowired
    private Elasticsearch es;

    @ResponseBody
    @RequestMapping("/search/health")
    public String getClusterHealth(){
        return es.getHealth();
    }

    @ResponseBody
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchAll(){
        return es.searchAll();
    }

    @ResponseBody
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public String searchAll(@RequestBody SearchQuery searchQuery){
        return es.searchAll(searchQuery);
    }

    @ResponseBody
    @RequestMapping(value="/search/podcast", method = RequestMethod.GET)
    public String searchPodcast(){
        return es.searchPodcast();
    }

    @ResponseBody
    @RequestMapping(value="/search/podcast", method = RequestMethod.POST)
    public String searchPodcast(@RequestBody SearchQuery searchQuery){
        return es.searchPodcast(searchQuery);
    }

    @ResponseBody
    @RequestMapping(value="/search/episode", method = RequestMethod.GET)
    public String searchEpisode(){
        return es.searchPodcast();
    }

    @ResponseBody
    @RequestMapping(value="/search/episode", method = RequestMethod.POST)
    public String searchEpisode(@RequestBody SearchQuery searchQuery){
        return es.searchEpisode(searchQuery);
    }

}
