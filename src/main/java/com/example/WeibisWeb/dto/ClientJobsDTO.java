package com.example.WeibisWeb.dto;


import com.example.WeibisWeb.resources.JobDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * The ClientJobsDTO class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientJobsDTO {

    private UUID clientJobsId;
    private String companyName;
    private String sector;
    private String city;
    private String country;
    private Integer size;
    private List<JobDescription> jobDescriptions;
}
