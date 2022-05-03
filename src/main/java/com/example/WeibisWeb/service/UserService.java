package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.UserDTO;
import com.example.WeibisWeb.dto.UserNoPassDTO;
import com.example.WeibisWeb.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The User Service Interface. All methods that User service has.
 */
public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(UUID id) throws UserNotFoundException;
    List<UserDTO> getUserByLastName(String lastName);
    List<UserDTO> getUserByFirstName(String firstName);
    UserNoPassDTO registerUser(UserDTO userDTO);
    UserNoPassDTO replaceUser(UserNoPassDTO userNoPassDTO, UUID id) throws UserNotFoundException;
    String deleteById(UUID id) throws UserNotFoundException;
    UserDTO getUserByEmail(String email);
}
