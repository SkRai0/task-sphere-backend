package com.iamskrai.task_sphere.TaskComment.dto;

import lombok.Data;

@Data
public class TaskCommentRequest {

    private String content;
    private Long taskId;
    private Long userId;
}
