package by.program.restAPI.utils;

import by.program.restAPI.dto.roleDto.RoleDto;
import by.program.restAPI.model.Role;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RoleUtil {

    public static List<RoleDto> getDtos(List<Role> roles) {
        return roles.stream()
                .map(RoleUtil::createDto)
                .collect(Collectors.toList());
    }

    public static RoleDto createDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }
}
