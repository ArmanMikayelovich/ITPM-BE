package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    UserDto findById(Long userId);

    void deActivate(Long userId);

    Page<UserDto> addAllUsersByProject(String projectId);


}
