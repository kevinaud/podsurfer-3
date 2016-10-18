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
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public String searchAll(@RequestBody SearchQuery searchQuery){
        return es.searchAll(searchQuery);
    }

    @ResponseBody
    @RequestMapping(value="/search/podcast", method = RequestMethod.POST)
    public String searchPodcasts(@RequestBody SearchQuery searchQuery){
        return es.searchPodcast(searchQuery);
    }

    @ResponseBody
    @RequestMapping(value="/search/episode", method = RequestMethod.POST)
    public String searchEpisodes(@RequestBody SearchQuery searchQuery){
        return es.searchEpisode(searchQuery);
    }

}
