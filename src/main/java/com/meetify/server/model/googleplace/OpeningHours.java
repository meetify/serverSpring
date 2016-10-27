package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpeningHours implements Serializable {
    @JsonProperty("open_now")
    private boolean openNow;
    
    @JsonProperty("weekday_text")
    private List<String> weekdayText;
    
    private List<Period> periods;
    
    public OpeningHours() {
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        OpeningHours that = (OpeningHours) o;
        
        return openNow == that.openNow && (weekdayText != null ? weekdayText.equals(that.weekdayText) : that.weekdayText == null && (periods != null ? periods.equals(that.periods) : that.periods == null));
        
    }
    
    @Override
    public int hashCode() {
        int result = (openNow ? 1 : 0);
        result = 31 * result + (weekdayText != null ? weekdayText.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }
    
    public boolean isOpenNow() {
        
        return openNow;
    }
    
    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }
    
    public List<String> getWeekdayText() {
        return weekdayText;
    }
    
    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
    
    public List<Period> getPeriods() {
        return periods;
    }
    
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }
}
