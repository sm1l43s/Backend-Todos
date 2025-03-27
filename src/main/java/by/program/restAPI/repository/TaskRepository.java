package by.program.restAPI.repository;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.status <> :excludedStatus AND LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Task> searchByUserIdAndTitleContainingIgnoreCase(
            @Param("userId") Long userId,
            @Param("excludedStatus") Status excludedStatus,
            @Param("title") String title,
            Pageable pageable
    );

    long countByStatus(Status status);
}
