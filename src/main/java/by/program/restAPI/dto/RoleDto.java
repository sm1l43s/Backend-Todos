package by.program.restAPI.dto;

import by.program.restAPI.model.Role;
import by.program.restAPI.service.RoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class RoleDto {

    @Autowired
    private static RoleService roleService;

    private String name;

    public static RoleDto fromRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }

}
