package com.iamskrai.task_sphere.TaskComment;

import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentRequest;
import com.iamskrai.task_sphere.TaskComment.dto.TaskCommentResponse;
import com.iamskrai.task_sphere.task.TaskRepository;
import com.iamskrai.task_sphere.task.entity.Task;
import com.iamskrai.task_sphere.user.UserRepository;
import com.iamskrai.task_sphere.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskCommentService {

    private final TaskCommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskCommentResponse addComment(TaskCommentRequest request) {
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User author = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TaskComment comment = TaskComment.builder()
                .content(request.getContent())
                .task(task)
                .author(author)
                .build();

        commentRepository.save(comment);

        return mapToResponse(comment);
    }

    public List<TaskCommentResponse> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskCommentResponse mapToResponse(TaskComment comment) {
        return TaskCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getAuthor().getName())
                .taskId(comment.getTask().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
