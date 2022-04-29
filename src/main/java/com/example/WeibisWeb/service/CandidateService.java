package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import com.example.WeibisWeb.resources.Candidate;

import java.util.List;
import java.util.UUID;

/**
 * The Candidate Service Interface. All methods that Candidate service has.
 */
public interface CandidateService {

    CandidateDTO getCandidatesById(UUID id) throws CandidateNotFoundException;
    List<CandidateDTO> getAllCandidates();
    CandidateDTO registerCandidate(CandidateDTO candidateDTO);
    List<CandidateDTO> getCandidatesByLastName(String lastName);
    List<CandidateDTO> getCandidateByName(String firstName);

}
