package com.example.WeibisWeb.Service;

import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.dtoMapper.CandidateMapper;
import com.example.WeibisWeb.exception.CandidateNotFoundException;
import com.example.WeibisWeb.resources.Candidate;
import com.example.WeibisWeb.service.CandidateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * JUnit testing for the CandidateService layer
 */
@ContextConfiguration(classes = {CandidateServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("testServiceLayer")
class CandidateServiceImplTest {

    @MockBean
    private CandidateRepository candidateRepository;

    @Autowired
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

    @Test
    void deleteById_ShouldReturnString_ValidReturn() {
        Candidate candidate = Candidate.builder()
                .candidateId(UUID.randomUUID()).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        candidateRepository.deleteById(candidate.getCandidateId());

        verify(candidateRepository).deleteById(candidate.getCandidateId());
    }

    @Test
    void getReplaceCandidate_ShouldUpdateCandidate_ValidReturn() {
        UUID id = UUID.randomUUID();

        Candidate candidate1 = Candidate.builder()
                .candidateId(id).name("NewRenos")
                .lastName("NewBardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        Candidate candidate2 = Candidate.builder()
                .candidateId(id).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        CandidateDTO candidateDTO = CandidateDTO.builder()
                .candidateId(id).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        when(this.candidateRepository.save(any())).thenReturn(candidate1);
        when(this.candidateRepository.findByCandidateId(any())).thenReturn(Optional.of(candidate2));

        CandidateDTO actualReplaceCandidate = this.candidateServiceImpl.getReplaceCandidate(candidateDTO, id);

        assertAll("Should return attributes of the CandidateDTO object",
                ()->assertEquals("Antibes", actualReplaceCandidate.getCity()),
                ()->assertEquals("France", actualReplaceCandidate.getCountry()),
                ()->assertEquals("NewRenos", actualReplaceCandidate.getName()),
                ()->assertEquals("NewBardis", actualReplaceCandidate.getLastName()));

        verify(this.candidateRepository).save(any());
        verify(this.candidateRepository).findByCandidateId(any());
    }
}
