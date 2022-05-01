package com.example.WeibisWeb.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The entity class of the Job_descriptions database table
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_descriptions")
public class JobDescription implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "job_descriptions_id", updatable = false, nullable = false)
    private UUID jobDescriptionsId;
    @NotNull(message = "The company name cannot be null")
    @Size(min = 3, max = 64, message = "The company name cannot be less than 3 and greater than 64 characters")
    @Column(name = "company_name")
    private String companyName;
    @NotNull(message = "The title cannot be null")
    @Size(min = 4, max = 32, message = "The title cannot be less than 4 and greater than 32 characters")
    @Column(name = "title")
    private String title;
    @NotNull(message = "The location cannot be null")
    @Size(min = 2, max = 32, message = "The location cannot be less than 2 and greater than 32 characters")
    @Column(name = "location")
    private String location;
    @NotNull(message = "The description cannot be null")
    @Size(min = 15, max = 512, message = "The description cannot be less than 15 and greater than 500 characters")
    @Column(name = "description")
    private String description;
    @NotNull(message = "The programming language cannot be null")
    @Size(min = 1, max = 64, message = "The programming language cannot be less than 1 and greater than 64 characters")
    @Column(name = "programming_language")
    private String programmingLanguage;
    @Column(name = "database_name")
    private String databaseName;
    @Column(name = "framework")
    private String framework;
    @Column(name = "level")
    private String level;
    @NotNull(message = "The is open status cannot be null")
    @Column(name = "is_open")
    private String isOpen;
    @Column(name = "number_needed")
    private Integer numberNeeded;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "jobs_candidates",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    private List<Candidate> candidates;

    @SuppressWarnings("squid:S00107") // Too many parameters. Should be OK.
    public JobDescription(UUID jobDescriptionsId, String companyName, String title, String location, String description, String programmingLanguage, String databaseName, String framework, String level, String isOpen, Integer numberNeeded) {
        this.jobDescriptionsId = jobDescriptionsId;
        this.companyName = companyName;
        this.title = title;
        this.location = location;
        this.description = description;
        this.programmingLanguage = programmingLanguage;
        this.databaseName = databaseName;
        this.framework = framework;
        this.level = level;
        this.isOpen = isOpen;
        this.numberNeeded = numberNeeded;
    }
}
