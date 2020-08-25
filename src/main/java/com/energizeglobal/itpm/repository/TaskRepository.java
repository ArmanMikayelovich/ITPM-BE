package com.energizeglobal.itpm.repository;

import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.enums.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllBySprintEntityAndTaskState(SprintEntity sprintEntity, TaskState taskState);

}
