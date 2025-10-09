package com.iamskrai.task_sphere.task;

import com.iamskrai.task_sphere.project.ProjectRepository;
import com.iamskrai.task_sphere.project.entity.Project;
import com.iamskrai.task_sphere.task.dto.TaskRequest;
import com.iamskrai.task_sphere.task.dto.TaskResponse;
import com.iamskrai.task_sphere.task.entity.Task;
import com.iamskrai.task_sphere.user.UserRepository;
import com.iamskrai.task_sphere.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status("PENDING")
                .deadline(dto.getDeadline())
                .project(project)
                .assignedTo(user)
                .build();

        taskRepository.save(task);

        return mapToDto(task);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskResponse mapToDto(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDeadline(),
                task.getProject().getId(),
                task.getAssignedTo().getId()
        );
    }

    public List<TaskResponse> getTasksByProject(Long projectId) {
        var tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByUser(Long userId) {
        var tasks = taskRepository.findByAssignedToId(userId);
        return tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
