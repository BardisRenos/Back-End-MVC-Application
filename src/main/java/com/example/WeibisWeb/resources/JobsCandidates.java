package com.example.WeibisWeb.resources;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * The entity class of the jobs_candidates database table
 */
@Entity
@Getter
@Setter
@Table(name = "jobs_candidates")
public class JobsCandidates {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "job_candidate_id", updatable = false, nullable = false)
    private UUID jobsCandidatesId;
    @NotNull
    @Column(name = "job_id")
    private Integer jobId;
    @NotNull
    @Column(name = "candidate_id")
    private Integer candidateId;
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "applying_date")
    private Date applyingDate;
}
