package com.example.WeibisWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The UserDTO class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userId;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String role;
    private String password;

}
