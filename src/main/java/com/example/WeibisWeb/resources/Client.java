package com.example.WeibisWeb.resources;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The entity class of the clients database table
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "client_id", updatable = false, nullable = false)
    private UUID clientId;
    @NotNull(message = "The company name cannot be null")
    @Size(min = 3, max = 64, message = "The company name cannot be less than 3 and greater than 64 characters")
    @Column(name = "company_name")
    private String companyName;
    @NotNull(message = "The sector cannot be null")
    @Size(min = 2, max = 64, message = "The sector cannot be less than 2 and greater than 64 characters")
    @Column(name = "sector")
    private String sector;
    @NotNull(message = "The city name cannot be null")
    @Size(min = 2, max = 64, message = "The city cannot be less than 2 and greater than 64 characters")
    @Column(name = "city")
    private String city;
    @NotNull(message = "The country name cannot be null")
    @Size(min = 2, max = 64, message = "The country cannot be less than 2 and greater than 64 characters")
    @Column(name = "country")
    private String country;
    @NotNull(message = "The size of the company cannot be null")
    @Min(1)
    @Max(20000)
    @Column(name = "company_size")
    private Integer size;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "jb_fk", referencedColumnName = "client_id")
    private List<JobDescription> jobDescriptions;


    public Client(UUID clientId, String companyName, String sector, String city, String country, Integer size) {
        this.clientId = clientId;
        this.companyName = companyName;
        this.sector = sector;
        this.city = city;
        this.country = country;
        this.size = size;
    }
}
