package by.program.restAPI.service.impl;

import by.program.restAPI.dto.roleDto.RoleDto;
import by.program.restAPI.exception.AvatarUploadException;
import by.program.restAPI.exception.NotFoundException;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.User;
import by.program.restAPI.repository.RoleRepository;
import by.program.restAPI.repository.UserRepository;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.RoleUtil;
import by.program.restAPI.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User create(String email, String password, String firstName, String lastName) {
        log.debug("Create new user");

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRoles(userRoles);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setActive(true);

        ValidationUtil.validate(newUser);
        newUser = userRepository.prepareAndSaveWithPassword(newUser);

        log.info("New user with id {} and email {} successfully created", newUser.getId(), email);

        return newUser;
    }

    @Override
    public User findByIdWithTaskList(Long id) {
        log.debug("Find user with id = {} with task list", id);
        return userRepository
                .findByIdWithTaskList(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
    }

    @Override
    public User findById(Long id) {
        log.debug("Find user with id = {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + " not found"));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        log.debug("Attempting to delete user with id = {}", id);
        int updatedEntities = userRepository.updateIsActiveById(id, false);

        if (updatedEntities > 0) {
            log.debug("Status was changed for user with id = {}", id);
            log.info("User with id: {} successfully deleted", id);
            return false;
        } else {
            throw new NotFoundException("Not found entity with id= " + id);
        }
    }

    @Override
    @Transactional
    public boolean updateAboutMe(Long id, String aboutMe) {
        int updatedEntities = userRepository.updateAboutMeById(id, aboutMe);

        if (updatedEntities > 0) {
            log.debug("User {} successfully updated", id);
            return true;
        } else {
            throw new NotFoundException("Not found entity with id= {}" + id);
        }
    }

    @Transactional
    public void updateAvatar(Long id, MultipartFile file) {
        Byte[] avatar;
        try {
            byte[] bytes = file.getBytes();
            avatar = new Byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                avatar[i] = bytes[i];
            }
            userRepository.updateAvatarById(id, avatar);
            log.debug("Update avatar for user with id= {}", id);
        } catch (IOException e) {
            throw new AvatarUploadException("Failed to process avatar file", e);
        }
    }

    // Все active/not active
    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Get all users by pageable");
        return userRepository.findAllWithTaskList(pageable);
    }

    // Active
    @Override
    public Page<User> findAllActive(boolean isActive, Pageable pageable) {
        log.info("Get all users, where status - active");
        return userRepository.findAllActiveUsersWithTaskList(isActive, pageable);
    }

    public Page<User> findAllActiveAndNameContaining(boolean isActive, String search, Pageable pageable) {
        log.info("Get all active users, where searching line - {}", search);
        return userRepository.findAllActiveAndNameContainingWithTaskList(
                isActive, search, pageable);
    }

    @Override
    public void update(Long id, String firstName, String lastName, String email, boolean isActive, List<RoleDto> roleDtoList, String aboutMe) {
        log.debug("Update user with id= {}", id);
        User user = findById(id);
        List<Role> roles = RoleUtil.getRoles(roleDtoList);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setActive(isActive);
        user.setRoles(roles);
        user.setAboutMe(aboutMe);

        userRepository.save(user);
        log.debug("User {} successfully updated", id);
    }

    @Override
    public long countActiveUsers() { // нужен? лог + вызов репозитория
        log.debug("Count active users");
        return userRepository.countActiveUsers();
    }

    @Override
    public long countNewUsers(int days) {
        log.debug("Count new users for {} days", days);
        LocalDate now = LocalDate.now();
        LocalDate fromDate = now.minusDays(days);

        return userRepository.countByCreatedBetween(fromDate, now);
    }
}
