package by.program.restAPI.service.impl;

import by.program.restAPI.exception.NotFoundException;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.repository.TaskRepository;
import by.program.restAPI.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) {
        log.debug("Find task with id={}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id = " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        log.debug("Attempting to delete task with id = {}", id);
        taskRepository.deleteById(id);
        log.info("Task with id: {} successfully deleted", id);
    }

    @Override
    public Task add(Task task) {
        task.setCreated(LocalDate.now());
        task.setUpdated(LocalDate.now());
        task.setStatus(Status.ACTIVE);
        Task savedTask = taskRepository.save(task);
        log.info("Task: {} successfully added", task.getTitle());
        return savedTask;
    }

    @Override
    @Transactional
    public Task update(Long id, String title, String description, LocalDate startDate, LocalDate endDate, Status status, User user) {
        log.debug("Update task with id = {}", id);

        Task updatedTask = findById(id);

        updatedTask.setTitle(title);
        updatedTask.setDescription(description);
        updatedTask.setStartDate(startDate);
        updatedTask.setEndDate(endDate);
        updatedTask.setUpdated(LocalDate.now());
        updatedTask.setStatus(status);
        updatedTask.setUser(user);

        taskRepository.save(updatedTask);
        log.debug("Task with id = {} successfully updated", id);

        return updatedTask;
    }

    public Page<Task> getTasksForUser(Long userId, String search, Pageable pageable) {
        log.debug("Get tasks for user with id = {} by pageable", userId);
        return taskRepository.searchByUserIdAndTitleContainingIgnoreCase(
                userId, Status.DELETED, search.trim(), pageable);
    }

    @Override
    public long getCountByStatus(Status status) {
        log.info("Count tasks with status {}", status);
        return taskRepository.countByStatus(status);
    }
}
