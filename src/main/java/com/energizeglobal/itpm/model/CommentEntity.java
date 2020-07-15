package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private UserEntity publisherUserEntity;


    @ManyToOne
    @JoinColumn(name = "fk_task_id")
    private TaskEntity taskEntity;

    @Column(name = "text", length = 1500)
    private String text;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private LocalDateTime createdAt;

}
