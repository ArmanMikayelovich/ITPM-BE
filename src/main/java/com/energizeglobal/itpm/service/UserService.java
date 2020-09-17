package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    UserDto findById(String userId);

    UserEntity findEntityById(String userId);

    void changeActivationStatus(String userId, Boolean status);

    Page<UserProjectDto> findAllUsersByProject(String projectId, Pageable pageable);

    List<UserProjectDto> findAllProjectsOfUser(String userId);


    void sendMailNotificationOfComment(String userId, Long taskId);

    UserEntity findByEmail(String email);
}
