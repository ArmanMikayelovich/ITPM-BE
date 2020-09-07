package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        log.trace("searching all tasks by Sprint: " + sprintId + " and state: " + taskState.toString());
        return taskService.findAllBySprintAndState(sprintId, taskState);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addTaskToSprint(@RequestBody TaskDto taskDto) {
        log.trace("adding task in sprint: " + taskDto);
        taskService.addTaskToSprint(taskDto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateTask(@RequestBody TaskDto taskDto) {
        log.trace("updating task: " + taskDto);
        taskService.changeTask(taskDto);
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

}
