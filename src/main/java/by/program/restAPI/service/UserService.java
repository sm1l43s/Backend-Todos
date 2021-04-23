package by.program.restAPI.service;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface UserService {

    User register(User user);

    User update(User user);

    Page<User> getAll(int page, int size);

    Page<User> getAllByStatusNot(Pageable pageable, Status status);

    long countUsersByBetweenDate(Date start, Date end);

    long countEntities();

    User findByEmail(String email);

    User findById(Long id);

    User findByIdAndStatusNot(long id, Status status);

    Page<User> getAllByStatusNotAndFirstNameContainingOrLastNameContaining(Pageable pageable,
                                                                            Status status,
                                                                            String searchFirstName,
                                                                            String searchLastName);

    void delete(Long id);
}
