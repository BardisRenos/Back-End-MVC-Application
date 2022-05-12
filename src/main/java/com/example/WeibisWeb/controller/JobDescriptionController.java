package com.example.WeibisWeb.controller;

import com.example.WeibisWeb.response.APIResponse;
import com.example.WeibisWeb.dto.JobDescriptionCandidateDTO;
import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.exception.JobDescriptionNotFoundException;
import com.example.WeibisWeb.service.JobDescriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * The Controller layer of JobDescription
 */
@RestController
@RequestMapping("/api/1.0/")
public class JobDescriptionController {

    private final JobDescriptionServiceImpl jobDescriptionServiceImpl;

    @Autowired
    public JobDescriptionController(JobDescriptionServiceImpl jobDescriptionServiceImpl) {
        this.jobDescriptionServiceImpl = jobDescriptionServiceImpl;
    }

    /**
     * Retrieve Job Descriptions by Id
     * @param id The id of the JobDescription
     * @return A JobDescriptionDTO object
     */
    @GetMapping(value = "/job")
    public JobDescriptionDTO getJobDescriptionsByCandidateId(@RequestParam(value = "id") UUID id) throws JobDescriptionNotFoundException {
        return jobDescriptionServiceImpl.getJobsDescriptionById(id);
    }

    /**
     * Retrieve all JobDescriptions by pagination
     * @param offset The offset of the data that we need to retrieve
     * @param pageSize The number of the records that will be retrieved on each offset
     * @return A APIResponse<Page<JobDescriptionDTO>>
     */
    @GetMapping(value = "/job/pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<Page<JobDescriptionDTO>> getAllJobsWithPaginationSorting(@PathVariable int offset, @PathVariable int pageSize) {
        Page<JobDescriptionDTO> jobDescriptionDTOPage = jobDescriptionServiceImpl.getAllJobsPagination(offset, pageSize);

        return new APIResponse<>(jobDescriptionDTOPage.getSize(), jobDescriptionDTOPage);
    }

    /**
     * Retrieve a list of Job Descriptions
     * @return A list of JobDescriptionsDTO
     */
    @GetMapping(value = "/jobs")
    @ResponseStatus(HttpStatus.OK)
    public List<JobDescriptionDTO> getAllJobs() {
        return jobDescriptionServiceImpl.getAllJobs();
    }

    /**
     * Retrieve Job Descriptions by programming language
     * @param language The language of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @GetMapping(value = "/jobs/language/{programmingLanguage}")
    @ResponseStatus(HttpStatus.OK)
    public List<JobDescriptionDTO> getJobDescriptionByProgrammingLanguage(@PathVariable(value = "language") String language) {
        return jobDescriptionServiceImpl.getJobsByProgrammingLanguage(language);
    }

    /**
     * Retrieve Job Descriptions by framework
     * @param framework The framework of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @GetMapping(value = "/jobs/framework/{framework}")
    @ResponseStatus(HttpStatus.OK)
    public List<JobDescriptionDTO> getJobDescriptionByFramework(@PathVariable(value = "framework") String framework) {
        return jobDescriptionServiceImpl.getJobsByFramework(framework.toLowerCase());
    }

    /**
     * Retrieve Job Descriptions by location
     * @param location The location of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @GetMapping(value = "/jobs/location/{location}")
    public List<JobDescriptionDTO> getJobDescriptionByLocation(@PathVariable(value = "location") String location) {
        return jobDescriptionServiceImpl.getJobsByLocation(location.toLowerCase());
    }

    /**
     * Inserting a new Job Description Entity
     * @param jobDescriptionDTO The jobDescriptionDTO class
     * @return A Job Description class
     */
    @PostMapping("/jobDescription")
    @ResponseStatus(HttpStatus.CREATED)
    public JobDescriptionDTO saveJobDescription(@Valid @RequestBody JobDescriptionDTO jobDescriptionDTO) {
        return jobDescriptionServiceImpl.registerJobDescription(jobDescriptionDTO);
    }

    /**
     * Retrieve Job Descriptions with Candidates by a given programming language
     * @param lang The programming language
     * @return A list of JobDescriptionCandidateDTO
     */
    @GetMapping(value = "/jobs/language/{lang}/candidate")
    public List<JobDescriptionCandidateDTO> getJobsWithCandidates(@PathVariable(value = "lang") String lang) {
        return jobDescriptionServiceImpl.getJobDescriptionWithCandidates(lang);
    }

}
