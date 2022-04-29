package com.example.WeibisWeb.JwtModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    public JwtRequest() {
    }
}
