package by.program.restAPI.service;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {

    Task add(Task task);

    Task update(Task task);

    long getCountByStatus(Status status);

    long getCountEntities();

    Page<Task> getAllTaskByUser(User user, Pageable pageable);

    Page<Task> getAllTaskByUserAndStatusNot(User user, Pageable pageable, Status status);

    Page<Task> findAllByUserAndStatusAndStatusNot(User user, Pageable pageable, Status containsStatus, Status exceptStatus);

    Page<Task> findAllByUserAndStatusNotAndTitleContaining(User user, Pageable pageable, Status status, String title);

    Task getById(long id);

    Task getTaskByIdAndStatusNot(long id, Status status);

    void deleteById(long id);

}
