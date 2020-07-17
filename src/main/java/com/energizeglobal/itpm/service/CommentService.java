package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.CommentEntity;
import com.energizeglobal.itpm.model.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    void createComment(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteDto(CommentDto commentDto);

    CommentDto findById(Long commentId);

    CommentEntity findEntityById(Long commentId);

    Page<CommentDto> findAllByTaskId(Long taskId, Pageable pageable);
}
