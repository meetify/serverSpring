package com.meetify.server.model.ids;

import java.io.Serializable;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */
@SuppressWarnings("unused")
public class UserID implements Serializable {
    private Long vkID;

    public UserID() {

    }

    public UserID(Long vkID) {

        this.vkID = vkID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserID userID = (UserID) o;

        return vkID != null ? vkID.equals(userID.vkID) : userID.vkID == null;

    }

    @Override
    public int hashCode() {
        return vkID != null ? vkID.hashCode() : 0;
    }

    public Long getVkID() {

        return vkID;
    }

    public void setVkID(Long vkID) {
        this.vkID = vkID;
    }
}
