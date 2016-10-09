package com.meetify.server.model.ids;

import java.io.Serializable;

/**
 * com.meetify.server.model.ids
 * Created by kr3v on 09.10.2016.
 */

@SuppressWarnings("unused")
public class PlaceID implements Serializable {
    private Long id;
    
    public PlaceID() {
        
    }
    
    public PlaceID(Long id) {
        
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PlaceID placeID = (PlaceID) o;
        
        return id != null ? id.equals(placeID.id) : placeID.id == null;
        
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    public Long getId() {
        
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
