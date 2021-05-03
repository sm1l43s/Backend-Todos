package by.program.restAPI.dto.forAdminDto;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminUserDto {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Status status;
    private int totalTask;
    private List<RoleDto> roles;

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
