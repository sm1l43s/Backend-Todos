package by.program.restAPI.repository;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByIdAndStatusNot(long id, Status status);

    Page<User> findAllByStatusNot(Pageable pageable, Status status);

    Page<User> findAllByStatusNotAndFirstNameContainingOrLastNameContaining(Pageable pageable,
                                                                            Status status,
                                                                            String searchFirstName,
                                                                            String searchLastName);

    long countByCreatedLessThanAndCreatedGreaterThan(Date startDate, Date endDate);
}
