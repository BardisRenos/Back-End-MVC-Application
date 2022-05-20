package com.example.WeibisWeb.controller;

import com.example.WeibisWeb.JwtModel.JwtRequest;
import com.example.WeibisWeb.JwtModel.JwtResponse;
import com.example.WeibisWeb.dto.UserDTO;
import com.example.WeibisWeb.dto.UserNoPassDTO;
import com.example.WeibisWeb.exception.UserNotFoundException;
import com.example.WeibisWeb.security.JwtUtil;
import com.example.WeibisWeb.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * The Controller layer of User
 */
@RestController
@RequestMapping("/api/1.0/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;

    /**
     * Retrieve all Users
     * @return A list of UserDTO
     */
    @GetMapping(value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userServiceImpl.getAllUsers();
    }

    /**
     * Retrieve User by Id
     * @param id The id of the User
     * @return A list of UserDTOs
     */
    @GetMapping(value = "/users/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable(value = "id") UUID id) throws UserNotFoundException {
        return userServiceImpl.getUserById(id);
    }

    /**
     * Retrieve User(s) by the last name
     * @param lastName The lastName of the User
     * @return A list of UserDTOs
     */
    @GetMapping(value = "/users/lastName/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsersByLastName(@PathVariable(value = "lastName") String lastName) {
        return userServiceImpl.getUserByLastName(lastName);
    }

    /**
     * Retrieve User(s) by first name
     * @param firstName The firstName of the User
     * @return A list of UserDTOs
     */
    @GetMapping(value = "/users/firstName/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsersByFirstName(@PathVariable(value = "firstName") String firstName) {
        return userServiceImpl.getUserByFirstName(firstName);
    }

    /**
     * Retrieve User by email
     * @param email The email of the User
     * @return A UserDTO class
     */
    @GetMapping(value = "/users/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@PathVariable(value = "email") String email) {
        return userServiceImpl.getUserByEmail(email);
    }

    /**
     * Inserting a new User Entity
     * @param userDTO The user class
     * @return A JwtResponse class
     */
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserNoPassDTO saveUser(@RequestBody UserDTO userDTO) {
        return userServiceImpl.registerUser(userDTO);
    }

    /**
     * Authenticate the user
     * @param jwtRequest A JWT Request class (username and password)
     * @return ResponseEntity
     * @throws BadCredentialsException Throws an exception in case
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect username or password", ex);
        }

        final UserDetails userDetails = userServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt));
    }

    /**
     * Updating the User with new data by giving the id of the User
     * @param id The id of the User
     * @return UserNoPassDTO class
     */
    @PutMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserNoPassDTO replaceUser(@RequestBody UserNoPassDTO userNoPassDTO, @PathVariable(value = "id") UUID id) {
        return userServiceImpl.replaceUser(userNoPassDTO, id);
    }

    /**
     * Delete the User by giving an id number
     * @param id The id number of the User
     * @return A String which indicates the entity id is deleted
     * @throws UserNotFoundException The Exception that throws
     */
    @DeleteMapping(value = "/user/{id}")
    public String deleteUser(@PathVariable(value = "id") UUID id) throws UserNotFoundException {
        return userServiceImpl.deleteUserById(id);
    }

}
