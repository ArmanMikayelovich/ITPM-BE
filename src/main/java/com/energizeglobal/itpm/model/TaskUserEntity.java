package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "tasks_users")
@Data
public class TaskUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private UserEntity assignedUserEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_task_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskEntity taskEntity;

}
