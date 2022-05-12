package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.JobDescriptionCandidateDTO;
import com.example.WeibisWeb.resources.JobDescription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from JobDescription object into JobDescriptionCandidateDTO object.
 * It is a combination Job Description with Candidate entities.
 */
@Service
public class JobDescriptionCandidateMapper {

    /**
     * The conversion of the JobDescription object into JobDescriptionCandidateDTO
     * @param jobDescriptionEntity A jobDescriptionEntity class
     * @return A JobDescriptionCandidateDTO class
     */
    public static JobDescriptionCandidateDTO convertAllJobDescriptionCandidateEntityToDTO(JobDescription jobDescriptionEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobDescriptionEntity, JobDescriptionCandidateDTO.class);
    }
}
