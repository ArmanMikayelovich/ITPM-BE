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
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);

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
        final Optional<TaskEntity> byId = taskRepository
                .findById(taskId);
        String resultLog = byId
                .map(e -> "Task : " + taskId + " found.")
                .orElseGet(() -> "Task:" + taskId + " not found.");
        log.trace(resultLog);
        return byId
                .orElseThrow(() -> new NotFoundException("Task with id: " + taskId + " not found."));
    }

    @Override
    public void addTaskToSprint(TaskDto taskDto) {
        taskDto.setId(null);
        log.trace("Adding task to Sprint: " + taskDto);
        final TaskEntity mappedTaskEntity = mapper.map(taskDto, new TaskEntity());
        final TaskEntity saved = taskRepository.save(mappedTaskEntity);
        log.trace("Task " + saved + "successfully attached to Sprint");
    }

    @Override
    @Transactional
    public void attachTaskToUser(Long taskId, Long userId) {
        log.trace("attaching task: " + taskId + " to user: " + userId);
        final TaskEntity taskEntity = findEntityById(taskId);
        final UserEntity userEntity = userService.findEntityById(userId);
        taskEntity.setAssignedUserEntity(userEntity);
        taskRepository.save(taskEntity);
        log.trace("Task: " + taskId + " attached to user: " + userId);
    }

    @Override
    public void changeTask(TaskDto taskDto) {
        log.trace("Changing task: " + taskDto.getId());
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        mapper.map(taskDto, taskEntity);
        taskRepository.save(taskEntity);
        log.trace("Task: " + taskEntity + " changed");
    }

    @Override
    public void remove(Long taskId) {
        log.trace("removing task: " + taskId);
        final TaskEntity taskEntity = findEntityById(taskId);
        taskRepository.delete(taskEntity);
        log.trace("task: " + taskId + " deleted.");
    }
}
