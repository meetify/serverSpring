package com.meetify.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meetify.server.model.ids.KeyID;

import javax.persistence.*;
import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */

@Entity
@Table(name = "keys")
@IdClass(KeyID.class)
public class Key implements Serializable {
    
    @Id
    @JsonIgnore
    @OneToOne
    private User user;
    
    @Column(name = "time")
    private Long time;
    
    @Column(name = "devinfo")
    private String devInfo;
    
    @SuppressWarnings("unused")
    private Key() {
    }
    
    public Key(User user, String devInfo) {
        this.user = user;
        this.devInfo = devInfo;
        this.time = currentTimeMillis();
    }
    
    public User getUser() {
        return user;
    }
    
    public boolean usable() {
        return (currentTimeMillis() - time) >= (3600 * 1000);
    }
    
    public String getDevInfo() {
        return devInfo;
    }
}
