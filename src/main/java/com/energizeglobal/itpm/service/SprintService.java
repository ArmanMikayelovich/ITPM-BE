package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.dto.SprintDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SprintService {
    void addSprintToProject(SprintDto sprintDto);


    Page<SprintDto> findAllSprintsByProjectId(String projectId, Pageable pageable);

    void changeDeadLine(SprintDto sprintDto);

    SprintEntity findEntityById(Long sprintId);

}
