package by.program.restAPI.service.impl;

import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.repository.RoleRepository;
import by.program.restAPI.repository.UserRepository;
import by.program.restAPI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    public User update(User user) {
        user.setUpdated(Date.valueOf(LocalDate.now()));
        log.info("IN update - data user {} succesfully updated", user.getEmail());
        User updateUser = userRepository.save(user);
        return updateUser;
    }

    @Override
    public Page<User> getAll(int page, int size) {
        Page<User> result = userRepository.findAll(PageRequest.of(page, size));
        log.info("IN getAll - {} users found with page - {} and size - {}", result.getContent().size(), page, size);
        return result;
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
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        log.info("IN findById - user: {} found by id: {}", result.getEmail());
        return result;
    }

    @Override
    public User findByIdAndStatusNot(long id, Status status) {
        User result = userRepository.findByIdAndStatusNot(id, status).orElse(null);
        log.info("IN findByIdAndStatusNot - user: {} found by id: {} and status not: {}", result.getEmail(), id, status);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}
