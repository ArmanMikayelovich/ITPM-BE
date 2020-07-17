package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    void createProject(ProjectDto projectDto);

    void updateProject(ProjectDto projectDto);

    void removeProject(String projectId);

    ProjectDto findById(String projectId);

    ProjectEntity findEntityById(String projectId);

    Page<ProjectDto> findAllByUserId(Long userId);

    ProjectEntity toEntity(ProjectDto projectDto);

    ProjectDto toDto(ProjectEntity projectEntity);

}
