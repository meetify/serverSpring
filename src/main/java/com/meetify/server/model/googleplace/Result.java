package com.meetify.server.model.googleplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private Geometry geometry;
    
    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
    
    @JsonProperty("formatted_address")
    private String formattedAddress;
    
    @JsonProperty("formatted_phone_number")
    private String formattedPhoneNumber;
    
    @JsonProperty("international_phone_number")
    private String internationalPhoneNumber;
    
    @JsonProperty("place_id")
    private String placeId;
    
    private String icon;
    private String id;
    private String name;
    private String reference;
    private String scope;
    private String vicinity;
    private String website;
    private String url;
    private Double rating;
    
    @JsonProperty("permanently_closed")
    private boolean permanentlyClosed;
    
    @JsonProperty("utc_offset")
    private Integer utcOffset;
    
    @JsonProperty("price_level")
    private Integer priceLevel;
    private List<String> types;
    private List<Photo> photos;
    
    @JsonProperty("alt_ids")
    private List<AlternativeId> alternativeIds;
    
    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;
    
    private List<Review> reviews;
    
    public Result() {
    }
    
    public boolean getPermanentlyClosed() {
        return permanentlyClosed;
    }
    
    public void setPermanentlyClosed(boolean permanentlyClosed) {
        this.permanentlyClosed = permanentlyClosed;
    }
    
    public String getFormattedAddress() {
        return formattedAddress;
    }
    
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
    
    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }
    
    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }
    
    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }
    
    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }
    
    public OpeningHours getOpeningHours() {
        return openingHours;
    }
    
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getUtcOffset() {
        return utcOffset;
    }
    
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }
    
    public Integer getPriceLevel() {
        return priceLevel;
    }
    
    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }
    
    public List<AlternativeId> getAlternativeIds() {
        return alternativeIds;
    }
    
    public void setAlternativeIds(List<AlternativeId> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }
    
    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Result result = (Result) o;
        
        return id != null ? id.equals(result.id) : result.id == null;
        
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    public Geometry getGeometry() {
        return geometry;
    }
    
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPlaceId() {
        return placeId;
    }
    
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    
    public String getReference() {
        return reference;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public String getVicinity() {
        return vicinity;
    }
    
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public List<String> getTypes() {
        return types;
    }
    
    public void setTypes(List<String> types) {
        this.types = types;
    }
    
    public List<Photo> getPhotos() {
        return photos;
    }
    
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
}
