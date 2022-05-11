package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.exception.JobDescriptionNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * The Job Description Service Interface. All methods that Job Description service has.
 */
public interface JobDescriptionService {

    JobDescriptionDTO getJobsDescriptionById(UUID id) throws JobDescriptionNotFoundException;
    List<JobDescriptionDTO> getAllJobs();
    Page<JobDescriptionDTO> getAllJobsPagination(int offset, int pageSize);
    List<JobDescriptionDTO> getJobsByProgrammingLanguage(String programmingLanguage);
    List<JobDescriptionDTO> getJobsByFramework(String framework);
    JobDescriptionDTO registerJobDescription(JobDescriptionDTO jobDescriptionDTO);

}
