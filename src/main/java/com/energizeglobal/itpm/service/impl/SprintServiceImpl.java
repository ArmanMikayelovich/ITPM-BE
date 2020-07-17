package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.SprintEntity;
import com.energizeglobal.itpm.model.dto.SprintDto;
import com.energizeglobal.itpm.repository.SprintRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.SprintService;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final Mapper mapper;
    private final ProjectService projectService;

    @Override
    public void addSprintToProject(SprintDto sprintDto) {
        sprintDto.setId(null);
        final SprintEntity sprintEntity = new SprintEntity();
        mapper.map(sprintDto, sprintEntity);
        sprintRepository.save(sprintEntity);
    }

    @Override
    public Page<SprintDto> findAllSprintsByProjectId(String projectId, Pageable pageable) {
        return sprintRepository
                .findAllByProjectEntity(projectService.findEntityById(projectId), pageable)
                .map(sprintEntity -> mapper.map(sprintEntity, new SprintDto()));
    }

    @Override
    public void changeDeadLine(SprintDto sprintDto) {
        final SprintEntity sprintEntity = findEntityById(sprintDto.getId());
        sprintEntity.setDeadLine(sprintDto.getDeadLine());

        sprintRepository.save(sprintEntity);
    }


    @Override
    public SprintEntity findEntityById(Long sprintId) {
        return sprintRepository
                .findById(sprintId)
                .orElseThrow(() -> new NotFoundException("Sprint with id: " + sprintId + " not found."));
    }
}
