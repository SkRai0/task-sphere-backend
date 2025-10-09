package com.iamskrai.task_sphere.TaskComment;

import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentRequest;
import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentResponse;
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
    public ResponseEntity<TaskCommentResponse> addComment(@RequestBody TaskCommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskCommentResponse>> getComments(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }
}
