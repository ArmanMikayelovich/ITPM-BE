package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private static final Logger log = Logger.getLogger(TaskController.class);

    private final TaskService taskService;

    @GetMapping(value = "/{taskId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TaskDto findById(@PathVariable("taskId") Long taskId) {
        log.trace("searching task by id: " + taskId);
        return taskService.findById(taskId);
    }

    @GetMapping(value = "/by-sprint/{sprintId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TaskDto> findBySprintIdAndState(@PathVariable("sprintId") Long sprintId,
                                                @RequestParam(required = false) TaskState taskState) {
        log.trace("searching all tasks by Sprint: " + sprintId + " and state: " + taskState);
        if (taskState != null) {
            return taskService.findAllBySprintAndState(sprintId, taskState);
        } else {
            return taskService.findAllBySprintId(sprintId);
        }


    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createTask(TaskDto taskDto, MultipartFile[] uploadedFiles) {
        log.trace("adding task in sprint: " + taskDto);
        taskService.addTaskToSprint(taskDto, uploadedFiles);
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateTask(@RequestBody TaskDto taskDto) {
        log.trace("updating task: " + taskDto);
        taskService.changeTask(taskDto);
    }

    @PutMapping(value = "change-priority", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeTaskPriority(@RequestBody TaskDto taskDto) {
        log.trace("changing priority of task: " + taskDto.getId() + " to : " + taskDto.getPriority());
        taskService.changeTaskPriority(taskDto);
    }

    @PutMapping(value = "/change-state", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeTaskState(@RequestBody TaskDto taskDto) {
        log.trace("changing state of task : " + taskDto.getId() + "to :" + taskDto.getTaskState());
        taskService.changeTaskState(taskDto.getId(), taskDto.getTaskState());
    }


    @PutMapping(value = "/attach", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void attachTaskToUser(@RequestBody TaskDto taskDto) {
        log.trace("attaching task: " + taskDto.getId() + " to user: " + taskDto.getAssignedUserId());
        taskService.attachTaskToUser(taskDto.getId(), taskDto.getAssignedUserId());
    }


    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable("taskId") Long taskId) {
        log.trace("removing task with id: " + taskId);
        taskService.remove(taskId);
    }

    @GetMapping("/{taskId}/sub-tasks")
    public List<TaskDto> getSubTasks(@PathVariable("taskId") Long taskId) {
        return taskService.findAllSubTasks(taskId);
    }

    @GetMapping(value = "/by-project/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> getTasksByProject(@PathVariable("projectId") String projectId) {
        log.trace("searching all tasks of project : " + projectId);
        return taskService.findAllByProjectId(projectId);
    }

    @PostMapping(value = "/clone", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cloneTaskToProject(@RequestBody TaskDto taskDto) {
        log.trace("cloning task: " + taskDto.getId() + " to project: " + taskDto.getProjectId());
        taskService.cloneTask(taskDto);
    }

    @PostMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void moveTaskToAnotherProject(TaskDto taskDto) {
        log.trace("moving task: " + taskDto.getId() + " to project: " + taskDto.getProjectId());
        taskService.moveTaskToAnotherProject(taskDto);
    }

    @GetMapping(value = "/by-project/{projectId}/free")
    public List<TaskDto> findAllFreeTasksOfProject(@PathVariable("projectId") String projectId,
                                                   @RequestParam(name = "sort", required = false) String sortProperty,
                                                   @RequestParam(required = false) String direction) {
        log.trace("searching all free tasks of project: " + projectId);

        if (sortProperty == null && direction == null) {
            return taskService.findAllFreeTasksOfProject(projectId, Sort.unsorted());
        }

        final Sort sort = Sort.by(Sort.Direction.fromString(direction), sortProperty);
        return taskService.findAllFreeTasksOfProject(projectId, sort);
    }

    @PutMapping(value = "/attach-to-sprint", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void attachTaskToSprint(@RequestBody TaskDto taskDto) {
        log.trace("attaching task: " + taskDto.getId() + "to sprint: " + taskDto.getSprintId());
        taskService.attachTaskToSprint(taskDto);
    }


    @PutMapping(value = "/detach-from-sprint/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void detachTaskFromSprint(@PathVariable("taskId") Long taskId) {
        log.trace("detaching task: " + taskId + "from sprint");
        taskService.detachTaskFromSprint(taskId);
    }
}
