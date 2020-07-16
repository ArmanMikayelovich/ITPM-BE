package com.energizeglobal.itpm.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;

    private Long publisherId;

    private Long taskId;

    private String text;

    private LocalDateTime createdAt;
}
