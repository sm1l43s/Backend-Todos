package by.program.restAPI.repository;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
    Optional<User> findByIdWithTaskList(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")
    int updateIsActiveById(@Param("id") Long id, @Param("isActive") Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.aboutMe = :aboutMe WHERE u.id = :id")
    int updateAboutMeById(@Param("id") Long id, @Param("aboutMe") String aboutMe);

    @Modifying
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.id = :id")
    int updateAvatarById(@Param("id") Long id, @Param("avatar") Byte[] avatar);

    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks",
            countQuery = "SELECT COUNT(u) FROM User u")
    Page<User> findAllWithTaskList(Pageable pageable);


    // TODO
    User findByEmail(String email);

    Optional<User> findByIdAndStatusNot(long id, Status status);

    Page<User> findAllByStatusNot(Pageable pageable, Status status);

    long countByCreatedLessThanAndCreatedGreaterThan(Date startDate, Date endDate);

    Page<User> findAllByStatusNotAndFirstNameContainingOrLastNameContaining(Pageable pageable,
                                                                            Status status,
                                                                            String searchFirstName,
                                                                            String searchLastName);
}
