package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.UserEntity;
import com.energizeglobal.itpm.model.dto.TaskDto;
import com.energizeglobal.itpm.model.enums.TaskState;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TaskService {
    TaskDto findById(Long taskId);

    TaskEntity findEntityById(Long taskId);

    List<TaskDto> findAllBySprintAndState(Long sprintId, TaskState taskState);

    void addTaskToSprint(TaskDto taskDto, MultipartFile[] uploadedFiles);

    void attachTaskToUser(Long taskId, String userId);

    void changeTaskState(Long taskId, TaskState taskState);

    void changeTask(TaskDto taskDto);

    void remove(Long taskId);

    List<TaskDto> findAllByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity);

    void changeTaskPriority(TaskDto taskDto);

    String[] parseStringToArray(String str);

    List<TaskDto> findAllSubTasks(Long taskId);

    List<TaskDto> findAllByProjectId(String projectId);

    void cloneTask(TaskDto taskDto);

    void moveTaskToAnotherProject(TaskDto taskDto);


    List<TaskDto> findAllFreeTasksOfProject(String projectId, Sort sort);

    void attachTaskToSprint(TaskDto taskDto);

    void detachTaskFromSprint(Long taskId);

    List<TaskDto> findAllBySprintId(Long sprintId);
}
