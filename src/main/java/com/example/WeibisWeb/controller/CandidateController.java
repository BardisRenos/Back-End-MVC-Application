package com.example.WeibisWeb.controller;

import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import com.example.WeibisWeb.service.CandidateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * The Controller layer of Candidate
 */
@RestController
@RequestMapping("/api/1.0/")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateServiceImpl candidateServiceImpl;

    /**
     * Retrieve the candidates by a given ID
     * @param id The id of the Candidate
     * @return A CandidateDTO class
     */
    @GetMapping(value = "/candidate")
    public CandidateDTO getCandidatesByCandidateId(@RequestParam(value = "id") UUID id) throws CandidateNotFoundException {
        return candidateServiceImpl.getCandidatesById(id);
    }

    /**
     * Retrieve the candidates
     * @return A list of CandidateDTOs
     */
    @GetMapping(value = "/candidates")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDTO> getAllCandidates() {
        return candidateServiceImpl.getAllCandidates();
    }

    /**
     * Insert a new Candidate entity
     * @param candidateDTO the candidate object
     * @return A candidateDTO object
     */
    @PostMapping("/candidate")
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateDTO saveCandidate(@Valid @RequestBody CandidateDTO candidateDTO) {
        return candidateServiceImpl.registerCandidate(candidateDTO);
    }

    /**
     * Retrieve the candidates by the nationality
     * @param lastName The last name of the Candidate
     * @return A list of CandidateDTOs
     */
    @GetMapping(value = "/candidates/lastName/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDTO> getCandidatesByLastName(@PathVariable(value = "lastName") String lastName) {
        return candidateServiceImpl.getCandidatesByLastName(lastName.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieve the candidates by the first name
     * @param firstName The first name of the Candidate
     * @return A list of CandidateDTOs
     */
    @GetMapping(value = "/candidates/firstName/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDTO> getCandidateByName(@PathVariable(value = "firstName") String firstName) {
        return candidateServiceImpl.getCandidateByName(firstName.toLowerCase(Locale.ROOT));
    }

    /**
     * Updating the candidate with new data by giving the id of the candidate
     * @param candidateDTO the candidate object
     * @param id The id of the Candidate
     * @return A CandidateDTO class
     */
    @PutMapping(value = "/candidate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidateDTO replaceCandidate(@RequestBody CandidateDTO candidateDTO, @PathVariable(value = "id") UUID id) {
        return candidateServiceImpl.getReplaceCandidate(candidateDTO, id);
    }

    /**
     * Delete the candidate by giving the id
     * @param id The id of the Candidate
     */
    @DeleteMapping(value = "/candidate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCandidate(@PathVariable(value = "id") UUID id) throws CandidateNotFoundException {
        return candidateServiceImpl.deleteById(id);
    }
}
