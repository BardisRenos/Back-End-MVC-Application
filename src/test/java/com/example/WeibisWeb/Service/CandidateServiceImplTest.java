package com.example.WeibisWeb.Service;

import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import com.example.WeibisWeb.resources.Candidate;
import com.example.WeibisWeb.service.CandidateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JUnit testing for the CandidateService layer
 */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CandidateServiceImplTest {

    @MockBean
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateServiceImpl;

    @BeforeEach
    void setUp() {
        this.candidateRepository = mock(CandidateRepository.class);
        this.candidateServiceImpl = new CandidateServiceImpl(this.candidateRepository);
    }

    @Test
    void findById_ShouldReturnAnObject_ValidReturn() throws CandidateNotFoundException {
        final UUID id = UUID.randomUUID();
        Candidate candidate1 = Candidate.builder()
                .candidateId(id).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        when(candidateRepository.findByCandidateId(id)).thenReturn(Optional.ofNullable(candidate1));

        CandidateDTO candidateDTO = candidateServiceImpl.getCandidatesById(id);
        assertAll("Should return attributes of the CandidateDTO object",
                ()->assertEquals("Antibes", candidateDTO.getCity()),
                ()->assertEquals("France", candidateDTO.getCountry()),
                ()->assertEquals("Renos", candidateDTO.getName()));
    }

    @Test
    void registerCandidate_ShouldReturnListObjects_ValidReturn() {
        CandidateDTO candidate1 = CandidateDTO.builder()
                .candidateId(UUID.randomUUID()).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        Candidate candidate = Candidate.builder()
                .candidateId(UUID.randomUUID()).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
        CandidateDTO candidateDTO = candidateServiceImpl.registerCandidate(candidate1);
        assertAll("Should return attributes of the CandidateDTO object",
                ()->assertEquals("Antibes", candidateDTO.getCity()),
                ()->assertEquals("France", candidateDTO.getCountry()),
                ()->assertEquals("Bardis", candidateDTO.getLastName()));
    }

}
