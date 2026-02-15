package com.iamskrai.task_sphere.task.dto;

import com.iamskrai.task_sphere.task.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskUpdateRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime deadline;
    private Long assignedToId;
}

