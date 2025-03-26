package by.program.restAPI.service.impl;

import by.program.restAPI.exception.AvatarUploadException;
import by.program.restAPI.exception.NotFoundException;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.repository.RoleRepository;
import by.program.restAPI.repository.UserRepository;
import by.program.restAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
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
    private final BCryptPasswordEncoder passwordEncoder;


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

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Get all users by pageable");
        return userRepository.findAllWithTaskList(pageable);
    }


    // TODO

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(Date.valueOf(LocalDate.now()));
        user.setUpdated(Date.valueOf(LocalDate.now()));

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser.getEmail());

        return registeredUser;
    }

    @Override
    public Page<User> getAllByStatusNot(Pageable pageable, Status status) {
        Page<User> result = userRepository.findAllByStatusNot(pageable, status);
        log.info("IN getAllByStatusNot - {} user found with pageable - {}, where status not - {}", result, pageable, status);
        return result;
    }

    @Override
    public long countUsersByBetweenDate(Date start, Date end) {
        long count = userRepository.countByCreatedLessThanAndCreatedGreaterThan(start, end);
        log.info("IN countUsersByBetweenDate - count users: {} by between date: {} - {}", count, start, end);
        return count;
    }

    @Override
    public long countEntities() {
        long count = userRepository.count();
        log.info("IN countEntities - count all users: {}", count);
        return count;
    }

    @Override
    public Page<User> getAllByStatusNotAndFirstNameContainingOrLastNameContaining(Pageable pageable, Status status,
                                                                                  String searchFirstName,
                                                                                  String searchLastName) {
        Page<User> result = userRepository.findAllByStatusNotAndFirstNameContainingOrLastNameContaining(pageable, status,
                searchFirstName, searchFirstName);
        log.info("IN getAllByStatusNotAndFirstNameContainingAndLastNameContaining - {} user found", result);
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);

        if (result == null) {
            log.warn("IN findByEmail - no user found by email: {}", email);
            return null;
        }

        log.info("IN findByEmail - user: {} found by email: {}", result.getEmail(), email);
        return result;
    }


    @Override
    public User findByIdAndStatusNot(long id, Status status) {
        User result = userRepository.findByIdAndStatusNot(id, status).orElse(null);
        log.info("IN findByIdAndStatusNot - user: {} found by id: {} and status not: {}", result.getEmail(), id, status);
        return result;
    }
}
