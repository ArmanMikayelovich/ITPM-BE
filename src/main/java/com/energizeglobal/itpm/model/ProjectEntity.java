package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
public class ProjectEntity {
    @Id
    @Column(name = "id", unique = true, updatable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_publisher")
    private UserEntity publisher;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "projectEntity")
    private List<SprintEntity> sprintEntities = new ArrayList<>();
}
