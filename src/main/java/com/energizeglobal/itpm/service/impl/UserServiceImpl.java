package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.UserProjectEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.dto.UserDto;
import com.energizeglobal.itpm.model.dto.UserProjectDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.repository.UserProjectRepository;
import com.energizeglobal.itpm.repository.UserRepository;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.ProjectService;
import com.energizeglobal.itpm.service.TaskService;
import com.energizeglobal.itpm.service.UserService;
import com.energizeglobal.itpm.util.exceptions.AlreadyExistsException;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import com.energizeglobal.itpm.util.exceptions.NotSupportedException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private static final String CREATED = "created";
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final UserProjectRepository userProjectRepository;
    private final TaskService taskService;

    public UserServiceImpl(Mapper mapper, UserRepository userRepository, @Lazy ProjectService projectService,
                           UserProjectRepository userProjectRepository, TaskService taskService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.userProjectRepository = userProjectRepository;
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        userDto.setUserId(null);
        log.trace("creating user: " + userDto);
        final UserEntity userEntity = new UserEntity();
        mapper.map(userDto, userEntity);

        final Optional<UserEntity> byEmail = userRepository
                .findByEmail(userEntity.getEmail());

        byEmail
                .ifPresent(u -> {
                    log.warn("User tries to register with existing email: " + u.getEmail());
                    throw new AlreadyExistsException("User with email" + u.getEmail() + " already exists");
                });

        userRepository.save(userEntity);
        log.trace("new User created: " + userEntity);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        log.trace("Updating user: " + userDto);

        final UserEntity userEntity = findEntityById(userDto.getUserId());

        if (!userDto.getEmail().equals(userEntity.getEmail())) {
            log.warn("User" + userDto.getUserId() + " tries to change his email.");
            throw new NotSupportedException("User can't change the email");
        }

        mapper.map(userDto, userEntity);
        final UserEntity saved = userRepository.save(userEntity);
        log.trace("User updated:" + saved);
    }

    @Override
    public UserDto findById(String userId) {
        log.trace("Searching user by id" + userId);
        return mapper.map(findEntityById(userId), new UserDto());
    }

    @Override
    public UserEntity findEntityById(String userId) {
        return userRepository.findById(userId)
                .orElseGet(() -> {
                    log.warn("User with id:" + userId + " not found.");
                    throw new NotFoundException("User with id: " + userId + " not found.");
                });
    }

    @Override
    @Transactional
    public void changeActivationStatus(String userId, Boolean status) {
        log.trace("Changing activation status of user: " + userId + " to :" + status);
        final UserEntity userEntity = findEntityById(userId);
        userEntity.setIsActive(status);
        userRepository.save(userEntity);
        log.trace("activation  status of user: " + userId + " changed to :" + status);
    }

    @Override
    public Page<UserProjectDto> findAllUsersByProject(String projectId, Pageable pageable) {
        log.trace("Searching All users in project. ");

        final ProjectEntity projectEntity = projectService.findEntityById(projectId);

        final Page<UserProjectEntity> userProjectEntities = userProjectRepository
                .findAllByProjectEntity(projectEntity, pageable);

        log.trace("Found " + userProjectEntities.getTotalElements() + " users by project Id: " + projectId);
        return userProjectEntities
                .map(entity -> mapper.map(entity, new UserProjectDto()));
    }

    @Override
    public List<UserProjectDto> findAllProjectsOfUser(String userId) {
        final UserEntity userEntity = findEntityById(userId);
        return userProjectRepository.findAllByUserEntity(userEntity, Pageable.unpaged())
                .map(entity -> mapper.map(entity, new UserProjectDto())).toList();
    }

    @Override
    public Map<String, List<TaskDto>> getUsersTasksInProject(String userId, String projectId) {
        final ProjectEntity projectEntity = projectService.findEntityById(projectId);
        final UserEntity userEntity = findEntityById(userId);
        final List<TaskDto> tasksByUserAndProject = taskService.findAllByUserAndProject(userEntity, projectEntity);
        final HashMap<String, List<TaskDto>> taskMap = new HashMap<>();

        taskMap.put(CREATED, new ArrayList<>());
        taskMap.put(TaskState.TODO.toString(), new ArrayList<>());
        taskMap.put(TaskState.IN_PROGRESS.toString(), new ArrayList<>());
        taskMap.put(TaskState.DONE.toString(), new ArrayList<>());

        sortTasksInMapByStateOrCreator(userId, tasksByUserAndProject, taskMap);

        return taskMap;
    }

    private void sortTasksInMapByStateOrCreator(String userId, List<TaskDto> tasksByUserAndProject,
                                                Map<String, List<TaskDto>> taskMap) {
        tasksByUserAndProject.forEach(taskDto -> {
            if (taskDto.getCreatorId().equals(userId)) {
                taskMap.get(CREATED).add(taskDto);
            }

            switch (taskDto.getTaskState()) {
                case TODO: {
                    taskMap.get(TaskState.TODO.toString()).add(taskDto);
                    break;
                }
                case IN_PROGRESS: {
                    taskMap.get(TaskState.IN_PROGRESS.toString()).add(taskDto);
                    break;
                }
                case DONE: {
                    taskMap.get(TaskState.DONE.toString()).add(taskDto);
                    break;
                }
                default:
                    throw new IllegalArgumentException(
                            "unexpected value in enum: TaskState. value is " + taskDto.getTaskState());
            }

        });
    }


}
