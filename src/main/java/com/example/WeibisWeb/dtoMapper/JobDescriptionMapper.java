package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.resources.JobDescription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from JobDescription object into JobDescriptionDTO object
 */
@Service
public class JobDescriptionMapper {

    /**
     * The conversion of the JobDescription object into JobDescriptionDTO
     * @param jobDescriptionEntity A jobDescriptionEntity class
     * @return A JobDescriptionDTO class
     */
    public static JobDescriptionDTO convertAllJobDescriptionEntityToDTO(JobDescription jobDescriptionEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jobDescriptionEntity, JobDescriptionDTO.class);
    }
}
