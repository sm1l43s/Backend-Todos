package by.program.restAPI.service;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public interface UserService {

    User findByIdWithTaskList(Long id);

    boolean updateAboutMe(Long id, String aboutMe);

    void updateAvatar(Long id, MultipartFile file);

    Page<User> findAll(Pageable pageable);

    boolean delete(Long id);


    // TODO
    User register(User user);

    User findById(Long id);

    Page<User> getAllByStatusNot(Pageable pageable, Status status);

    long countUsersByBetweenDate(Date start, Date end);

    long countEntities();

    User findByEmail(String email);


    User findByIdAndStatusNot(long id, Status status);

    Page<User> getAllByStatusNotAndFirstNameContainingOrLastNameContaining(Pageable pageable,
                                                                           Status status,
                                                                           String searchFirstName,
                                                                           String searchLastName);


}
