package com.energizeglobal.itpm.repository;

import com.energizeglobal.itpm.model.FileInfoEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Long> {

    List<FileInfoEntity> findAllByOwnerTaskEntityAndIsDeletedFalse(TaskEntity taskEntity);

}
