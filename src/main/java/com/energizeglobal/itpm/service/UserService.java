package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    UserDto findById(Long userId);

    UserEntity findEntityById(Long userId);

    void changeActivationStatus(Long userId, Boolean status);

    Page<UserProjectDto> findAllUsersByProject(String projectId, Pageable pageable);


}
