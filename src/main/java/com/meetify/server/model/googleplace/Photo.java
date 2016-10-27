package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo implements Serializable {
    private Double height;
    
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;
    
    @JsonProperty("photo_reference")
    private String photoReference;
    private Double width;
    
    public Photo() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Photo photo = (Photo) o;
        
        return photoReference != null ? photoReference.equals(photo.photoReference) : photo.photoReference == null;
        
    }
    
    @Override
    public int hashCode() {
        return photoReference != null ? photoReference.hashCode() : 0;
    }
    
    public Double getHeight() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }
    
    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }
    
    public String getPhotoReference() {
        return photoReference;
    }
    
    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
    
    public Double getWidth() {
        return width;
    }
    
    public void setWidth(Double width) {
        this.width = width;
    }
}
