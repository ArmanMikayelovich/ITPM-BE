package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskDto findById(Long taskId);

    TaskEntity findEntityById(Long taskId);

    void addTaskToSprint(TaskDto taskDto);

    void attachTaskToUser(Long taskId, Long userId);

    void changeTask(TaskDto taskDto);

    void remote(TaskDto taskDto);

    TaskEntity toEntity(TaskDto taskDto);

    TaskDto toDto(TaskEntity taskEntity);

}
