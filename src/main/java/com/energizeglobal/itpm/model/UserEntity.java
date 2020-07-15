package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Column(name = "registration_time",updatable = false)
    private LocalDate registrationDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;


    @OneToMany(mappedBy = "publisher")
    private List<ProjectEntity> items = new ArrayList<>();


    @Column(name = "is_active")
    private Boolean isActive = false;

    //TODO add roles lastly
}
