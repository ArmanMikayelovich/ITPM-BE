package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void addTaskToSprint(TaskDto taskDto) {
        log.trace("adding task in sprint: " + taskDto);
        taskService.addTaskToSprint(taskDto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateTask(TaskDto taskDto) {
        log.trace("updating task: " + taskDto);
        taskService.changeTask(taskDto);
    }

    @PutMapping(value = "/attach", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void attachTaskToUser(@RequestParam("userId") Long userId,
                                 @RequestParam("taskId") Long taskId) {
        log.trace("attaching task: " + taskId + " to user: " + userId);
        taskService.attachTaskToUser(taskId, userId);
    }


    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable("taskId") Long taskId) {
        log.trace("removing task with id: " + taskId);
        taskService.remove(taskId);
    }

}
