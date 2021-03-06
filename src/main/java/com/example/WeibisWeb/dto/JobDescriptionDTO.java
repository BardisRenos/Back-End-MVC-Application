package com.example.WeibisWeb.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * The JobDescriptionDTO class
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class JobDescriptionDTO implements Serializable {

    private UUID jobDescriptionId;
    private String companyName;
    private String title;
    private String location;
    private String description;
    private String programmingLanguage;
    private String databaseName;
    private String framework;
    private String level;
    private String isOpen;
    private Integer numberNeeded;
}
