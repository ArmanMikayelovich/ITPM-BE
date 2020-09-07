package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import com.energizeglobal.itpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    private final UserService userService;

    //EXAMPLE
// TODO DELETE AFTER INVESTIGATING
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@RequestBody UserDto userDto) {
        log.trace("creating user:" + userDto);
        userService.createUser(userDto);
        log.trace("user created:" + userDto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(@RequestBody UserDto userDto) {
        log.trace("updating user:" + userDto);
        userService.updateUser(userDto);
        log.trace("user updated:" + userDto);

    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDto findById(@PathVariable("userId") String userId) {
        log.trace("searching user by id: " + userId);
        return userService.findById(userId);
    }

    @PutMapping(value = "/activation")
    public void changeActivation(@RequestParam("userId") String userId,
                                 @RequestParam("status") Boolean status) {
        log.trace("changing activation status of user:" + userId + " to: " + status);
        userService.changeActivationStatus(userId, status);
    }

    @GetMapping(value = "/by-project/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<UserProjectDto> findAllByProjectId(@PathVariable("projectId") String projectId,
                                                   @RequestParam(required = false) final Pageable pageable) {
        log.trace("searching users by project id: " + projectId + " || pagination: " + pageable);
        return userService.findAllUsersByProject(projectId, pageable);
    }

    @GetMapping(value = "/{userId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserProjectDto> findAllProjectsOfUser(@PathVariable("userId") String userId) {
        return userService.findAllProjectsOfUser(userId);
    }

    @GetMapping(value = "/{userId}/projects/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<TaskDto>> getUserTasksInProject(@PathVariable("userId") String userId,
                                                            @PathVariable("projectId") String projectId) {
        return userService.getUsersTasksInProject(userId, projectId);
    }
}
