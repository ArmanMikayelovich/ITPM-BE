package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.ProjectDto;
import com.energizeglobal.itpm.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private static final Logger log = Logger.getLogger(ProjectController.class);

    private final ProjectService projectService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,})
    public void createProject(@RequestBody ProjectDto projectDto) {
        log.trace("creating project: " + projectDto);
        projectService.createProject(projectDto);
        log.trace("project created : " + projectDto);

    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateProject(@RequestBody ProjectDto projectDto) {
        log.trace("updating project: " + projectDto);
        projectService.updateProject(projectDto);
        log.trace("project updated : " + projectDto);
    }

    @GetMapping(value = "/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ProjectDto findById(@PathVariable("projectId") String projectId) {
        log.trace("Searching project by id: " + projectId);
        final ProjectDto byId = projectService.findById(projectId);
        log.trace("Project with id: " + projectId + " found." + byId);
        return byId;
    }

    @DeleteMapping(value = "/{projectId}")
    public void delete(@PathVariable("projectId") String projectId) {
        projectService.removeProject(projectId);
        log.trace("project with id: " + projectId + " removed.");

    }

    @GetMapping(value = "/by-users/{userId}")
    public Page<ProjectDto> findAllByUser(@PathVariable("userId") Long userId, @RequestParam final Pageable pageable) {
        log.trace("searching projects by userId: " + userId + " || pageable: " + pageable);
        return projectService.findAllByAssignedUserId(userId, pageable);
    }
}
