package com.example.WeibisWeb.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * The entity class of the candidates' database table
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates")
public class Candidate implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "candidate_id", updatable = false, nullable = false)
    private UUID candidateId;
    @NotNull(message = "The name cannot be null")
    @Size(min = 4, max = 32, message = "The name cannot be less than 4 and greater than 32 characters")
    @Column(name = "name")
    private String name;
    @NotNull(message = "The last name cannot be null")
    @Size(min = 5, max = 64, message = "The name cannot be less than 5 and greater than 64 characters")
    @Column(name = "last_name")
    private String lastName;
    @NotNull(message = "The date of birth cannot be null")
    @Size(min = 4, max = 24, message = "The name cannot be less than 4 and greater than 24 characters")
    @Column(name = "dob")
    private String dob;
    @NotNull(message = "The address cannot be null")
    @Size(min = 4, max = 256, message = "The address cannot be less than 4 and greater than 256 characters")
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<JobDescription> jobDescription;


    /**
     * Constructor of the Candidate
     * @param candidateId The candidate id
     * @param name The name of the candidate
     * @param lastName The last name of the candidate
     * @param dob The date of birth of the candidate
     * @param address The address of the candidate
     * @param city The city of the candidate
     * @param country The country of the candidate
     */
    public Candidate(UUID candidateId, String name, String lastName, String dob, String address, String city, String country) {
        this.candidateId = candidateId;
        this.name = name;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.country = country;
    }
}
