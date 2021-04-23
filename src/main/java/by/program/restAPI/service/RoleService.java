package by.program.restAPI.service;

import by.program.restAPI.model.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    Role register(Role role);

    Role update(Role role);

    Page<Role> getAll(int page, int size);

    Role findByName(String name);

    Role findById(Long id);

    void delete(Long id);

}
