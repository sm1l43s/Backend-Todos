package by.program.restAPI.utils;

import by.program.restAPI.dto.adminDto.AdminUserDto;
import by.program.restAPI.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminUtil {

    public static AdminUserDto createDto(User user) {

        return AdminUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .totalTask(user.getTasks().size())
                .roles(RoleUtil.getDtos(user.getRoles()))
                .build();
    }
}
