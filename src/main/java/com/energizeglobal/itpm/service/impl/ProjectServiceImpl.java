package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.ProjectDto;
import com.energizeglobal.itpm.repository.ProjectRepository;
import com.energizeglobal.itpm.repository.UserProjectRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.UserService;
import com.energizeglobal.itpm.util.exceptions.AlreadyExistsException;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Mapper mapper;
    private final UserProjectRepository userProjectRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void createProject(ProjectDto projectDto) {
        projectRepository.findById(projectDto.getId()).ifPresent(projectEntity -> {
            throw new AlreadyExistsException("Project with key: " + projectDto.getId() + " already exists.");
        });

        final ProjectEntity projectEntity = mapper.map(projectDto, new ProjectEntity());
        projectRepository.save(projectEntity);
    }

    @Override
    @Transactional
    public void updateProject(ProjectDto projectDto) {
        final ProjectEntity projectEntity = findEntityById(projectDto.getId());
        final ProjectEntity changedProjectEntity = mapper.map(projectDto, projectEntity);
        projectRepository.save(changedProjectEntity);
    }

    @Override
    @Transactional
    public void removeProject(String projectId) {
        final ProjectEntity entityById = findEntityById(projectId);
        projectRepository.delete(entityById);
    }

    @Override
    public ProjectDto findById(String projectId) {
        final ProjectEntity entity = findEntityById(projectId);

        return mapper.map(entity, new ProjectDto());
    }

    @Override
    public ProjectEntity findEntityById(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with key: " + projectId + " not found."));
    }

    @Override
    public Page<ProjectDto> findAllByUserId(Long userId, Pageable pageable) {
        final UserEntity userEntity = userService.findEntityById(userId);
        return userProjectRepository
                .findAllByUserEntity(userEntity, pageable)
                .map(entity -> mapper.map(entity.getProjectEntity(), new ProjectDto()));
    }

}
