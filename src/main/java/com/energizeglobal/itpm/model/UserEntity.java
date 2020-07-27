package com.energizeglobal.itpm.model;

import com.energizeglobal.itpm.model.enums.AuthProvider;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlRootElement
@Entity
@Table(name = "users")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Column(name = "registration_time", updatable = false)
    private LocalDate registrationDate;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "publisher")
    private List<ProjectEntity> ownProjects = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = false;


    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    //TODO add roles lastly

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(registrationDate, that.registrationDate) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(ownProjects, that.ownProjects) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationDate=" + registrationDate +
                ", email='" + email + '\'' +
                ", ownProjects=" + ownProjects.stream().map(ProjectEntity::getId).collect(Collectors.toList()) +
                ", isActive=" + isActive +
                '}';
    }
}
