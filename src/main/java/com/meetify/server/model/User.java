package com.meetify.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetify.server.model.ids.UserID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kr3v on 02.10.2016.
 * Users entity. Based on VK ID.
 */

@SuppressWarnings("unused")

@Entity
@Table(name = "users")
@IdClass(UserID.class)
public class User implements Serializable {
    
    @Id
    @Column(name = "user_id")
    private Long vkID;
    
    @ElementCollection
    private Set<Long> friends;
    
    @JsonIgnore
    @ElementCollection
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Place> placesCreated;
    
    @ElementCollection
    private Set<Long> placesInvolved;
    
    private User() {
    }
    
    public User(Long vkID, Set<Long> friends) {
        this.vkID = vkID;
        this.friends = friends;
        this.placesCreated = new HashSet<>();
        this.placesInvolved = new HashSet<>();
    }
    
    public Set<Place> getPlacesCreated() {
        return placesCreated;
    }
    
    public Set<Long> getPlacesInvolved() {
        return placesInvolved;
    }
    
    public Set<Long> getFriends() {
        return friends;
    }
    
    public User setFriends(Set<Long> friends) {
        this.friends = friends;
        return this;
    }
    
    public Long getVkID() {
        return vkID;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        return vkID != null ? vkID.equals(user.vkID) : user.vkID == null;
        
    }
    
    @Override
    public int hashCode() {
        return vkID != null ? vkID.hashCode() : 0;
    }
    
    public User addPlacesInvolved(Long aLong) {
        placesInvolved.add(aLong);
        System.out.println(this);
        return this;
    }
}
