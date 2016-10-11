package com.meetify.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetify.server.model.embeddable.Location;
import com.meetify.server.model.ids.PlaceID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */

@SuppressWarnings("unused")
@Entity
@IdClass(PlaceID.class)
@Table(name = "places")
public class Place implements Serializable {
    
    private final static Random random = new Random();
    @Id
    @Column(name = "place_id")
    private Long id;
    @Column(name = "place_name")
    private String name;
    @Column(name = "place_description")
    private String description;
    @Column(name = "place_photo")
    private String photoIdentifier;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vkid", nullable = false)
    private User creator;
    // TODO: 11.10.2016 many-to-many?
    @ElementCollection
    private Set<Long> allowedUsers;
    @Embedded
    private Location location;
    
    private Place() {
    }
    
    public Place(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.id = nextId();
    }
    
    public Place(String name, String description, String photoIdentifier, User creator, Set<Long> involved, Location location) {
        this.name = name;
        this.description = description;
        this.photoIdentifier = photoIdentifier;
        this.creator = creator;
        this.allowedUsers = involved;
        this.location = location;
        this.id = nextId();
    }
    
    // TODO: 11.10.2016 not very cool solution
    private Long nextId() {
        long id = (long) name.hashCode() + description.hashCode() + creator.hashCode() + random.nextLong();
        return (id < 0) ? id + Long.MAX_VALUE : id;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getPhotoIdentifier() {
        return photoIdentifier;
    }
    
    public User getCreator() {
        return creator;
    }
    
    public Set<Long> getAllowedUsers() {
        return allowedUsers;
    }
    
    public Place update(Place place) {
        this.name = place.name;
        this.creator = place.creator;
        this.allowedUsers = place.allowedUsers;
        this.description = place.description;
        this.photoIdentifier = place.photoIdentifier;
        return this;
    }
    
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
