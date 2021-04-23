package by.program.restAPI.service.impl;

import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.repository.RoleRepository;
import by.program.restAPI.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role register(Role role) {
        role.setCreated(Date.valueOf(LocalDate.now()));
        role.setUpdated(Date.valueOf(LocalDate.now()));
        role.setStatus(Status.ACTIVE);
        log.info("IN register - role: {} successfully registered", role);
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        role.setUpdated(Date.valueOf(LocalDate.now()));
        Role updatedRole = roleRepository.save(role);
        log.info("IN update - role: {} successfully updated", role);
        return updatedRole;
    }

    @Override
    public Page<Role> getAll(int page, int size) {
        Page<Role> result = roleRepository.findAll(PageRequest.of(page, size));
        log.info("IN getAll - {} role found with page - {} and size - {}", result.getContent().size(), page, size);
        return result;
    }

    @Override
    public Role findByName(String name) {
        Role result = roleRepository.findByName(name);

        if (result == null) {
            log.warn("IN findByName - no roles found by name: {}", name);
            return null;
        }

        log.info("IN findByName - role: {} found by name: {}", result, name);
        return result;
    }

    @Override
    public Role findById(Long id) {
        Role result = roleRepository.findById(id).orElse(null);

        log.info("IN findById - role: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
        log.info("IN delete - role with id: {} successfully deleted");
    }
}
