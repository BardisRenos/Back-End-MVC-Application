package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.exception.ClientNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The Client Service Interface. All methods that Client service has.
 */
public interface ClientService {

    List<ClientDTO> getAllClient();
    List<ClientJobsDTO> getAllClientWithDescription();
    ClientDTO getClientById(UUID id) throws ClientNotFoundException;
    List<ClientDTO> getClientByName(String companyName);
    List<ClientDTO> getClientsByCity(String city);
    List<ClientJobsDTO> getClientsByNameWithJobs(String companyName);
    List<ClientJobsDTO> getClientsByNameAndLocation(String companyName, String location);
    List<ClientJobsDTO> getClientsByNameAndProgrammingLanguage(String companyName, String programmingLanguage);
    List<ClientJobsDTO> getClientsByNameAndProgrammingLanguageAndLocation(String companyName, String programmingLanguage, String location);
    List<ClientJobsDTO> getClientsByNameAndStatus(String companyName, String status);
    ClientDTO getReplaceClient(ClientDTO clientDTO, UUID id) throws ClientNotFoundException;
    String deleteById(UUID id) throws ClientNotFoundException;
}
