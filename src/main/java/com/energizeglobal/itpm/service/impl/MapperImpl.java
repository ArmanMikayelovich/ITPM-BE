package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.*;
import com.energizeglobal.itpm.model.dto.*;
import com.energizeglobal.itpm.service.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MapperImpl implements Mapper {
    @Override
    public UserEntity map(UserDto userDto, UserEntity userEntity) {
        return null;
    }

    @Override
    public UserDto map(UserEntity userEntity, UserDto userDto) {
        return null;
    }

    @Override
    public CommentEntity map(CommentDto commentDto, CommentEntity commentEntity) {
        return null;
    }

    @Override
    public CommentDto map(CommentEntity commentEntity, CommentDto commentDto) {
        return null;
    }

    @Override
    public ProjectEntity map(ProjectDto projectDto, ProjectEntity projectEntity) {
        return null;
    }

    @Override
    public ProjectDto map(ProjectEntity projectEntity, ProjectDto projectDto) {
        return null;
    }

    @Override
    public SprintEntity map(SprintDto sprintDto, SprintEntity sprintEntity) {
        return null;
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
        return null;
    }

    @Override
    public TaskDto map(TaskEntity taskEntity, TaskDto taskDto) {
        return null;
    }

    @Override
    public UserProjectEntity map(UserProjectDto userProjectDto, UserProjectEntity userProjectEntity) {
        return null;
    }

    @Override
    public UserProjectDto map(UserProjectEntity userProjectEntity, UserProjectDto userProjectDto) {
        return null;
    }

    public
}
