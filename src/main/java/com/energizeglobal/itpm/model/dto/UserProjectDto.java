package com.energizeglobal.itpm.model.dto;

import lombok.Data;

@Data
public class UserProjectDto {

    private Long id;

    private Long userId;

    private Long projectId;

    private String role;
}

