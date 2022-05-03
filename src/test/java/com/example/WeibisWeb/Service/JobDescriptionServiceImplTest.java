package com.example.WeibisWeb.Service;

import com.example.WeibisWeb.dao.JobDescriptionRepository;
import com.example.WeibisWeb.dto.JobDescriptionCandidateDTO;
import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.exception.JobDescriptionNotFoundException;
import com.example.WeibisWeb.resources.Candidate;
import com.example.WeibisWeb.resources.JobDescription;
import com.example.WeibisWeb.service.ClientServiceImpl;
import com.example.WeibisWeb.service.JobDescriptionServiceImpl;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {JobDescriptionServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("testServiceLayer")
class JobDescriptionServiceImplTest {

    @MockBean
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private JobDescriptionServiceImpl jobDescriptionService;

    @BeforeEach
    void setUp() {
        this.jobDescriptionRepository = mock(JobDescriptionRepository.class);
        this.jobDescriptionService = new JobDescriptionServiceImpl(this.jobDescriptionRepository);
    }

    @Test
    void getJobsDescriptionById_ShouldReturnAnObject_ValidReturn() throws JobDescriptionNotFoundException {
        final UUID id = UUID.randomUUID();
        JobDescription jobDescription = JobDescription.builder().jobDescriptionsId(id)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        when(jobDescriptionRepository.findById(id)).thenReturn(Optional.ofNullable(jobDescription));
        JobDescriptionDTO jobDescriptionDTO = jobDescriptionService.getJobsDescriptionById(id);

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Paris", jobDescriptionDTO.getLocation()),
                ()->assertEquals("SQL", jobDescriptionDTO.getDatabaseName()),
                ()->assertEquals("Java", jobDescriptionDTO.getTitle()),
                ()->assertEquals("Atos", jobDescriptionDTO.getCompanyName()));
    }

    @Test
    void getAllJobs_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID id2 = UUID.randomUUID();
        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2)
                .companyName("Atos").title("Java").location("Lille").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Junior")
                .isOpen("Open").numberNeeded(2).build();

        List<JobDescription> listOfClient = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2));

        when(jobDescriptionRepository.findAll()).thenReturn(listOfClient);
        List<JobDescriptionDTO> jobDescriptionDTOList = jobDescriptionService.getAllJobs();

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionDTOList.get(0).getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionDTOList.get(1).getCompanyName()),
                ()->assertEquals("Java", jobDescriptionDTOList.get(0).getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionDTOList.get(0).getDatabaseName()));
    }

    @Test
    void getJobsByProgrammingLanguage_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID id2 = UUID.randomUUID();
        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2)
                .companyName("Atos").title("Java").location("Lille").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Junior")
                .isOpen("Open").numberNeeded(2).build();

        List<JobDescription> listOfClient = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2));

        when(jobDescriptionRepository.findByProgrammingLanguage("Java")).thenReturn(listOfClient);
        List<JobDescriptionDTO> jobDescriptionDTOList = jobDescriptionService.getJobsByProgrammingLanguage("Java");

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionDTOList.get(0).getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionDTOList.get(1).getCompanyName()),
                ()->assertEquals("Java", jobDescriptionDTOList.get(0).getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionDTOList.get(0).getDatabaseName()));
    }

    @Test
    void getJobsByFramework_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID id2 = UUID.randomUUID();
        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2)
                .companyName("Atos").title("Java").location("Lille").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Junior")
                .isOpen("Open").numberNeeded(2).build();

        List<JobDescription> listOfClient = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2));

        when(jobDescriptionRepository.findByFramework("Spring")).thenReturn(listOfClient);
        List<JobDescriptionDTO> jobDescriptionDTOList = jobDescriptionService.getJobsByFramework("Spring");

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionDTOList.get(0).getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionDTOList.get(1).getCompanyName()),
                ()->assertEquals("Java", jobDescriptionDTOList.get(0).getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionDTOList.get(0).getDatabaseName()));
    }

    @Test
    void getJobsByLocation_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID id2 = UUID.randomUUID();
        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2)
                .companyName("Atos").title("Java").location("Lille").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Junior")
                .isOpen("Open").numberNeeded(2).build();

        List<JobDescription> listOfClient = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2));

        when(jobDescriptionRepository.findByLocation("Paris")).thenReturn(listOfClient);
        List<JobDescriptionDTO> jobDescriptionDTOList = jobDescriptionService.getJobsByLocation("Paris");

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionDTOList.get(0).getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionDTOList.get(1).getCompanyName()),
                ()->assertEquals("Java", jobDescriptionDTOList.get(0).getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionDTOList.get(0).getDatabaseName()));
    }

    @Test
    void getJobDescriptionWithCandidates_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID id2 = UUID.randomUUID();
        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2)
                .companyName("Atos").title("Java").location("Lille").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Junior")
                .isOpen("Open").numberNeeded(2).build();

        final UUID idCandidate1 = UUID.randomUUID();
        Candidate candidate1 = Candidate.builder()
                .candidateId(idCandidate1).name("Renos")
                .lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        final UUID idCandidate2 = UUID.randomUUID();
        Candidate candidate2 = Candidate.builder()
                .candidateId(idCandidate2).name("Nikos")
                .lastName("papadopoulos").dob("15/10/1981").address("73 BD du President Jackson")
                .city("Nice").country("France").build();

        List<Candidate> candidateList = new ArrayList<>(Arrays.asList(candidate1, candidate2));
        jobDescription1.setCandidates(candidateList);
        jobDescription2.setCandidates(candidateList);

        List<JobDescription> jobDescriptionList = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2));

        when(jobDescriptionRepository.findByLocationAndCandidates("Java")).thenReturn(jobDescriptionList);

        List<JobDescriptionCandidateDTO> jobDescriptionCandidateDTOList = jobDescriptionService.getJobDescriptionWithCandidates("Java");

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionCandidateDTOList.get(0).getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionCandidateDTOList.get(1).getCompanyName()),
                ()->assertEquals("Java", jobDescriptionCandidateDTOList.get(0).getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionCandidateDTOList.get(0).getDatabaseName()),
                ()->assertEquals("Renos", jobDescriptionCandidateDTOList.get(0).getCandidates().get(0).getName()),
                ()->assertEquals("Bardis", jobDescriptionCandidateDTOList.get(0).getCandidates().get(0).getLastName()),
                ()->assertEquals("Nikos", jobDescriptionCandidateDTOList.get(1).getCandidates().get(1).getName()),
                ()->assertEquals("Nice", jobDescriptionCandidateDTOList.get(1).getCandidates().get(1).getCity()));

    }

    @Test
    void registerJobDescription_ShouldReturnAnObject_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1)
                .companyName("Atos").title("Java").location("Paris").description("Searching Java developer")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Senior")
                .isOpen("Open").numberNeeded(2).build();

        when(jobDescriptionRepository.save(jobDescription1)).thenReturn(jobDescription1);
        JobDescriptionDTO jobDescriptionDTO = jobDescriptionService.registerJobDescription(jobDescription1);

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertEquals("Atos", jobDescriptionDTO.getCompanyName()),
                ()->assertEquals("Atos", jobDescriptionDTO.getCompanyName()),
                ()->assertEquals("Java", jobDescriptionDTO.getProgrammingLanguage()),
                ()->assertEquals("SQL", jobDescriptionDTO.getDatabaseName()));
    }

}
