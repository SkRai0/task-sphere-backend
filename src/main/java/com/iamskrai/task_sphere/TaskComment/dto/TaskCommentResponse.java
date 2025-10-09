package com.iamskrai.task_sphere.TaskComment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskCommentResponse {

    private Long id;
    private String content;
    private String authorName;
    private Long taskId;
    private LocalDateTime createdAt;
}
