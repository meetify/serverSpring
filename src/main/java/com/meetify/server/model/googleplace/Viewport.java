package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Viewport implements Serializable {
    private Location northeast;
    private Location southwest;
    
    public Viewport() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Viewport viewport = (Viewport) o;
        
        return northeast != null ? northeast.equals(viewport.northeast) : viewport.northeast == null && (southwest != null ? southwest.equals(viewport.southwest) : viewport.southwest == null);
        
    }
    
    @Override
    public int hashCode() {
        int result = northeast != null ? northeast.hashCode() : 0;
        result = 31 * result + (southwest != null ? southwest.hashCode() : 0);
        return result;
    }
    
    public Location getNortheast() {
        
        return northeast;
    }
    
    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }
    
    public Location getSouthwest() {
        return southwest;
    }
    
    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }
}
