package com.energizeglobal.itpm.model;

import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "tasks")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    private TaskState taskState = TaskState.TODO;

    @Column(name = "description", length = 1500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_creator_user_id")
    private UserEntity creatorUserEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_assigned_user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity assignedUserEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_sprint_id")
    private SprintEntity sprintEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                taskType == that.taskType &&
                Objects.equals(description, that.description) &&

                (creatorUserEntity != null && that.creatorUserEntity != null) &&
                creatorUserEntity.getId().equals(that.creatorUserEntity.getId()) &&

                Objects.equals(assignedUserEntity, that.assignedUserEntity) &&
                Objects.equals(sprintEntity, that.sprintEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
