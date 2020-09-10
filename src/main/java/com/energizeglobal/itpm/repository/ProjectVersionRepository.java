package com.energizeglobal.itpm.repository;

import com.energizeglobal.itpm.model.ProjectEntity;
import com.energizeglobal.itpm.model.ProjectVersionEntity;
import com.energizeglobal.itpm.model.enums.ProjectVersionStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectVersionRepository extends JpaRepository<ProjectVersionEntity, Long> {
    List<ProjectVersionEntity> findAllByProjectEntity(ProjectEntity projectEntity);

    List<ProjectVersionEntity> findAllByProjectEntityAndVersionStatus(ProjectEntity projectEntity,
                                                                      ProjectVersionStatus projectVersionStatus, Sort sort);
}
