package by.program.restAPI.dto;

import by.program.restAPI.model.Role;
import lombok.Data;

@Data
public class RoleDto {

    private String name;

    public static RoleDto fromRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }
}
