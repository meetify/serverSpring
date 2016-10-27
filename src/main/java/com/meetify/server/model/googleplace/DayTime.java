package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayTime implements Serializable {
    private Integer day;
    private Integer time;
    
    public DayTime() {
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        DayTime dayTime = (DayTime) o;
        
        return day != null ? day.equals(dayTime.day) : dayTime.day == null && (time != null ? time.equals(dayTime.time) : dayTime.time == null);
        
    }
    
    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
    
    public Integer getTime() {
        
        return time;
    }
    
    public void setTime(Integer time) {
        this.time = time;
    }
    
    public Integer getDay() {
        return day;
    }
    
    public void setDay(Integer day) {
        this.day = day;
    }
}
