package by.program.restAPI.service.impl;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.repository.TaskRepository;
import by.program.restAPI.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task add(Task task) {
        task.setCreated(Date.valueOf(LocalDate.now()));
        task.setUpdated(Date.valueOf(LocalDate.now()));
        task.setStatus(Status.ACTIVE);
        log.info("IN add - task: {} successfully add", task);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task update(Task task) {
        task.setUpdated(Date.valueOf(LocalDate.now()));
        log.info("IN update - task: {} successfully update", task);
        return task;
    }

    @Override
    public long getCountByStatus(Status status) {
        long count = taskRepository.countByStatus(status);
        log.info("IN getCountByStatus - count task: {} by status", count, status);
        return count;
    }

    @Override
    public long getCountEntities() {
        long count = taskRepository.count();
        log.info("IN getCountEntities - count task: {} ", count);
        return count;
    }

    @Override
    public Page<Task> getAllTaskByUser(User user, Pageable pageable) {
        log.info("IN getAllTaskByUser - get list task");
        Page<Task> tasks = taskRepository.findAllByUser(user, pageable);
        return tasks;
    }

    @Override
    public Page<Task> getAllTaskByUserAndStatusNot(User user, Pageable pageable, Status status) {
        log.info("IN getAllTaskByUserAndStatusNot - get list task");
        Page<Task> tasks = taskRepository.findAllByUserAndStatusNot(user, pageable, status);
        return tasks;
    }

    @Override
    public Page<Task> findAllByUserAndStatusAndStatusNot(User user, Pageable pageable, Status containsStatus, Status exceptStatus) {
        log.info("IN findAllByUserAndStatusAndStatusNot - get list task");
        Page<Task> tasks = taskRepository.findAllByUserAndStatusAndStatusNot(user, pageable, containsStatus, exceptStatus);
        return tasks;
    }

    @Override
    public Page<Task> findAllByUserAndStatusNotAndTitleContaining(User user, Pageable pageable, Status status, String title) {
        log.info("IN findAllByUserAndStatusAndStatusNotAndTitleContaining - get list task");
        Page<Task> tasks = taskRepository.findAllByUserAndStatusNotAndTitleContaining(user, pageable, status, title);
        return tasks;
    }

    @Override
    public Task getById(long id) {
        Task task = (Task) taskRepository.findById(id).orElse(null);
        log.info("IN getById - task with id: {} found - {}", id, task);
        return task;
    }

    @Override
    public Task getTaskByIdAndStatusNot(long id, Status status) {
        Task task = taskRepository.findByIdAndStatusNot(id, status).orElse(null);
        log.info("IN getTaskByIdAndStatusNot - task: {} with id: {} and status not: {} found", task, id, status);
        return task;
    }

    @Override
    public void deleteById(long id) {
        taskRepository.deleteById(id);
        log.info("IN delete - task with id : {} deleted", id);
    }
}
