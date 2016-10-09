package com.meetify.server.model;

import com.meetify.server.model.embeddable.Location;
import com.meetify.server.model.ids.PlaceID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */

@SuppressWarnings("unused")
@Entity
@IdClass(PlaceID.class)
@Table(name = "places")
public class Place implements Serializable {
    
    @Id
    @Column(name = "place_id")
    @GeneratedValue
    private Long id;
    
    @Column(name = "place_name")
    private String name;
    
    @Column(name = "place_description")
    private String description;
    
    @Column(name = "place_photo")
    private String photoIdentifier;
    
    @OneToOne
    private User creator;
    
    @OneToMany
    private List<User> involved;
    
    @Embedded
    private Location location;
    
    public Place() {
    }
    
    public Place(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
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
    
    public List<User> getInvolved() {
        return involved;
    }
}
