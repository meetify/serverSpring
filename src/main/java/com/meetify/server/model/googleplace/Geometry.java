package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Geometry implements Serializable {
    private Location location;
    private Viewport viewport;
    
    public Geometry() {
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
}
