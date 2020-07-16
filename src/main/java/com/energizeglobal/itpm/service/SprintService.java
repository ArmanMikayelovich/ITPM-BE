package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.dto.SprintDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SprintService {
    void addSprintToProject(SprintDto sprintDto);

    Page<SprintDto> findAllSprintsByProjectId(String projectId);

    void changeDeadLine(SprintDto sprintDto);

    SprintEntity toEntity(SprintDto sprintDto);

    SprintDto toDto(SprintEntity sprintEntity);

    SprintEntity findEntityById(Long sprintId);

}
