package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.dtoMapper.ClientCandidateMapper;
import com.example.WeibisWeb.dtoMapper.ClientMapper;
import com.example.WeibisWeb.exception.ClientNotFoundException;
import com.example.WeibisWeb.resources.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The Service layer Client
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Clients"})
@Profile(value = {"dev", "test"})
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    /**
     * Retrieve all Clients
     * @return A list of ClientDTO
     */
    @Override
    @Cacheable(value = "Clients")
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll().stream().map(ClientMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve all Clients by pagination
     * @param offset The offset of the data that we need to retrieve
     * @param pageSize The number of the records that will be retrieved on each offset
     * @return A Page<ClientDTO>
     */
    @Override
    public Page<ClientDTO> getAllClientsPagination(int offset, int pageSize) {
        log.info("Getting all Clients from the database by pagination with offset: {} and pageSize: {} variables", offset, pageSize);
        Page<Client> pageResponse = clientRepository.findAll(PageRequest.of(offset, pageSize));

        return pageResponse.map(ClientMapper::convertAllClientEntityToDTO);
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
     * Retrieve Client by id
     * @param id The id of the Client
     * @return A ClientDTO class
     */
    @Override
    @Cacheable(value = "Clients", key = "'Clients'+#id")
    public ClientDTO getClientById(UUID id) throws ClientNotFoundException {
        return clientRepository.findById(id).map(ClientMapper::convertAllClientEntityToDTO).orElseThrow(()-> new ClientNotFoundException(String.format("The Client was not found with ID: %s", id)));
    }

    /**
     * Retrieve Client by company name
     * @param companyName The company name of the Client
     * @return A ClientDTO class
     */
    @Override
    @Cacheable(value = "Clients", key = "'Clients'+#companyName")
    public List<ClientDTO> getClientByName(String companyName) {
        return clientRepository.findByCompanyName(companyName).stream().map(ClientMapper::convertAllClientEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve Clients by city
     * @param city The city of the Client
     * @return A list of ClientDTO class
     */
    @Override
    @Cacheable(value = "Clients", key = "'Clients'+#city")
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

    /**
     * Updating the Client with new data by giving the id of the client
     * @param clientDTO the clientDTO object
     * @param id The id of the Client
     * @return A ClientDTO object
     */
    @Override
    public ClientDTO getReplaceClient(ClientDTO clientDTO, UUID id) {
        Client clientEntity = ClientMapper.convertAllClientDTOtoEntity(clientDTO);

        return clientRepository.findById(id).map(
                client -> {
                    client.setClientId(clientEntity.getClientId());
                    client.setCompanyName(clientEntity.getCompanyName());
                    client.setSector(clientEntity.getSector());
                    client.setCity(clientEntity.getCity());
                    client.setCountry(clientEntity.getCountry());
                    client.setSize(clientEntity.getSize());
                    Client clientRes = clientRepository.save(client);
                    return ClientMapper.convertAllClientEntityToDTO(clientRes);
                }).orElseGet(()-> { clientDTO.setClientId(id);
                    Client client = ClientMapper.convertAllClientDTOtoEntity(clientDTO);
                    client = clientRepository.save(client);
                    return  ClientMapper.convertAllClientEntityToDTO(client);
                });
    }

    /**
     * Delete Client by giving an id number
     * @param id The id number of the Client
     * @return A String which indicates the entity id is deleted
     * @throws ClientNotFoundException The exception that throws
     */
    @Override
    public String deleteById(UUID id) throws ClientNotFoundException {
        Client clientRes = clientRepository.findById(id).filter(client -> client.getClientId().equals(id)).orElseThrow(()-> new ClientNotFoundException(String.format("The Client was not found with ID: %s", id)));
        if(Objects.nonNull(clientRes.getClientId())) {
            clientRepository.deleteById(clientRes.getClientId());
            return "The Client is deleted successfully";
        }
        return "Not deleted";
    }

}
