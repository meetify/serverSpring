package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location implements Serializable {
    private double lat;
    private double lng;
    
    public Location(double lat, double lng) {
        
        this.lat = lat;
        this.lng = lng;
    }
    
    public Location() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Location location = (Location) o;
        
        return Double.compare(location.lat, lat) == 0 && Double.compare(location.lng, lng) == 0;
        
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    
    public double getLat() {
        
        return lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }
    
    public double getLng() {
        return lng;
    }
    
    public void setLng(double lng) {
        this.lng = lng;
    }
}
