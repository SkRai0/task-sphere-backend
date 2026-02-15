package com.iamskrai.task_sphere.project;

import com.iamskrai.task_sphere.exception.BadRequestException;
import com.iamskrai.task_sphere.exception.ResourceNotFoundException;
import com.iamskrai.task_sphere.project.dto.ProjectCreateRequest;
import com.iamskrai.task_sphere.project.dto.ProjectResponse;
import com.iamskrai.task_sphere.project.dto.ProjectUpdateRequest;
import com.iamskrai.task_sphere.project.entity.Project;
import com.iamskrai.task_sphere.user.UserRepository;
import com.iamskrai.task_sphere.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectResponse createProject(ProjectCreateRequest request){
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwnerId()));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(owner)
                .build();

        Project saved = projectRepository.save(project);

        return ProjectResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .ownerId(owner.getId())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return projectRepository.findByOwnerId(userId)
                .stream()
                .map(p -> ProjectResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .ownerId(p.getOwner().getId())
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request){
        if (!projectId.equals(request.getId())) {
            throw new BadRequestException("Project ID in path does not match ID in request body");
        }
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwnerId()));
            project.setOwner(owner);
        }
        project.setUpdatedAt(Instant.now());

        Project updatedProject = projectRepository.save(project);

        return ProjectResponse.builder()
                .id(updatedProject.getId())
                .name(updatedProject.getName())
                .description(updatedProject.getDescription())
                .ownerId(updatedProject.getOwner().getId())
                .createdAt(updatedProject.getCreatedAt())
                .updatedAt(updatedProject.getUpdatedAt())
                .build();

    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        projectRepository.delete(project);
    }

}
