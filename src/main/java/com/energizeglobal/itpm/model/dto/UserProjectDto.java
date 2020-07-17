package com.energizeglobal.itpm.model.dto;

import com.energizeglobal.itpm.model.enums.UserRole;
import lombok.Data;

@Data
public class UserProjectDto {

    private Long id;

    private Long userId;

    private String projectId;

    private UserRole role;
}

