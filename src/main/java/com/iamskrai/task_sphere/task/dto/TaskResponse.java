package com.iamskrai.task_sphere.task.dto;

import com.iamskrai.task_sphere.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime deadline;
    private Long projectId;
    private Long assignedToId;

}
