package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressComponent implements Serializable {
    
    @JsonProperty("long_name")
    private String longName;
    
    @JsonProperty("short_name")
    private String shortName;
    
    private List<String> types;
    
    public AddressComponent() {
    }
    
    public String getLongName() {
        return longName;
    }
    
    public void setLongName(String longName) {
        this.longName = longName;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public List<String> getTypes() {
        return types;
    }
    
    public void setTypes(List<String> types) {
        this.types = types;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AddressComponent that = (AddressComponent) o;
        
        return longName != null ? longName.equals(that.longName) : that.longName == null && (shortName != null ? shortName.equals(that.shortName) : that.shortName == null && (types != null ? types.equals(that.types) : that.types == null));
        
    }
    
    @Override
    public int hashCode() {
        int result = longName != null ? longName.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        return result;
    }
}
