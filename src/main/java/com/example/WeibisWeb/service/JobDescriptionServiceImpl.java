package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.JobDescriptionRepository;
import com.example.WeibisWeb.dto.JobDescriptionCandidateDTO;
import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.dtoMapper.JobDescriptionCandidateMapper;
import com.example.WeibisWeb.dtoMapper.JobDescriptionMapper;
import com.example.WeibisWeb.exception.JobDescriptionNotFoundException;
import com.example.WeibisWeb.resources.JobDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer of JobDescription
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobDescriptionServiceImpl implements JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;

    /**
     * Retrieve Job Description by Id
     * @param id The id of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @Override
    public JobDescriptionDTO getJobsDescriptionById(UUID id) throws JobDescriptionNotFoundException {
        log.info("Job Description from the database by giving an ID");
        return jobDescriptionRepository.findById(id).map(JobDescriptionMapper::convertAllJobDescriptionEntityToDTO).orElseThrow(()-> new JobDescriptionNotFoundException(String.format("The JobDescription was not found with ID: %s", id)));
    }

    /**
     * Retrieve all Job Description
     * @return A list of JobDescriptionDTO
     */
    @Override
    public List<JobDescriptionDTO> getAllJobs() {
        log.info("Getting all Job Descriptions from the database");
        return jobDescriptionRepository.findAll().stream().map(JobDescriptionMapper::convertAllJobDescriptionEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Job Description by programming language
     * @param programmingLanguage The programming language of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @Override
    public List<JobDescriptionDTO> getJobsByProgrammingLanguage(String programmingLanguage) {
        log.info("Getting all Job Descriptions from the database by giving a programming language");
        return jobDescriptionRepository.findByProgrammingLanguage(programmingLanguage).stream().map(JobDescriptionMapper::convertAllJobDescriptionEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Job Description by framework
     * @param framework The framework of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    @Override
    public List<JobDescriptionDTO> getJobsByFramework(String framework) {
        log.info("Getting all Job Descriptions from the database by giving a framework");
        return jobDescriptionRepository.findByFramework(framework).stream().map(JobDescriptionMapper::convertAllJobDescriptionEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Job Description by location
     * @param location The location of the JobDescription
     * @return A list of JobDescriptionDTO
     */
    public List<JobDescriptionDTO> getJobsByLocation(String location) {
        return jobDescriptionRepository.findByLocation(location).stream().map(JobDescriptionMapper::convertAllJobDescriptionEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Insert a new JobDescription entity
     * @param jobDescription the job description object
     * @return A JobDescriptionDTO object
     */
    @Override
    public JobDescriptionDTO registerJobDescription(JobDescription jobDescription) {
        log.info("Saving new Job Description with ID: {}", jobDescription.getJobDescriptionsId());
        jobDescription = jobDescriptionRepository.save(jobDescription);
        return JobDescriptionMapper.convertAllJobDescriptionEntityToDTO(jobDescription);
    }

    /**
     * Retrieve Job Descriptions with Candidates by a given programming language
     * @param lang The programming language
     * @return A list of JobDescriptionCandidateDTO
     */
    public List<JobDescriptionCandidateDTO> getJobDescriptionWithCandidates(String lang) {
        return jobDescriptionRepository.findByLocationAndCandidates(lang).stream().map(JobDescriptionCandidateMapper::convertAllJobDescriptionCandidateEntityToDTO).collect(Collectors.toList());
    }

}
