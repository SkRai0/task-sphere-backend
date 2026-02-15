package com.iamskrai.task_sphere.TaskComment;

import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentRequest;
import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentResponse;
import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService commentService;

    @PostMapping
    public ResponseEntity<TaskCommentResponse> addComment(@Valid @RequestBody TaskCommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskCommentResponse>> getComments(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskCommentResponse> updateComment(@PathVariable Long id, @Valid @RequestBody TaskCommentUpdateRequest request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
