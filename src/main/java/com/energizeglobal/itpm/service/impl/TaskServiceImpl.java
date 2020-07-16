package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.service.SprintService;
import com.energizeglobal.itpm.service.TaskService;
import com.energizeglobal.itpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;
    private final SprintService sprintService;
    @Override
    public TaskEntity toEntity(TaskDto taskDto) {
        final TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskDto.getId());
        taskEntity.setCreatorUserEntity(userService.findEntityById(taskDto.getCreatorId()));
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setName(taskDto.getName());
        taskEntity.setSprintEntity(sprintService.findEntityById(taskDto.getSpringId()));
        taskEntity.setTaskType(taskDto.getTaskType());
        return taskEntity;
    }

    @Override
    public TaskDto toDto(TaskEntity taskEntity) {
        final TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setName(taskEntity.getName());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setSpringId(taskEntity.getSprintEntity().getId());
        taskDto.setCreatorId(taskEntity.getCreatorUserEntity().getId());
        taskDto.setTaskType(taskEntity.getTaskType());
        return taskDto;
    }
}
