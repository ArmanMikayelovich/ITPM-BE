package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.dto.SprintDto;
import com.energizeglobal.itpm.repository.SprintRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.SprintService;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {
    private static final Logger log = Logger.getLogger(SprintServiceImpl.class);
    private final SprintRepository sprintRepository;
    private final Mapper mapper;
    private final ProjectService projectService;

    @Override
    @Transactional
    public void addSprintToProject(SprintDto sprintDto) {
        sprintDto.setId(null);
        log.trace("adding sprint to Project: " + sprintDto);
        final SprintEntity sprintEntity = new SprintEntity();
        mapper.map(sprintDto, sprintEntity);
        sprintRepository.save(sprintEntity);
        log.trace("Sprint attached to Project: " + sprintEntity);
    }

    @Override
    @Transactional
    public void changeDeadLine(SprintDto sprintDto) {
        final SprintEntity sprintEntity = findEntityById(sprintDto.getId());
        log.trace("Deadline of sprint: " + sprintEntity + " changing to: " + sprintEntity.getDeadLine());
        sprintEntity.setDeadLine(sprintDto.getDeadLine());

        sprintRepository.save(sprintEntity);
        log.trace("Deadline of sprint: " + sprintEntity + " changed to " + sprintDto.getDeadLine());
        sprintRepository.flush();
    }

    @Override
    public Page<SprintDto> findAllSprintsByProjectId(String projectId, Pageable pageable) {
        log.trace("Searching all sprints by projectId: " + projectId);
        final Page<SprintEntity> allByProjectEntity = sprintRepository
                .findAllByProjectEntity(projectService.findEntityById(projectId), pageable);
        log.trace("Sprints found: " + allByProjectEntity.getTotalElements());
        return allByProjectEntity
                .map(sprintEntity -> mapper.map(sprintEntity, new SprintDto()));
    }


    @Override
    public SprintEntity findEntityById(Long sprintId) {
        log.trace("Searching Sprint by id: " + sprintId);
        final Optional<SprintEntity> byId = sprintRepository
                .findById(sprintId);

        String resultLog = byId
                .map(e -> "sprint with id" + sprintId + " found.")
                .orElseGet(() -> "Sprint with id " + sprintId + " not found.");
        log.trace(resultLog);

        return byId
                .orElseThrow(() -> new NotFoundException("Sprint with id: " + sprintId + " not found."));
    }
}
