package com.energizeglobal.itpm.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SprintDto {

    private Long id;

    private String projectId;

    private String creatorId;

    private LocalDateTime creationTimestamp;

    private LocalDateTime deadLine;
}
