package com.meetify.server.repository;

import com.meetify.server.model.Key;
import com.meetify.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * com.meetify.server.repository
 * Created by kr3v on 08.10.2016.
 */
public interface KeyRepository extends JpaRepository<Key, Long> {
    Optional<Key> findByUser(User user);
    
    Optional<Key> findByDevInfo(String devInfo);
}
