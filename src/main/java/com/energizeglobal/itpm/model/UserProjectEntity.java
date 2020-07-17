package com.energizeglobal.itpm.model;

import com.energizeglobal.itpm.model.enums.UserRole;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_projects")
@Data
@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class UserProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_project_id")
    private ProjectEntity projectEntity;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
