package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.*;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.repository.TaskRepository;
import com.energizeglobal.itpm.service.*;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        final TaskEntity clone = cloneProperties(taskDto);
        taskRepository.save(clone);
        cloneChildren(taskDto, clone);
    }

    @Transactional
    public void cloneChildren(TaskDto taskDto, TaskEntity newParent) {
        final List<TaskEntity> children = taskRepository
                .findAllByParent(findEntityById(taskDto.getId()));
        final ArrayList<TaskEntity> newChildren = new ArrayList<>();
        for (TaskEntity source : children) {
            final TaskEntity dest = new TaskEntity();
            dest.setCreatorUserEntity(newParent.getCreatorUserEntity());
            dest.setDescription(source.getDescription());
            dest.setName(source.getName());
            dest.setPriority(source.getPriority());
            dest.setProjectEntity(newParent.getProjectEntity());
            dest.setProjectVersionEntity(newParent.getProjectVersionEntity());
            dest.setTaskState(source.getTaskState());
            dest.setTaskType(source.getTaskType());
            dest.setParent(newParent);
            newChildren.add(dest);
        }

        taskRepository.saveAll(newChildren);
    }


    protected TaskEntity cloneProperties(TaskDto taskDto) {
        final TaskEntity source = findEntityById(taskDto.getId());
        final TaskEntity dest = new TaskEntity();
        dest.setCreatorUserEntity(userService.findEntityById(taskDto.getCreatorId()));
        dest.setDescription(source.getDescription());
        dest.setName(source.getName());
        dest.setPriority(source.getPriority());
        dest.setProjectEntity(projectService.findEntityById(taskDto.getProjectId()));
        dest.setProjectVersionEntity(projectVersionService.findEntityById(taskDto.getProjectVersionId()));
        dest.setTaskState(source.getTaskState());
        dest.setTaskType(source.getTaskType());
        return dest;
    }


    @Override
    @Transactional
    public void moveTaskToAnotherProject(TaskDto taskDto) {
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        final List<TaskEntity> children = taskRepository.findAllByParent(taskEntity);

        final ProjectEntity newProject = projectService.findEntityById(taskDto.getProjectId());

        final ProjectVersionEntity newProjectVersion = projectVersionService.findEntityById(taskDto.getProjectVersionId());

        taskEntity.setProjectEntity(newProject);
        taskEntity.setProjectVersionEntity(newProjectVersion);
        taskEntity.setAffectedProjectVersions("");
        taskEntity.setTriggeredBy(null);
        taskEntity.setTriggerType(null);

        for (TaskEntity child : children) {
            child.setProjectEntity(newProject);
            child.setProjectVersionEntity(newProjectVersion);
            child.setAffectedProjectVersions("");
            child.setTriggeredBy(null);
            child.setTriggerType(null);
        }

        taskRepository.save(taskEntity);
        taskRepository.saveAll(children);
    }

    @Override
    public List<TaskDto> findAllFreeTasksOfProject(String projectId, Sort sort) {
        final ProjectEntity projectEntity = projectService.findEntityById(projectId);
        return taskRepository.findAllByProjectEntityAndSprintEntityNull(projectEntity, sort)
                .stream()
                .map(taskEntity -> mapper.map(taskEntity, new TaskDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void attachTaskToSprint(TaskDto taskDto) {
        log.trace("attaching task:" + taskDto.getId() + " to sprint: " + taskDto.getSprintId());
        final TaskEntity taskEntity = findEntityById(taskDto.getId());
        final SprintEntity sprintEntity = sprintService.findEntityById(taskDto.getSprintId());
        taskEntity.setSprintEntity(sprintEntity);
        taskRepository.save(taskEntity);
    }

    @Override
    @Transactional
    public void detachTaskFromSprint(Long taskId) {
        final TaskEntity taskEntity = findEntityById(taskId);
        log.trace("detaching task: " + taskEntity.getId() + " from sprint: " + taskEntity.getSprintEntity().getId());
        taskEntity.setSprintEntity(null);
        taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskDto> findAllBySprintId(Long sprintId) {
        log.trace("Searching tasks in sprint: " + sprintId);
        final SprintEntity sprintEntity = sprintService.findEntityById(sprintId);
        return taskRepository.findAllBySprintEntity(sprintEntity).stream()
                .map(taskEntity -> mapper.map(taskEntity, new TaskDto())).collect(Collectors.toList());
    }

}
