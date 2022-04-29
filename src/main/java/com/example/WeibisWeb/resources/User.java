package com.example.WeibisWeb.resources;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The user entity la
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;
    @NotNull(message = "The first name cannot be null")
    @Column(name = "first_name")
    private String firstName;
    @NotNull(message = "The user name cannot be null")
    @Column(name = "user_name")
    private String username;
    @NotNull(message = "The last name cannot be null")
    @Column(name = "last_name")
    private String lastName;
    @NotNull(message = "The email cannot be null")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull(message = "The status cannot be null")
    @Column(name = "role")
    private String role;
    @NotNull(message = "The password cannot be null")
    @Column(name = "password", nullable = false)
    private String password;
}
