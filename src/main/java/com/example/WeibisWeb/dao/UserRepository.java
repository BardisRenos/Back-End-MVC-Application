package com.example.WeibisWeb.dao;

import com.example.WeibisWeb.resources.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository layer of the User
 */
@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {

    /**
     * Retrieve the User entity by a given ID
     * @param id The id number of a User
     * @return A User Entity
     */
    Optional<User> findByUserId(UUID id);

    /**
     * Retrieve the User entity by a given Last Name
     * @param lastName The lastName of a User
     * @return A list of User Entities
     */
    List<User> findByLastName(String lastName);

    /**
     * Retrieve the User entity by a given First Name
     * @param firstName The firstName of a User
     * @return A list of User Entities
     */
    List<User> findByFirstName(String firstName);

    /**
     * Retrieve the User entity by a given email
     * @param email The email of a User
     * @return A User Entity
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieve the User entity by a given username
     * @param username The username of the user
     * @return A User Entity
     */
    User findByUsername(String username);
}
