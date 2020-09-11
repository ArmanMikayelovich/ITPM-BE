package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskDto findById(Long taskId);

    TaskEntity findEntityById(Long taskId);

    List<TaskDto> findAllBySprintAndState(Long sprintId, TaskState taskState);

    void addTaskToSprint(TaskDto taskDto);

    void attachTaskToUser(Long taskId, String userId);

    void changeTaskState(Long taskId, TaskState taskState);

    void changeTask(TaskDto taskDto);

    void remove(Long taskId);

    List<TaskDto> findAllByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity);

    void changeTaskPriority(TaskDto taskDto);

    String[] parseStringToArray(String str);

    List<TaskDto> findAllSubTasks(Long taskId);
}
