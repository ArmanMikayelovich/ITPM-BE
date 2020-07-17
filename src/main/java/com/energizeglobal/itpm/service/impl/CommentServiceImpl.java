package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.CommentEntity;
import com.energizeglobal.itpm.model.dto.CommentDto;
import com.energizeglobal.itpm.repository.CommentRepository;
import com.energizeglobal.itpm.service.CommentService;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Mapper mapper;
    private final CommentRepository commentRepository;


    @Override
    public void createComment(CommentDto commentDto) {
        commentDto.setId(null);
        final CommentEntity commentEntity = mapper.map(commentDto, new CommentEntity());
        commentRepository.save(commentEntity);
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        final CommentEntity commentEntity = findEntityById(commentDto.getId());
        mapper.map(commentDto, commentEntity);
        commentRepository.save(commentEntity);
    }

    @Override
    public void deleteDto(CommentDto commentDto) {
        final CommentEntity commentEntity = findEntityById(commentDto.getId());
        commentRepository.delete(commentEntity);
    }

    @Override
    public CommentDto findById(Long commentId) {
        return null;
    }

    @Override
    public CommentEntity findEntityById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id: " + commentId + " not found."));
    }

    @Override
    public Page<CommentDto> findAllByTaskId(Long taskId, Pageable pageable) {
        final CommentEntity commentEntity = findEntityById(taskId);
        return commentRepository
                .findAllByTaskEntity(commentEntity.getTaskEntity(), pageable)
                .map(c -> mapper.map(c, new CommentDto()));
    }

}
