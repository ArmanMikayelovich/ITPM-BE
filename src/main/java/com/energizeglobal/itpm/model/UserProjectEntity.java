package com.energizeglobal.itpm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_projects")
@Data
public class UserProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_project_id")
    private ProjectEntity projectEntity;

    @Column(name = "role")
    private String role;
}
