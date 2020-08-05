package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    void createProject(ProjectDto projectDto);

    void updateProject(ProjectDto projectDto);

    void removeProject(String projectId);

    ProjectDto findById(String projectId);

    ProjectEntity findEntityById(String projectId);

    Page<ProjectDto> findAllByAssignedUserId(String userId, Pageable pageable);

}
