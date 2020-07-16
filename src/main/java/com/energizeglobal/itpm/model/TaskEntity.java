package com.energizeglobal.itpm.model;

import com.energizeglobal.itpm.model.enums.TaskType;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
@Data
@Entity
@Table(name = "tasks")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Column(name = "description", length = 1500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private UserEntity creatorUserEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_sprint_id")
    private SprintEntity sprintEntity;

}
