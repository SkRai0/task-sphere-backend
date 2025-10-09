package com.iamskrai.task_sphere.task;

import com.iamskrai.task_sphere.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignedToId(Long userId);
}
