package com.example.WeibisWeb.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * The ClientDTO class
 */
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {

    private UUID clientId;
    private String companyName;
    private String sector;
    private String city;
    private String country;
    private Integer size;
}
