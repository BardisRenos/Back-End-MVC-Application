package com.example.WeibisWeb.dto;

import com.example.WeibisWeb.resources.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The JobDescriptionCandidateDTO class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDescriptionCandidateDTO implements Serializable {

    private UUID JobDescriptionCandidateId;
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
    private List<Candidate> candidates;

}
