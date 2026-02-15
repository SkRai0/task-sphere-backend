package com.iamskrai.task_sphere.task;

import com.iamskrai.task_sphere.exception.ResourceNotFoundException;
import com.iamskrai.task_sphere.project.ProjectRepository;
import com.iamskrai.task_sphere.project.entity.Project;
import com.iamskrai.task_sphere.task.dto.TaskRequest;
import com.iamskrai.task_sphere.task.dto.TaskResponse;
import com.iamskrai.task_sphere.task.dto.TaskUpdateRequest;
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
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", dto.getProjectId()));

        User user = userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getAssignedToId()));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(TaskStatus.PENDING)
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
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getDeadline() != null) {
            task.setDeadline(request.getDeadline());
        }
        if (request.getAssignedToId() != null) {
            User user = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getAssignedToId()));
            task.setAssignedTo(user);
        }

        Task updatedTask = taskRepository.save(task);
        return mapToDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
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
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        var tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        var tasks = taskRepository.findByAssignedToId(userId);
        return tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
