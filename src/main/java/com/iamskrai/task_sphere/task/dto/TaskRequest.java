package com.iamskrai.task_sphere.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotNull
    private String title;

    private String description;

    @NotNull
    private Long projectId;

    @NotNull
    private Long assignedToId;

    private LocalDateTime deadline;
}
