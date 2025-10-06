package com.iamskrai.task_sphere.project.dto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private Instant createdAt;
    private Instant updatedAt;
}
