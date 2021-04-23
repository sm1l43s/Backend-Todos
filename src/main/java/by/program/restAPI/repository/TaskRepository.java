package by.program.restAPI.repository;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(Status status);

    Page<Task> findAllByUser(User user, Pageable pageable);

    Page<Task> findAllByUserAndStatusNot(User user, Pageable pageable, Status status);

    Page<Task> findAllByUserAndStatusAndStatusNot(User user, Pageable pageable, Status containsStatus, Status exceptStatus);

    Page<Task> findAllByUserAndStatusNotAndTitleContaining(User user, Pageable pageable, Status status, String title);

    Optional<Task> findByIdAndStatusNot(long id, Status status);

    void deleteById(long id);

}
