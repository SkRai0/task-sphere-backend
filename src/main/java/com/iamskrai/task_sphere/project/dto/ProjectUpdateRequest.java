package com.iamskrai.task_sphere.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectUpdateRequest {

    @NotBlank
    private Long id;

    private String name;

    private String description;

    private Long ownerId;

}
