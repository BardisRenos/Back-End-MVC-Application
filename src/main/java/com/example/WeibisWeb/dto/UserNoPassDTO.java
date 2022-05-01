package com.example.WeibisWeb.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserNoPassDTO implements Serializable {

    private UUID userId;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String role;
}
