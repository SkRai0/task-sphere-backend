package com.iamskrai.task_sphere.project;

import com.iamskrai.task_sphere.project.dto.ProjectCreateRequest;
import com.iamskrai.task_sphere.project.dto.ProjectResponse;
import com.iamskrai.task_sphere.project.dto.ProjectUpdateRequest;
import com.iamskrai.task_sphere.user.UserRepository;
import com.iamskrai.task_sphere.user.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponse createProject(ProjectCreateRequest request){
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

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
        Project project = projectRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Project Not Found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
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
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

}
