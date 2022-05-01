package com.example.WeibisWeb.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * The CandidateDTO class
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CandidateDTO implements Serializable {

    private UUID candidateId;
    private String name;
    private String lastName;
    private String dob;
    private String address;
    private String city;
    private String country;

}
