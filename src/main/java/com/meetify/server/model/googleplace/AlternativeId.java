package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlternativeId implements Serializable {
    
    @JsonProperty("place_id")
    private String placeId;
    private String scope;
    
    public AlternativeId() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AlternativeId that = (AlternativeId) o;
        
        return placeId != null ? placeId.equals(that.placeId) : that.placeId == null && (scope != null ? scope.equals(that.scope) : that.scope == null);
        
    }
    
    @Override
    public int hashCode() {
        int result = placeId != null ? placeId.hashCode() : 0;
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        return result;
    }
    
    public String getPlaceId() {
        
        return placeId;
    }
    
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
}
