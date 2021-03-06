package com.example.WeibisWeb.controller;

import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.exception.ClientNotFoundException;
import com.example.WeibisWeb.response.APIResponse;
import com.example.WeibisWeb.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * The Controller layer of Client
 */
@RestController
@RequestMapping("/api/1.0/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServiceImpl clientServiceImpl;

    /**
     * Retrieve all clients
     * @return A list of ClientDTOs
     */
    @GetMapping(value = "/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getAllClients() {
        return clientServiceImpl.getAllClient();
    }

    /**
     * Retrieve all Clients by pagination
     * @param offset The offset of the data that we need to retrieve
     * @param pageSize The number of the records that will be retrieved on each offset
     * @return A APIResponse<Page<ClientDTO>>
     */
    @GetMapping(value = "/clients/pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse<Page<ClientDTO>> getAllClientsPaginationSorting(@PathVariable int offset, @PathVariable int pageSize) {
        Page<ClientDTO> clientDTOPage = clientServiceImpl.getAllClientsPagination(offset, pageSize);

        return new APIResponse<>(clientDTOPage.getSize(), clientDTOPage);
    }

    /**
     * Retrieve all clients with all job description
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/allClients/allJobDescription")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientJobsDTO> getAllClientsWithAllDescription() {
        return clientServiceImpl.getAllClientWithDescription();
    }

    /**
     * Retrieve a client by a given ID
     * @param id The id of the Clients
     * @return A ClientDTO class
     */
    @GetMapping(value = "/client")
    @ResponseStatus(HttpStatus.OK)
    public Object getClientById(@RequestParam(value = "id") UUID id) throws ClientNotFoundException {
        return clientServiceImpl.getClientById(id);
    }

    /**
     * Retrieve the clients by a given company name
     * @param companyName The company name of the Clients
     * @return A list of ClientDTOs
     */
    @GetMapping(value = "/clients/companyName/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getClientsByCompanyName(@PathVariable(value = "companyName") String companyName) {
        return clientServiceImpl.getClientByName(companyName.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieve clients by a given city
     * @param city The city name of the Clients
     * @return A list of ClientDTOs
     */
    @GetMapping(value =  "/clients/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getClientsByCityLocation(@PathVariable(value = "city") String city) {
        return clientServiceImpl.getClientsByCity(city.toLowerCase());
    }

    /**
     * Retrieve Clients by a given company name with Job Description of that company
     * @param companyName The company name
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/clients/jobsByCompanyName/{jobsByCompanyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientJobsDTO> getClientsByCompanyNameAndJobs(@PathVariable(value = "jobsByCompanyName") String companyName) {
        return clientServiceImpl.getClientsByNameWithJobs(companyName.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieve Clients by a given location with Job Description of that company
     * @param companyName The company name
     * @param location The location
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/clients/jobsByCompanyName/{jobsByCompanyName}/location/{location}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientJobsDTO> getClientsByCompanyNameAndLocation(@PathVariable(value = "jobsByCompanyName") String companyName, @PathVariable(value = "location") String location) {
        return clientServiceImpl.getClientsByNameAndLocation(companyName.toLowerCase(Locale.ROOT), location.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieve Clients by a given programming language with Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/clients/jobsByCompanyName/{jobsByCompanyName}/programmingLanguage/{programmingLanguage}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientJobsDTO>> getClientsByCompanyNameAndProgrammingLanguage(@PathVariable(value = "jobsByCompanyName") String companyName, @PathVariable(value = "programmingLanguage") String programmingLanguage) {
        return ResponseEntity.ok(clientServiceImpl.getClientsByNameAndProgrammingLanguage(companyName.toLowerCase(Locale.ROOT), programmingLanguage.toLowerCase(Locale.ROOT)));
    }

    /**
     * Retrieve Clients by a given company name, programming language and the location of the Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @param location The location of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/clients/jobsByCompanyName/{jobsByCompanyName}/programmingLanguage/{programmingLanguage}/location/{location}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientJobsDTO> getClientsByCompanyNameAndProgrammingLanguageAndLocation(@PathVariable(value = "jobsByCompanyName") String companyName,
                                                                                        @PathVariable(value = "programmingLanguage") String programmingLanguage,
                                                                                        @PathVariable(value = "location") String location) {
        return clientServiceImpl.getClientsByNameAndProgrammingLanguageAndLocation(companyName.toLowerCase(Locale.ROOT), programmingLanguage.toLowerCase(Locale.ROOT), location.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieve Clients by a given company name and a status of the Job Description of that company
     * @param companyName The company name
     * @param status The status of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @GetMapping(value = "/clients/jobsByCompanyName/{jobsByCompanyName}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientJobsDTO> getClientsByCompanyNameAndStatus(@PathVariable(value = "jobsByCompanyName") String companyName, @PathVariable(value = "status") String status) {
        return clientServiceImpl.getClientsByNameAndStatus(companyName.toLowerCase(Locale.ROOT), status.toLowerCase(Locale.ROOT));
    }

    /**
     * Replace the Client with a new data by giving the id of the Client
     * @param clientDTO  The clientDTO object
     * @param id The id number of the Client
     * @return A ClientDTO class
     */
    @PutMapping(value = "/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO replaceClient(@RequestBody ClientDTO clientDTO, @PathVariable(value = "id") UUID id) {
        return clientServiceImpl.getReplaceClient(clientDTO, id);
    }

    /**
     * Delete the Client by the id number
     * @param id The id number of the Client
     * @return A string which indicates the entity id is deleted
     * @throws ClientNotFoundException The exception that throws
     */
    @DeleteMapping(value = "/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteClient(@PathVariable(value = "id") UUID id) throws ClientNotFoundException {
        return clientServiceImpl.deleteById(id);
    }
}
