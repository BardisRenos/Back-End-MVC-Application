package com.example.WeibisWeb.dao;

import com.example.WeibisWeb.resources.JobDescription;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository layer of Job Description
 */
@Repository
@Profile(value = {"dev", "test"})
public interface JobDescriptionRepository extends JpaRepository<JobDescription, UUID> {

    /**
     * Retrieve the Job Description by a given ID
     * @param id The id number of a Job Description
     * @return A JobDescription Entity
     */
    Optional<JobDescription> findById(UUID id);

    /**
     * Retrieve the JobDescription by a given programming language
     * @param language the programming language that the job description is referred to
     * @return A list of JobDescriptions Entity
     */
    List<JobDescription> findByProgrammingLanguage(String language);

    /**
     * Retrieve the JobDescription by a given framework
     * @param framework The framework that the job description is referred to
     * @return A list of JobDescriptions Entity
     */
    @Query("select p from JobDescription p where lower(p.framework) like %:framework%")
    List<JobDescription> findByFramework(@Param("framework") String framework);

    /**
     * Retrieve the JobDescription by a given location (City or Country)
     * @param location The location that the job description is referred to
     * @return A list of JobDescriptions Entity
     */
    @Query("select p from JobDescription p where lower(p.location) like %:location%")
    List<JobDescription> findByLocation(@Param("location") String location);

    /**
     * Retrieve Job Description by given a programming language with the candidates that suits the programming language.
     * @param language The programming language
     * @return A list of JobDescriptions Entity
     */
    @Query("Select distinct p from JobDescription p join fetch p.candidates c where p.programmingLanguage like %:language%")
    List<JobDescription> findByLocationAndCandidates(@Param("language") String language);
}
