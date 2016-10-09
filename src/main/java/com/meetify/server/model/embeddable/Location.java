package com.meetify.server.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * com.meetify.server.model
 * Created by kr3v on 09.10.2016.
 */
@SuppressWarnings("unused")
@Embeddable
public class Location implements Serializable {
    @Column(name = "lat")
    private double lat;
    
    @Column(name = "lon")
    private double lon;
    
    public Location() {
        
    }
    
    public Location(double lat, double lon) {
        
        this.lat = lat;
        this.lon = lon;
    }
    
    public double getLon() {
        return lon;
    }
    
    public void setLon(double lon) {
        this.lon = lon;
    }
    
    public double getLat() {
        
        return lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }
}
