package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.*;
import com.energizeglobal.itpm.model.dto.*;
import com.energizeglobal.itpm.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class MapperImpl implements Mapper {
    private final UserService userService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final ProjectService projectService;

    public MapperImpl(@Lazy UserService userService,
                      @Lazy SprintService sprintService,
                      @Lazy TaskService taskService,
                      @Lazy ProjectService projectService) {
        this.userService = userService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    public UserEntity map(UserDto userDto, UserEntity userEntity) {
        userEntity.setId(userDto.getUserId());
        userEntity.setEmail(userDto.getEmail());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        userEntity.setIsActive(userDto.getIsActive());

        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }

    @Override
    public UserDto map(UserEntity userEntity, UserDto userDto) {
        userDto.setUserId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setIsActive(userEntity.getIsActive());
        return userDto;
    }

    @Override
    public CommentEntity map(CommentDto commentDto, CommentEntity commentEntity) {
        commentEntity.setId(commentDto.getId());
        commentEntity.setPublisherUserEntity(userService.findEntityById(commentDto.getId()));
        commentEntity.setTaskEntity(taskService.findEntityById(commentDto.getTaskId()));
        commentEntity.setText(commentDto.getText());
        commentEntity.setCreatedAt(commentDto.getCreatedAt());
        return commentEntity;
    }

    @Override
    public CommentDto map(CommentEntity commentEntity, CommentDto commentDto) {
        commentDto.setId(commentEntity.getId());
        commentDto.setPublisherId(commentEntity.getPublisherUserEntity().getId());
        commentDto.setTaskId(commentEntity.getTaskEntity().getId());
        commentDto.setText(commentEntity.getText());
        commentDto.setCreatedAt(commentEntity.getCreatedAt());
        return commentDto;
    }

    @Override
    public ProjectEntity map(ProjectDto projectDto, ProjectEntity projectEntity) {
        projectEntity.setId(projectDto.getId());
        projectEntity.setName(projectDto.getName());
        projectEntity.setDescription(projectDto.getDescription());
        return projectEntity;
    }

    @Override
    public ProjectDto map(ProjectEntity projectEntity, ProjectDto projectDto) {
        projectDto.setId(projectEntity.getId());
        projectDto.setName(projectEntity.getName());
        projectDto.setDescription(projectEntity.getDescription());
        projectDto.setCreatedAt(projectEntity.getCreatedAt());
        return projectDto;
    }

    @Override
    public SprintEntity map(SprintDto sprintDto, SprintEntity sprintEntity) {
        sprintEntity.setId(sprintDto.getId());
        sprintEntity.setProjectEntity(projectService.findEntityById(sprintDto.getProjectId()));
        sprintEntity.setCreatorUserEntity(userService.findEntityById(sprintDto.getCreatorId()));
        sprintEntity.setDeadLine(sprintDto.getDeadLine());
        return sprintEntity;
    }

    @Override
    public SprintDto map(SprintEntity source, SprintDto sprintDto) {
        sprintDto.setId(source.getId());
        sprintDto.setProjectId(source.getProjectEntity().getId());
        sprintDto.setCreatorId(source.getCreatorUserEntity().getId());
        sprintDto.setCreationTimestamp(source.getCreationTimestamp());
        sprintDto.setDeadLine(source.getDeadLine());
        return sprintDto;
    }

    @Override
    public TaskEntity map(TaskDto taskDto, TaskEntity taskEntity) {
        taskEntity.setId(taskDto.getId());
        taskEntity.setCreatorUserEntity(userService.findEntityById(taskDto.getCreatorId()));
        if (taskDto.getAssignedUserId() != null) {
            taskEntity.setAssignedUserEntity(userService.findEntityById(taskDto.getAssignedUserId()));
        }
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setName(taskDto.getName());
        taskEntity.setSprintEntity(sprintService.findEntityById(taskDto.getSpringId()));
        taskEntity.setTaskType(taskDto.getTaskType());
        return taskEntity;
    }

    @Override
    public TaskDto map(TaskEntity taskEntity, TaskDto taskDto) {
        taskDto.setId(taskEntity.getId());
        taskDto.setName(taskEntity.getName());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setSpringId(taskEntity.getSprintEntity().getId());
        taskDto.setCreatorId(taskEntity.getCreatorUserEntity().getId());
        if (taskEntity.getAssignedUserEntity() != null) {
            taskDto.setAssignedUserId(taskEntity.getAssignedUserEntity().getId());
        }
        taskDto.setTaskType(taskEntity.getTaskType());
        return taskDto;
    }

    @Override
    public UserProjectEntity map(UserProjectDto userProjectDto, UserProjectEntity userProjectEntity) {
        userProjectEntity.setId(userProjectDto.getId());
        userProjectEntity.setUserEntity(userService.findEntityById(userProjectDto.getUserId()));
        userProjectEntity.setProjectEntity(projectService.findEntityById(userProjectDto.getProjectId()));
        userProjectEntity.setRole(userProjectDto.getRole());
        return userProjectEntity;
    }

    @Override
    public UserProjectDto map(UserProjectEntity userProjectEntity, UserProjectDto userProjectDto) {
        userProjectDto.setId(userProjectEntity.getId());

        userProjectDto.setUserId(userProjectEntity.getUserEntity().getId());
        userProjectDto.setEmail(userProjectEntity.getUserEntity().getEmail());
        userProjectDto.setFirstName(userProjectEntity.getUserEntity().getFirstName());
        userProjectDto.setLastName(userProjectEntity.getUserEntity().getLastName());
        userProjectDto.setIsActive(userProjectEntity.getUserEntity().getIsActive());

        userProjectDto.setProjectId(userProjectEntity.getProjectEntity().getId());
        userProjectDto.setRole(userProjectEntity.getRole());
        return userProjectDto;
    }


}
