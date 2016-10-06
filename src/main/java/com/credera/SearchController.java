package com.credera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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



}
