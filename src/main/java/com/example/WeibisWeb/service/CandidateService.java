package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * The Candidate Service Interface. All methods that Candidate service has.
 */
public interface CandidateService {

    CandidateDTO getCandidatesById(UUID id) throws CandidateNotFoundException;
    List<CandidateDTO> getAllCandidates();
    Page<CandidateDTO> getAllCandidatesPagination(int offset, int pageSize);
    CandidateDTO registerCandidate(CandidateDTO candidateDTO);
    List<CandidateDTO> getCandidatesByLastName(String lastName);
    List<CandidateDTO> getCandidateByName(String firstName);
    CandidateDTO getReplaceCandidate(CandidateDTO candidateDTO, UUID uuid) throws CandidateNotFoundException;
    Object deleteById(UUID id) throws CandidateNotFoundException;

}
