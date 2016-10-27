package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Period implements Serializable {
    private DayTime open;
    private DayTime close;
    
    public Period() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Period period = (Period) o;
        
        return open != null ? open.equals(period.open) : period.open == null && (close != null ? close.equals(period.close) : period.close == null);
        
    }
    
    @Override
    public int hashCode() {
        int result = open != null ? open.hashCode() : 0;
        result = 31 * result + (close != null ? close.hashCode() : 0);
        return result;
    }
    
    public DayTime getOpen() {
        
        return open;
    }
    
    public void setOpen(DayTime open) {
        this.open = open;
    }
    
    public DayTime getClose() {
        return close;
    }
    
    public void setClose(DayTime close) {
        this.close = close;
    }
}
