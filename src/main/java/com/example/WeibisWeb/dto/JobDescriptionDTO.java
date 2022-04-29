package com.example.WeibisWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The JobDescriptionDTO class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDescriptionDTO {

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
