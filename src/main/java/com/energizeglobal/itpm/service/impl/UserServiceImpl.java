package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import com.energizeglobal.itpm.repository.UserProjectRepository;
import com.energizeglobal.itpm.repository.UserRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.UserService;
import com.energizeglobal.itpm.util.exceptions.AlreadyExistsException;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final UserProjectRepository userProjectRepository;

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        final UserEntity userEntity = new UserEntity();
        mapper.map(userDto, userEntity);
        userEntity.setId(null);

        userRepository
                .findByEmail(userEntity.getEmail())
                .ifPresent(u -> {
                    throw new AlreadyExistsException("User with email" + u.getEmail() + " already exists");
                });

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        final UserEntity userEntity = findEntityById(userDto.getUserId());

        if (!userDto.getEmail().equals(userEntity.getEmail())) {
            throw new IllegalArgumentException("User can't change the email");
        }

        mapper.map(userDto, userEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto findById(Long userId) {
        return mapper.map(findEntityById(userId), new UserDto());
    }

    @Override
    public UserEntity findEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found."));
    }

    @Override
    @Transactional
    public void changeActivationStatus(Long userId, Boolean status) {
        final UserEntity userEntity = findEntityById(userId);
        userEntity.setIsActive(status);
        userRepository.save(userEntity);
    }

    @Override
    public Page<UserProjectDto> findAllUsersByProject(String projectId, Pageable pageable) {

        final ProjectEntity projectEntity = projectService.findEntityById(projectId);
        return userProjectRepository.findAllByProjectEntity(projectEntity, pageable)
                .map(entity -> mapper.map(entity, new UserProjectDto()));
    }


}
