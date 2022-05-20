package com.example.WeibisWeb.dao;

import com.example.WeibisWeb.resources.Candidate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Repository layer of Candidate
 */
@Repository
@Profile(value = {"dev", "test"})
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    /**
     * Retrieve the candidates by a given ID
     * @param id The id of a Candidate
     * @return A Candidate Entity
     */
    Optional<Candidate> findByCandidateId(UUID id);

    /**
     * Retrieve the candidates by the last name
     * @param lastName The last name of a Candidate
     * @return A list of Candidate Entities
     */
    @Query("select p from Candidate p where lower(p.lastName) like %:lastName%")
    List<Candidate> findByLastName(@Param("lastName") String lastName);

    /**
     * Retrieve the candidates by the first name
     * @param firstName The first name of a Candidate
     * @return A list of Candidate Entities
     */
    @Query("select p from Candidate p where lower(p.name) like %:firstName%")
    List<Candidate> findByName(@Param("firstName") String firstName);
}
