package com.energizeglobal.itpm.api;

import com.energizeglobal.itpm.model.dto.CommentDto;
import com.energizeglobal.itpm.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger log = Logger.getLogger(CommentController.class);

    private final CommentService commentService;

    @GetMapping(value = "/comments/{commentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommentDto findById(@PathVariable("commentId") Long commentId) {
        log.trace("Searching comment by id: " + commentId);
        return commentService.findById(commentId);
    }

    @GetMapping(value = "{tasksId}/comments", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<CommentDto> findAllByTaskId(@PathVariable("tasksId") Long tasksId, @RequestParam Pageable pageable) {
        log.trace("Searching comments  by taskId: " + tasksId);
        return commentService.findAllByTaskId(tasksId, pageable);
    }


    @PostMapping(value = "/{taskId}/comments",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addComment(@PathVariable("taskId") Long taskId, @RequestBody CommentDto commentDto) {
        commentDto.setTaskId(taskId);
        log.trace("adding comment: " + commentDto);
        commentService.createComment(commentDto);
    }

    @PutMapping(value = "/{taskId}/comments",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateComment(@PathVariable("taskId") Long taskId, @RequestBody CommentDto commentDto) {
        commentDto.setTaskId(taskId);
        log.trace("updating comment: " + commentDto);
        commentService.updateComment(commentDto);
    }


    @DeleteMapping(value = "/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        log.trace("Removing comment: " + commentId);
        commentService.delete(commentId);
    }
}
