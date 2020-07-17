package com.energizeglobal.itpm.model.dto;

import com.energizeglobal.itpm.model.enums.TaskType;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;

    private String name;

    private String description;

    private Long springId;

    private Long creatorId;

    private Long assignedUserId;

    private TaskType taskType;
}
