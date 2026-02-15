package com.iamskrai.task_sphere.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Assigned user ID is required")
    private Long assignedToId;

    private LocalDateTime deadline;
}
