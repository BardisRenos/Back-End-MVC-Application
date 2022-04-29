package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.dtoMapper.ClientCandidateMapper;
import com.example.WeibisWeb.dtoMapper.ClientMapper;
import com.example.WeibisWeb.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The Service layer Client
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    /**
     * Retrieve all Clients
     * @return A list of ClientDTO
     */
    @Override
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll().stream().map(ClientMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve all clients with all job description
     * @return A list of ClientJobsDTOs
     */
    @Override
    public List<ClientJobsDTO> getAllClientWithDescription() {
        return clientRepository.findAll().stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Client by Id
     * @param id The id of the Client
     * @return A ClientDTO class
     */
    @Override
    public ClientDTO getClientById(UUID id) throws ClientNotFoundException {
        return clientRepository.findById(id).map(ClientMapper::convertAllClientEntityToDTO).orElseThrow(()-> new ClientNotFoundException(String.format("The Client was not found with ID: %s", id)));
    }

    /**
     * Retrieve Client by company name
     * @param companyName The company name of the Client
     * @return A ClientDTO class
     */
    @Override
    public List<ClientDTO> getClientByName(String companyName) {
        return clientRepository.findByCompanyName(companyName).stream().map(ClientMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by city
     * @param city The city of the Client
     * @return A list of ClientDTO class
     */
    @Override
    public List<ClientDTO> getClientsByCity(String city) {
        return clientRepository.findByCity(city).stream().map(ClientMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by a given company name with Job Description of that company
     * @param companyName The company name
     * @return A list of ClientJobsDTO
     */
    @Override
    public List<ClientJobsDTO> getClientsByNameWithJobs(String companyName) {
        return clientRepository.findByCompanyNameAndJobName(companyName).stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by a given location with Job Description of that company
     * @param companyName The company name
     * @param location The location
     * @return A list of ClientJobsDTOs
     */
    @Override
    public List<ClientJobsDTO> getClientsByNameAndLocation(String companyName, String location) {
        return clientRepository.findByCompanyNameAndLocation(companyName, location).stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by a given programming language with Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @Override
    public List<ClientJobsDTO> getClientsByNameAndProgrammingLanguage(String companyName, String programmingLanguage) {
        return clientRepository.findByCompanyNameAndProgrammingLanguage(companyName, programmingLanguage).stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by a given company name, programming language and the location of the Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @param location The location of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @Override
    public List<ClientJobsDTO> getClientsByNameAndProgrammingLanguageAndLocation(String companyName, String programmingLanguage, String location) {
        return clientRepository.findByCompanyNameAndProgrammingLanguageAndLocation(companyName, programmingLanguage, location).stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by a given company name and a status of the Job Description of that company
     * @param companyName The company name
     * @param status The status of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @Override
    public List<ClientJobsDTO> getClientsByNameAndStatus(String companyName, String status) {
        return clientRepository.findByCompanyNameAndStatus(companyName, status).stream().map(ClientCandidateMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }
}
