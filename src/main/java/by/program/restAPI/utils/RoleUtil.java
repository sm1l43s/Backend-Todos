package by.program.restAPI.utils;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleUtil {

    public static RoleDto fromRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }
}
