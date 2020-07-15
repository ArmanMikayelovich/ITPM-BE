package com.energizeglobal.itpm.repository;

import com.energizeglobal.itpm.model.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Long> {
}
