package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    void addTaskToSprint(TaskDto taskDto);

    void attachTaskToUser(Long taskId, Long userId);

    void changeTask(TaskDto taskDto);

    void remote(TaskDto taskDto);

    TaskEntity toEntity(TaskDto taskDto);

    TaskDto toDto(TaskEntity taskEntity);

}
