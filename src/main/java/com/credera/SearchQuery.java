package com.credera;

/**
 * Created by kevinaud on 10/18/16.
 */
public class SearchQuery {

    private String query;

    public SearchQuery() {};

    public SearchQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
