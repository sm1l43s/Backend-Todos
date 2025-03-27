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
import java.time.LocalDate;
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
    Page<User> findAllWithTaskList(Pageable pageable); // заменить на следующий метод с проверкой статуса

    @Query(
            value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.isActive = :active",
            countQuery = "SELECT COUNT(u) FROM User u WHERE u.isActive = :active"
    )
    Page<User> findAllActiveUsersWithTaskList(@Param("active") boolean active, Pageable pageable);

    @Query(
            value = """
        SELECT DISTINCT u FROM User u
        LEFT JOIN FETCH u.tasks
        WHERE u.isActive <> :active AND 
              (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) 
              OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')))
        """,
            countQuery = """
        SELECT COUNT(u) FROM User u
        WHERE u.isActive <> :active AND 
              (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) 
              OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')))
        """
    )
    Page<User> findAllActiveAndNameContainingWithTaskList(
            @Param("active") boolean active,
            @Param("search") String search,
            Pageable pageable
    );

    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();

    long countByCreatedBetween(LocalDate startDate, LocalDate endDate);
}
