package by.program.restAPI.service;

import by.program.restAPI.dto.roleDto.RoleDto;
import by.program.restAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User findByIdWithTaskList(Long id);

    boolean updateAboutMe(Long id, String aboutMe);

    void updateAvatar(Long id, MultipartFile file);

    Page<User> findAll(Pageable pageable);

    boolean delete(Long id);

    Page<User> findAllActive(boolean isActive, Pageable pageable);

    Page<User> findAllActiveAndNameContaining(boolean isActive, String search, Pageable pageable);

    void update(Long id, String firstName, String lastName, String email, boolean isActive, List<RoleDto> roles, String aboutMe);

    long countActiveUsers();

    long countNewUsers(int days);

    // TODO
    User register(User user);

    User findById(Long id);

    User findByEmail(String email);
}
