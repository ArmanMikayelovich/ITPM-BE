package com.energizeglobal.itpm.model.dto;

import com.energizeglobal.itpm.model.enums.TaskPriority;
import com.energizeglobal.itpm.model.enums.TaskState;
import com.energizeglobal.itpm.model.enums.TaskType;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;

    private String name;

    private String description;

    private Long springId;

    private String creatorId;

    private String assignedUserId;

    private TaskType taskType;

    private TaskState taskState;

    private String projectId;

    private TaskPriority priority;
}
