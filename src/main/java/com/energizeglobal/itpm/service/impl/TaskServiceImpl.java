package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.repository.TaskRepository;
import com.energizeglobal.itpm.service.*;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);

    private final Mapper mapper;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final SprintService sprintService;
    private final ProjectService projectService;
    private final ProjectVersionService projectVersionService;

    public TaskServiceImpl(Mapper mapper, TaskRepository taskRepository,
                           @Lazy UserService userService, @Lazy SprintService sprintService,
                           @Lazy ProjectService projectService, @Lazy ProjectVersionService projectVersionService) {

        this.mapper = mapper;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.sprintService = sprintService;
        this.projectService = projectService;
        this.projectVersionService = projectVersionService;
    }

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
    public List<TaskDto> findAllBySprintAndState(Long sprintId, TaskState taskState) {
        final SprintEntity sprintEntity = sprintService.findEntityById(sprintId);

        return taskRepository.findAllBySprintAndState(sprintEntity, taskState)
                .stream()
                .map(taskEntity -> {
                    final TaskDto taskDto = new TaskDto();
                    mapper.map(taskEntity, taskDto);
                    return taskDto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addTaskToSprint(TaskDto taskDto, MultipartFile[] uploadedFiles) {
        taskDto.setId(null);
        log.trace("Adding task to Sprint: " + taskDto);
        final TaskEntity mappedTaskEntity = mapper.map(taskDto, new TaskEntity());
        final TaskEntity saved = taskRepository.save(mappedTaskEntity);
        log.trace("Task " + saved + "successfully attached to Sprint");
    }

    @Override
    @Transactional
    public void attachTaskToUser(Long taskId, String userId) {
        log.trace("attaching task: " + taskId + " to user: " + userId);
        final TaskEntity taskEntity = findEntityById(taskId);
        final UserEntity userEntity = userService.findEntityById(userId);
        taskEntity.setAssignedUserEntity(userEntity);
        taskRepository.save(taskEntity);
        log.trace("Task: " + taskId + " attached to user: " + userId);
    }

    @Override
    @Transactional
    public void changeTaskState(Long taskId, TaskState taskState) {
        final TaskEntity taskEntity = findEntityById(taskId);
        //TODO if (logged in user) != taskEntity.getAssignedUser throw Exception
        taskEntity.setTaskState(taskState);
        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public void changeTask(TaskDto taskDto) {
        log.trace("Changing task: " + taskDto.getId());
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        mapper.map(taskDto, taskEntity);
        taskRepository.save(taskEntity);
        log.trace("Task: " + taskEntity + " changed");
    }

    @Override
    @Transactional
    public void remove(Long taskId) {

        taskRepository.deleteById(taskId);
        log.trace("task: " + taskId + " deleted.");
    }

    @Override
    public List<TaskDto> findAllByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity) {
        return taskRepository.findAllByUserAndProject(userEntity, projectEntity)
                .stream()
                .map(entity -> mapper.map(entity, new TaskDto()))
                .collect(Collectors.toList());
    }

    @Override
    public String[] parseStringToArray(String str) {
        String parsed = str.replace("[", "");
        parsed = parsed.replace("]", "");
        return Arrays.stream(parsed.split(",")).toArray(String[]::new);
    }

    @Override
    @Transactional
    public void changeTaskPriority(TaskDto taskDto) {
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        taskEntity.setPriority(taskDto.getPriority());
        taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskDto> findAllSubTasks(Long taskId) {
        final TaskEntity parent = findEntityById(taskId);
        final List<TaskEntity> subTasks = taskRepository.findAllByParent(parent);
        return subTasks.stream()
                .map(task -> mapper.map(task, new TaskDto())).collect(Collectors.toList());

    }

    @Override
    public List<TaskDto> findAllByProjectId(String projectId) {
        final ProjectEntity projectEntity = projectService.findEntityById(projectId);
        final List<TaskEntity> taskEntities = taskRepository.findAllByProjectEntity(projectEntity);
        return taskEntities.stream()
                .map(taskEntity -> mapper.map(taskEntity, new TaskDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cloneTask(TaskDto taskDto) {
/**
 "creatorId": "dnum enq clone anox-i ID",
 "description": "string",

 "name": "string",

 "priority": "LOW",
 "projectId": "string",
 "projectVersionId": 0,
 "taskState": "TODO",
 "taskType": "TASK",
 */
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        final TaskEntity clone = new TaskEntity();

        clone.setCreatorUserEntity(userService.findEntityById(taskDto.getCreatorId()));
        clone.setDescription(taskEntity.getDescription());
        clone.setName(taskEntity.getName());
        clone.setPriority(taskEntity.getPriority());
        clone.setProjectEntity(projectService.findEntityById(taskDto.getProjectId()));
        clone.setProjectVersionEntity(projectVersionService.findEntityById(taskDto.getProjectVersionId()));
        clone.setTaskState(taskEntity.getTaskState());
        clone.setTaskType(taskEntity.getTaskType());
        taskRepository.save(clone);
    }


}
