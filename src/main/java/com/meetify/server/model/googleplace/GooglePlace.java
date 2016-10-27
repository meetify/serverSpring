package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Google place class
 * Created by Dima on 15.10.2016.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GooglePlace implements Serializable {
    private List<Result> results;
    
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;
    
    private String status;
    
    @JsonProperty("next_page_token")
    private String nextPageToken;
    
    public GooglePlace() {
    }
    
    public String getNextPageToken() {
        return nextPageToken;
    }
    
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Result> getResults() {
        return results;
    }
    
    public void setResults(List<Result> results) {
        this.results = results;
    }
    
    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }
    
    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }
    
}

