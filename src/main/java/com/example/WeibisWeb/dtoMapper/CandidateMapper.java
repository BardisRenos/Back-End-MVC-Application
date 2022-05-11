package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.resources.Candidate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from Candidate object into CandidateDTO object
 */
@Service
public class CandidateMapper {

    /**
     * The conversion of the Candidate object into CandidateDTO
     * @param candidateEntity candidateEntity class
     * @return CandidateDTO class
     */
    public static CandidateDTO convertAllCandidateEntityToDTO(Candidate candidateEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(candidateEntity, CandidateDTO.class);
    }

    /**
     * The conversion of the CandidateDTO object into Candidate
     * @param candidateDTO CandidateDTO class
     * @return Candidate class
     */
    public static Candidate convertAllCandidateDTOtoEntity(CandidateDTO candidateDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(candidateDTO, Candidate.class);
    }
}
