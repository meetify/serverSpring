package com.meetify.server.repository;

import com.meetify.server.model.Id;
import com.meetify.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * com.meetify.server.repository
 * Created by kr3v on 04.10.2016.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Id id);
}
