package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.repository.TaskRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.TaskService;
import com.energizeglobal.itpm.service.UserService;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Mapper mapper;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskDto findById(Long taskId) {
        final TaskEntity taskEntity = findEntityById(taskId);
        return mapper.map(taskEntity, new TaskDto());
    }

    @Override
    public TaskEntity findEntityById(Long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id: " + taskId + " not found."));
    }

    @Override
    public void addTaskToSprint(TaskDto taskDto) {
        taskDto.setId(null);
        final TaskEntity mappedTaskEntity = mapper.map(taskDto, new TaskEntity());
        taskRepository.save(mappedTaskEntity);
    }

    @Override
    @Transactional
    public void attachTaskToUser(Long taskId, Long userId) {
        final TaskEntity taskEntity = findEntityById(taskId);
        final UserEntity userEntity = userService.findEntityById(userId);
        taskEntity.setAssignedUserEntity(userEntity);
        taskRepository.save(taskEntity);
    }

    @Override
    public void changeTask(TaskDto taskDto) {
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        mapper.map(taskDto, taskEntity);
        taskRepository.save(taskEntity);
    }

    @Override
    public void remove(TaskDto taskDto) {
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        taskRepository.delete(taskEntity);
    }
}
