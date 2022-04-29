package com.example.WeibisWeb.Service;

import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.exception.ClientNotFoundException;
import com.example.WeibisWeb.resources.Client;
import com.example.WeibisWeb.resources.JobDescription;
import com.example.WeibisWeb.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientRepositoryImpl;


    @BeforeEach
    void setUp() {
        this.clientRepository = mock(ClientRepository.class);
        this.clientRepositoryImpl = new ClientServiceImpl(this.clientRepository);
    }

    @Test
    void getAllClient_ShouldReturnAnObject_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Alten").sector("IT")
                .city("PACA").country("France").size(1020).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("SII").sector("IT")
                .city("Lille").country("France").size(1110).build();

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findAll()).thenReturn(listOfClient);

        List<ClientDTO> clientDTOs = clientRepositoryImpl.getAllClient();

        assertAll("Should return attributes of the CandidateDTO object",
                ()->assertEquals(3, clientDTOs.size()),
                ()->assertEquals("France", clientDTOs.get(0).getCountry()),
                ()->assertEquals("France", clientDTOs.get(1).getCountry()),
                ()->assertEquals("France", clientDTOs.get(2).getCountry()));

    }

    @Test
    void getClientById_ShouldReturnAnObject_ValidReturn() throws ClientNotFoundException {
        final UUID id = UUID.randomUUID();
        Client client = Client.builder().clientId(id).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        when(clientRepository.findById(id)).thenReturn(Optional.ofNullable(client));

        ClientDTO clientDTO = clientRepositoryImpl.getClientById(id);
        assertAll("Should return attributes of the ClientDTO object",
                ()->assertEquals("Paris", clientDTO.getCity()),
                ()->assertEquals(1000, clientDTO.getSize()),
                ()->assertEquals("France", clientDTO.getCountry()),
                ()->assertEquals("Atos", clientDTO.getCompanyName()));
    }

    @Test
    void getClientByName_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Lille").country("France").size(1000).build();

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2));

        when(clientRepository.findByCompanyName("Atos")).thenReturn(listOfClient);

        List<ClientDTO> clientDTO = clientRepositoryImpl.getClientByName("Atos");
        assertAll("Should return attributes of the ClientDTO object",
                () -> assertEquals(2, clientDTO.size()),
                () -> assertEquals("Paris", clientDTO.get(0).getCity()),
                () -> assertEquals(1000, clientDTO.get(0).getSize()),
                () -> assertEquals("France", clientDTO.get(0).getCountry()),
                () -> assertEquals("Atos", clientDTO.get(0).getCompanyName()),
                () -> assertEquals("Lille", clientDTO.get(1).getCity()),
                () -> assertEquals(1000, clientDTO.get(1).getSize()),
                () -> assertEquals("France", clientDTO.get(1).getCountry()),
                () -> assertEquals("Atos", clientDTO.get(1).getCompanyName()));
    }

    @Test
    void getClientsByCity_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Alten").sector("IT")
                .city("Paris").country("France").size(1000).build();

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCity("Paris")).thenReturn(listOfClient);

        List<ClientDTO> clientDTO = clientRepositoryImpl.getClientsByCity("Paris");
        assertAll("Should return attributes of the ClientDTO object",
                ()-> assertEquals(3, clientDTO.size()),
                ()->assertEquals("Alten", clientDTO.get(2).getCompanyName()),
                ()->assertEquals("Atos", clientDTO.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientDTO.get(0).getCity()));
    }

    @Test
    void getClientsByNameWithJobs_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2).companyName("Atos")
                .title("Java Developer").location("Lille").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription3 = JobDescription.builder().jobDescriptionsId(id3).companyName("Atos")
                .title("Java Developer").location("Monaco").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("Postgres").framework("Spring").level("Junior").isOpen("Open")
                .numberNeeded(1).build();

        List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2, jobDescription3));

        client1.setJobDescriptions(jobDescriptions);
        client2.setJobDescriptions(jobDescriptions);
        client3.setJobDescriptions(jobDescriptions);

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCompanyNameAndJobName("Atos")).thenReturn(listOfClient);

        List<ClientJobsDTO> clientJobsDTOList = clientRepositoryImpl.getClientsByNameWithJobs("Atos");

        assertAll("Should return attributes of the ClientJobsDTO object",
                ()->assertEquals(3, clientJobsDTOList.size()),
                ()->assertEquals(3, clientJobsDTOList.get(0).getJobDescriptions().size()),
                ()->assertEquals("Atos", clientJobsDTOList.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientJobsDTOList.get(0).getJobDescriptions().get(0).getLocation()),
                ()->assertEquals("Lille", clientJobsDTOList.get(1).getJobDescriptions().get(1).getLocation()),
                ()->assertEquals("Monaco", clientJobsDTOList.get(2).getJobDescriptions().get(2).getLocation()));
    }

    @Test
    void getClientsByNameAndLocation_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription3 = JobDescription.builder().jobDescriptionsId(id3).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("Postgres").framework("Spring").level("Junior").isOpen("Open")
                .numberNeeded(1).build();

        List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2, jobDescription3));

        client1.setJobDescriptions(jobDescriptions);
        client2.setJobDescriptions(jobDescriptions);
        client3.setJobDescriptions(jobDescriptions);

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCompanyNameAndLocation("Atos","Paris")).thenReturn(listOfClient);
        List<ClientJobsDTO> clientJobsDTOList = clientRepositoryImpl.getClientsByNameAndLocation("Atos", "Paris");

        assertAll("Should return attributes of the ClientJobsDTO object",
                ()->assertEquals(3, clientJobsDTOList.size()),
                ()->assertEquals(3, clientJobsDTOList.get(0).getJobDescriptions().size()),
                ()->assertEquals("Atos", clientJobsDTOList.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientJobsDTOList.get(0).getJobDescriptions().get(0).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(1).getJobDescriptions().get(1).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(2).getJobDescriptions().get(2).getLocation()));
    }

    @Test
    void getClientsByNameAndProgrammingLanguage_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription3 = JobDescription.builder().jobDescriptionsId(id3).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("Postgres").framework("Spring").level("Junior").isOpen("Open")
                .numberNeeded(1).build();

        List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2, jobDescription3));

        client1.setJobDescriptions(jobDescriptions);
        client2.setJobDescriptions(jobDescriptions);
        client3.setJobDescriptions(jobDescriptions);

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCompanyNameAndProgrammingLanguage("Atos","Java")).thenReturn(listOfClient);
        List<ClientJobsDTO> clientJobsDTOList = clientRepositoryImpl.getClientsByNameAndProgrammingLanguage("Atos", "Java");

        assertAll("Should return attributes of the ClientJobsDTO object",
                ()->assertEquals(3, clientJobsDTOList.size()),
                ()->assertEquals(3, clientJobsDTOList.get(0).getJobDescriptions().size()),
                ()->assertEquals("Atos", clientJobsDTOList.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientJobsDTOList.get(0).getJobDescriptions().get(0).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(1).getJobDescriptions().get(1).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(2).getJobDescriptions().get(2).getLocation()));
    }

    @Test
    void getClientsByNameAndProgrammingLanguageAndLocation_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription3 = JobDescription.builder().jobDescriptionsId(id3).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("Postgres").framework("Spring").level("Junior").isOpen("Open")
                .numberNeeded(1).build();

        List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2, jobDescription3));

        client1.setJobDescriptions(jobDescriptions);
        client2.setJobDescriptions(jobDescriptions);
        client3.setJobDescriptions(jobDescriptions);

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCompanyNameAndProgrammingLanguageAndLocation("Atos","Java", "Paris")).thenReturn(listOfClient);
        List<ClientJobsDTO> clientJobsDTOList = clientRepositoryImpl.getClientsByNameAndProgrammingLanguageAndLocation("Atos", "Java", "Paris");

        assertAll("Should return attributes of the ClientJobsDTO object",
                ()->assertEquals(3, clientJobsDTOList.size()),
                ()->assertEquals(3, clientJobsDTOList.get(0).getJobDescriptions().size()),
                ()->assertEquals("Atos", clientJobsDTOList.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientJobsDTOList.get(0).getJobDescriptions().get(0).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(1).getJobDescriptions().get(1).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(2).getJobDescriptions().get(2).getLocation()));
    }

    @Test
    void getClientsByNameAndStatus_ShouldReturnAListObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        Client client1 = Client.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        Client client2 = Client.builder().clientId(id2).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id3 = UUID.randomUUID();
        Client client3 = Client.builder().clientId(id3).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        JobDescription jobDescription1 = JobDescription.builder().jobDescriptionsId(id1).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription2 = JobDescription.builder().jobDescriptionsId(id2).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("SQL").framework("Spring").level("Mid, Senior").isOpen("Open")
                .numberNeeded(1).build();

        JobDescription jobDescription3 = JobDescription.builder().jobDescriptionsId(id3).companyName("Atos")
                .title("Java Developer").location("Paris").description("Java EE with Spring Boot, Maven, JPA Data")
                .programmingLanguage("Java").databaseName("Postgres").framework("Spring").level("Junior").isOpen("Open")
                .numberNeeded(1).build();

        List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jobDescription1, jobDescription2, jobDescription3));

        client1.setJobDescriptions(jobDescriptions);
        client2.setJobDescriptions(jobDescriptions);
        client3.setJobDescriptions(jobDescriptions);

        List<Client> listOfClient = new ArrayList<>(Arrays.asList(client1, client2, client3));

        when(clientRepository.findByCompanyNameAndStatus("Atos","Open")).thenReturn(listOfClient);
        List<ClientJobsDTO> clientJobsDTOList = clientRepositoryImpl.getClientsByNameAndStatus("Atos", "Open");

        assertAll("Should return attributes of the ClientJobsDTO object",
                ()->assertEquals(3, clientJobsDTOList.size()),
                ()->assertEquals(3, clientJobsDTOList.get(0).getJobDescriptions().size()),
                ()->assertEquals("Atos", clientJobsDTOList.get(0).getCompanyName()),
                ()->assertEquals("Paris", clientJobsDTOList.get(0).getJobDescriptions().get(0).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(1).getJobDescriptions().get(1).getLocation()),
                ()->assertEquals("Paris", clientJobsDTOList.get(2).getJobDescriptions().get(2).getLocation()));
    }
}
