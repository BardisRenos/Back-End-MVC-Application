package com.example.WeibisWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * The UserDTO class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private UUID userId;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String role;
    private String password;

}
