package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Review implements Serializable {
    private List<Aspect> aspects;
    
    @JsonProperty("author_name")
    private String authorName;
    
    @JsonProperty("author_url")
    private String authorUrl;
    private String language;
    private Double rating;
    private String text;
    private Integer time;
    
    public Review() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Review review = (Review) o;
        
        return authorName != null ? authorName.equals(review.authorName) : review.authorName == null && (text != null ? text.equals(review.text) : review.text == null && (time != null ? time.equals(review.time) : review.time == null));
        
    }
    
    @Override
    public int hashCode() {
        int result = authorName != null ? authorName.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
    
    public List<Aspect> getAspects() {
        return aspects;
    }
    
    public void setAspects(List<Aspect> aspects) {
        this.aspects = aspects;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorUrl() {
        return authorUrl;
    }
    
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Integer getTime() {
        return time;
    }
    
    public void setTime(Integer time) {
        this.time = time;
    }
}
