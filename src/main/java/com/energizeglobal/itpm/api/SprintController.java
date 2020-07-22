package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.SprintDto;
import com.energizeglobal.itpm.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sprints")
@RequiredArgsConstructor
public class SprintController {
    private static final Logger log = Logger.getLogger(SprintController.class);

    private final SprintService sprintService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addSprint(@RequestBody SprintDto sprintDto) {
        log.trace("creating Sprint: " + sprintDto);
        sprintService.addSprintToProject(sprintDto);
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void changeDeadline(@RequestBody SprintDto sprintDto) {
        log.trace("Changing deadline of sprint with id: " + sprintDto
                + " to " + sprintDto.getDeadLine());
        sprintService.changeDeadLine(sprintDto);
    }

    @GetMapping(value = "/by-project/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<SprintDto> findAllByProjectId(@PathVariable("projectId") String projectId,
                                              @RequestParam Pageable pageable) {
        log.trace("searching sprints by project id: " + projectId + " || pageable: " + pageable);
        return sprintService.findAllSprintsByProjectId(projectId, pageable);
    }
}
