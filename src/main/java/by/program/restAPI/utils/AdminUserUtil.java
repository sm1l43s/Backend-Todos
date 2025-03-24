package by.program.restAPI.utils;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.dto.forAdminDto.AdminUserDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class AdminUserUtil {

    public static AdminUserDto fromUserToAdminUserDto(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setStatus(user.getStatus());
        adminUserDto.setTotalTask(user.getTaskList().size());
        adminUserDto.setRoles(transfer(user.getRoles()));
        return adminUserDto;
    }

    public static List<AdminUserDto> fromListUserToListAdminUserDto(List<User> users) {
        List<AdminUserDto> adminUserDtoList = new ArrayList<>();

        for (User user: users) {
            adminUserDtoList.add(fromUserToAdminUserDto(user));
        }
        return adminUserDtoList;
    }


    private static List<RoleDto> transfer(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            roleDtoList.add(RoleDto.fromRoleToRoleDto(roles.get(i)));
        }
        return roleDtoList;
    }
}
