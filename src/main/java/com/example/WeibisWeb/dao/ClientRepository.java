package com.example.WeibisWeb.dao;

import com.example.WeibisWeb.resources.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Repository layer of Client
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    /**
     * Retrieve the client by a given ID
     * @param Id The id number of a Client
     * @return A Client Entity
     */
    Optional<Client> findById(UUID Id);

    /**
     * Retrieve Clients by a given name
     * @param companyName The name of the company
     * @return A List of Client Entity
     */
    @Query("select distinct p from Client p where lower(p.companyName) like %:companyName%")
    List<Client> findByCompanyName(String companyName);

    /**
     * Retrieve Clients by a given city
     * @param city The city of a Client
     * @return A List of Client Entity
     */
    @Query("select distinct p from Client p where lower(p.city) like %:city%")
    List<Client> findByCity(@Param("city") String city);

    /**
     * Retrieve Clients by a given company name with Job Description of that company
     * @param companyName The name of the
     * @return A list of Client Entity
     */
    @Query("select distinct p from Client p join fetch p.jobDescriptions c where lower(p.companyName) like %:companyName% and lower(c.companyName) like %:companyName%")
    List<Client> findByCompanyNameAndJobName(@Param("companyName") String companyName);

    /**
     * Retrieve Clients by a given location with Job Description of that company
     * @param companyName The company name
     * @param location The location
     * @return A list of ClientJobsDTOs
     */
    @Query("select distinct p from Client p join fetch p.jobDescriptions c where lower(p.companyName) like %:companyName% and lower(p.city) like %:location% and lower(c.companyName) like %:companyName% and lower(c.location) like %:location%")
    List<Client> findByCompanyNameAndLocation(@Param("companyName") String companyName, @Param("location") String location);

    /**
     * Retrieve Clients by a given programming language with Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @return A list of Clients
     */
    @Query("select distinct p from Client p join fetch p.jobDescriptions c where lower(p.companyName) like %:companyName% and lower(c.companyName) like %:companyName% and lower(c.programmingLanguage) like %:programmingLanguage%")
    List<Client> findByCompanyNameAndProgrammingLanguage(@Param("companyName") String companyName, @Param("programmingLanguage") String programmingLanguage);

    /**
     * Retrieve Clients by a given company name, programming language and the location of the Job Description of that company
     * @param companyName The company name
     * @param programmingLanguage The programming language of the Job Description entity
     * @param location The location of the Job Description entity
     * @return A list of ClientJobsDTOs
     */
    @Query("select distinct p from Client p join fetch p.jobDescriptions c where lower(p.companyName) like %:companyName% and lower(c.companyName) like %:companyName% and lower(c.programmingLanguage) like %:programmingLanguage%" +
            " and lower(p.city) like %:location% and lower(c.location) like %:location%")
    List<Client> findByCompanyNameAndProgrammingLanguageAndLocation(@Param("companyName") String companyName, @Param("programmingLanguage") String programmingLanguage, @Param("location") String location);

    /**
     * Retrieve Clients by a given company name and a status of the Job Description of that company
     * @param companyName The company name
     * @param status The status of the Job Description entity
     * @return A list of Clients
     */
    @Query("select distinct p from Client p join fetch p.jobDescriptions c where lower(p.companyName) like %:companyName% and lower(c.companyName) like %:companyName% and lower(c.isOpen) like %:status%")
    List<Client> findByCompanyNameAndStatus(@Param("companyName") String companyName, @Param("status") String status);
}
