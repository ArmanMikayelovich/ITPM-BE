package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import com.energizeglobal.itpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void createUser(UserDto userDto) {
        log.trace("creating user:" + userDto);
        userService.createUser(userDto);
        log.trace("user created:" + userDto);

    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateUser(UserDto userDto) {
        log.trace("updating user:" + userDto);
        userService.updateUser(userDto);
        log.trace("user updated:" + userDto);

    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDto findById(@PathVariable("userId") Long userId) {
        log.trace("searching user by id: " + userId);
        return userService.findById(userId);
    }

    @PutMapping(value = "/activation")
    public void changeActivation(@RequestParam("userId") Long userId, @RequestParam("status") Boolean status) {
        log.trace("changing activation status of user:" + userId + " to: " + status);
        userService.changeActivationStatus(userId, status);
    }

    @GetMapping(value = "/by-project/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Page<UserProjectDto> findAllByProjectId(@PathVariable("projectId") String projectId, @RequestParam final Pageable pageable) {
        log.trace("searching users by project id: " + projectId + " || pagination: " + pageable);
        return userService.findAllUsersByProject(projectId, pageable);
    }
}
