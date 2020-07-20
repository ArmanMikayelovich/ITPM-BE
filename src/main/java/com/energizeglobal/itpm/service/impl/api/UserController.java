package com.energizeglobal.itpm.service.impl.api;

import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import com.energizeglobal.itpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void createUser(UserDto userDto) {
        userService.createUser(userDto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateUser(UserDto userDto) {
        userService.updateUser(userDto);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDto findById(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @PutMapping(value = "/activation")
    public void changeActivation(@RequestParam("userId") Long userId, @RequestParam("status") Boolean status) {
        userService.changeActivationStatus(userId, status);
    }

    @GetMapping(value = "/by-project/{projectId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Page<UserProjectDto> findAllByProjectId(@PathVariable("projectId") String projectId, @RequestParam final Pageable pageable) {
        return userService.findAllUsersByProject(projectId, pageable);
    }
}
