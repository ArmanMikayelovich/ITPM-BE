package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    @Column(updatable = false)
    private UserEntity publisherUserEntity;


    @ManyToOne
    @JoinColumn(name = "fk_task_id")
    @Column(updatable = false)
    private TaskEntity taskEntity;

    @Column(name = "text", length = 1500)
    private String text;

    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime modified;

}
