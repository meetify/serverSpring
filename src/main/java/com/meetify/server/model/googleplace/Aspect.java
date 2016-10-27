package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Aspect implements Serializable {
    private Integer rating;
    private String type;
    
    public Aspect() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Aspect aspect = (Aspect) o;
        
        return rating != null ? rating.equals(aspect.rating) : aspect.rating == null && (type != null ? type.equals(aspect.type) : aspect.type == null);
        
    }
    
    @Override
    public int hashCode() {
        int result = rating != null ? rating.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
    
    public Integer getRating() {
        
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
