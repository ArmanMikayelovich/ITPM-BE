package com.energizeglobal.itpm.repository;

import com.energizeglobal.itpm.model.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {

}
