package by.program.restAPI.service;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface TaskService {

    Task findById(Long id);

    void delete(Long id);

    Task add(Task task);

    Task update(Long id, String title, String description, LocalDate startDate, LocalDate endDate, Status status, User user);

    Page<Task> getTasksForUser(Long userId, String search, Pageable pageable);


    //TODO

    long getCountByStatus(Status status);

    long getCountEntities();
}
