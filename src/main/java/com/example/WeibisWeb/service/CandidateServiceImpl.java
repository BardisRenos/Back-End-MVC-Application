package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.dtoMapper.CandidateMapper;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import com.example.WeibisWeb.resources.Candidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The Service layer Candidate
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    /**
     * Retrieve the Candidates by a given ID
     * @param id The id of the Candidate
     * @return A CandidateDTO object
     */
    @Override
    public CandidateDTO getCandidatesById(UUID id) throws CandidateNotFoundException {
        log.info("Getting a candidate from the database by giving an ID");
        return candidateRepository.findByCandidateId(id).map(CandidateMapper::convertAllCandidateEntityToDTO).orElseThrow(()-> new CandidateNotFoundException(String.format("The Candidate was not found with ID: %s", id)));
    }

    /**
     * Retrieve all Candidates by a given ID
     * @return A list of CandidatesDTOs
     */
    @Override
    public List<CandidateDTO> getAllCandidates() {
        log.info("Getting all candidates from the database");
        return candidateRepository.findAll().stream().map(CandidateMapper::convertAllCandidateEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Insert a new Candidate entity
     * @param candidateDTO the candidateDTO object
     * @return A CandidateDTO object
     */
    @Override
    public CandidateDTO registerCandidate(CandidateDTO candidateDTO) {
        log.info("Saving new candidate with last name: {} and first name: {} to the database", candidateDTO.getLastName(), candidateDTO.getName());
        Candidate candidate = CandidateMapper.convertAllCandidateDTOtoEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return CandidateMapper.convertAllCandidateEntityToDTO(candidate);
    }

    /**
     * Retrieve a list of Candidate entity
     * @param lastName the last name of the candidate object
     * @return A list of CandidatesDTOs
     */
    @Override
    public List<CandidateDTO> getCandidatesByLastName(String lastName) {
        log.info("Getting candidates by giving a last name");
        return candidateRepository.findByLastName(lastName).stream().map(CandidateMapper::convertAllCandidateEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve a list of Candidate Entity
     * @param firstName the first name of the candidate object
     * @return A list of CandidatesDTOs
     */
    @Override
    public List<CandidateDTO> getCandidateByName(String firstName) {
        log.info("Getting candidates by giving a first name");
        return candidateRepository.findByName(firstName).stream().map(CandidateMapper::convertAllCandidateEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Updating the candidate with new data by giving the id of the candidate
     * @param candidateDTO the candidateDTO object
     * @param id The id of the Candidate
     * @return A CandidateDTO object
     */
    @Override
    public CandidateDTO getReplaceCandidate(CandidateDTO candidateDTO, UUID id) {
        Candidate candidateEntity = CandidateMapper.convertAllCandidateDTOtoEntity(candidateDTO);

        return candidateRepository.findByCandidateId(id).map(
                candidate -> {
                    candidate.setName(candidateEntity.getName());
                    candidate.setLastName(candidateEntity.getLastName());
                    candidate.setDob(candidateEntity.getDob());
                    candidate.setAddress(candidateEntity.getAddress());
                    candidate.setCity(candidateEntity.getCity());
                    candidate.setCountry(candidateEntity.getCountry());
                    Candidate candidateEntityRes = candidateRepository.save(candidate);
                    return CandidateMapper.convertAllCandidateEntityToDTO(candidateEntityRes);
                }).orElseGet(()-> { candidateDTO.setCandidateId(id);
                    Candidate candidate = CandidateMapper.convertAllCandidateDTOtoEntity(candidateDTO);
                    candidate = candidateRepository.save(candidate);
                    return CandidateMapper.convertAllCandidateEntityToDTO(candidate);
                });
    }

    /**
     * Delete candidate by giving an id number
     * @param id The id of the Candidate
     * @return A String which indicates the entity id deleted
     * @throws CandidateNotFoundException
     */
    @Override
    public String deleteById(UUID id) throws CandidateNotFoundException {
        Candidate candidateRes = candidateRepository.findByCandidateId(id).filter(candidate -> candidate.getCandidateId().equals(id)).orElseThrow(()-> new CandidateNotFoundException(String.format("The Candidate was not found with ID: %s", id)));
        if(Objects.nonNull(candidateRes.getCandidateId())) {
            candidateRepository.deleteById(candidateRes.getCandidateId());
            return "The Candidate is deleted successfully";
        }
        return "Not deleted";
    }
}
